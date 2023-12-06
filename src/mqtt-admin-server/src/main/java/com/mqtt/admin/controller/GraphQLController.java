package com.mqtt.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mqtt.admin.db_entity.*;
import com.mqtt.admin.entity.*;
import com.mqtt.admin.exception_handler.exception.ActiveConnectionFoundException;
import com.mqtt.admin.exception_handler.exception.NotFoundException;
import com.mqtt.admin.helper.MqttUtils;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Controller
public class GraphQLController {
    @Autowired
    private IotRepository iotRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TopicRepository topicRepository;


    // TODO: Retrieve online and offline iot devices quantity
    @QueryMapping
    public IotActivityState getIotActivityState() {
        return null;
    }

    @QueryMapping
    public Integer getActiveListeningConnection() {
        return iotRepository.activeDevicesCount();
    }

    @QueryMapping
    public List<MessageCountPerDay> latestNDaysMessagesCountTrend(
            @Argument Integer n
    ) {
        List<MessageCountPerDay> messagesCountTrend = messageRepository.messagesCountTrend();
        int sIdx = messagesCountTrend.size() - Math.min(messagesCountTrend.size(), n);
        return messagesCountTrend.subList(sIdx, messagesCountTrend.size());
    }

    @QueryMapping
    public List<Iot> complexIotSearch(
            @Argument List<Category> categories,
            @Argument String type,
            @Argument String typeValue,
            @Argument String status,
            @Argument String sDate,
            @Argument String eDate
    ) {
        Specification<Iot> spec = Specification.where(null);
        spec = spec.and((root, query, cb) -> {
            Predicate p = null;
            if (categories != null && !categories.isEmpty()) {
                Predicate pp = null;
                for (Category category : categories) {
                    Predicate ppp = cb.equal(root.get("category"), category);
                    if (pp == null) pp = cb.or(ppp);
                    else pp = cb.or(pp, ppp);
                }
                p = cb.and(pp);
            }

            if (type != null && !type.equals("None") && !typeValue.isEmpty()) {
                Predicate pp = cb.like(root.get(type), "%" + typeValue + "%");
                if (p == null) p = cb.and(pp);
                else p = cb.and(p, pp);
            }

            if (sDate != null && eDate != null && !sDate.isEmpty() && !eDate.isEmpty()) {
                Predicate pp = cb.between(root.get("createTime"), Timestamp.valueOf(sDate), Timestamp.valueOf(eDate));
                if (p == null) p = cb.and(pp);
                else p = cb.and(p, pp);
            }
            return p;
        });
        return iotRepository.findAll(spec);

    }

    @QueryMapping
    public List<Message> searchMessages(
            @Argument String type,
            @Argument String value
    ) {
        switch (type) {
            case "Category":
                return messageRepository.findMessagesByIot_Category(Category.get(value));
            case "Iot ID":
                return messageRepository.findMessagesByIot_IotIdContaining(value.toLowerCase());
            case "Topic":
                return messageRepository.findMessagesByTopicContainingIgnoreCase(value);
            case "Message":
                return messageRepository.findMessagesByPayloadContainingIgnoreCase(value);
            default:
                return messageRepository.findAll();
        }
    }

    @QueryMapping
    public Integer getTotalMessagesCount() {
        try {
            return messageRepository.countAll();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return -1;
    }

    @QueryMapping
    public Integer getIotsCount() {
        try {
            return iotRepository.countAll();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return -1;
    }

    @QueryMapping
    public List<Iot> getAllIots() {
        ArrayList<Iot> iots = new ArrayList<>();
        iotRepository.findAll().forEach(iots::add);
        return iots;
    }

    @QueryMapping
    public Iot getIot(
            @Argument String iotId
    ) {
        Optional<Iot> opIot = iotRepository.findById(iotId);
        if (opIot.isPresent()) {
            return opIot.get();
        } else {
            throw new NotFoundException("Iot not found!");
        }
    }

    @MutationMapping
    public Iot deleteIot(
            @Argument String iotId
    ) {
        Optional<Iot> opIot = iotRepository.findById(iotId);
        if (opIot.isPresent()) {
            iotRepository.delete(opIot.get());
            return opIot.get();
        } else {
            throw new NotFoundException("Iot not found!");
        }
    }

    @MutationMapping
    public Iot createIot(
            @Argument String iotId,
            @Argument String iotName,
            @Argument String info,
            @Argument Category iotCategory
    ) {
        Iot iot = new Iot(iotId, iotName, info, iotCategory);
        iotRepository.save(iot);
        return iot;
    }

    @MutationMapping
    public Iot createFullIot(
            @Argument String iotId,
            @Argument String iotName,
            @Argument String info,
            @Argument Category iotCategory,
            @Argument List<String> topics
    ) {
        if (iotId.isEmpty() || iotName.isEmpty()) throw new IllegalArgumentException("Device ID or Device name cannot be empty!");

        Iot iot = new Iot(iotId, iotName, info, iotCategory);
        iotRepository.save(iot);
        Optional<Iot> opIot;
        if ((opIot = iotRepository.findById(iotId)).isPresent()) {
            for (String t : topics) {
                topicRepository.save(new Topic(t, opIot.get()));
            }
        }
        return iot;
    }

    @MutationMapping
    public Iot updateFullIot(
            @Argument String iotId,
            @Argument String iotName,
            @Argument String info,
            @Argument Category iotCategory,
            @Argument List<String> topics
    ) {
        Optional<Iot> optionalIot = iotRepository.findById(iotId);
        if (optionalIot.isPresent()) {
            Iot iot = optionalIot.get();

            if (iot.getConnectionState())
                throw new ActiveConnectionFoundException("Found active connection, please disconnect first");

            iot.setInfo(info);
            iot.setCategory(iotCategory);
            iot.setName(iotName);
            List<Topic> oldList = iot.getTopics();

            iotRepository.save(iot);
            for (String t : topics) {
                Topic check = new Topic(t, iot);
                int i = 0;
                for (; i < oldList.size(); i++) {
                    if (oldList.get(i).getIot().getIotId().equals(iot.getIotId()) &&
                            oldList.get(i).getTopic().equals(t)) {
                        break;
                    }
                }
                if (i >= oldList.size()) topicRepository.save(check);
            }

            for (Topic t : oldList) {
                if (!topics.contains(t.getTopic())) {
                    topicRepository.delete(t);
                }
            }

            return iotRepository.findById(iotId).get();
        } else {
            throw new NotFoundException("Iot not found");
        }
    }

    @QueryMapping
    public List<Topic> getTopicsByIotId(
            @Argument String iotId
    ) {
        return topicRepository.findTopicsByIot_IotId(iotId);
    }

    @QueryMapping
    public List<CountIotGroupByCategory> getCountIotGroupByCategory() {
        return iotRepository.countIotGroupByCategory();
    }

    @MutationMapping
    public Iot createTopic(
            @Argument String iotId,
            @Argument String topicName
    ) {
        Optional<Iot> opIot = iotRepository.findById(iotId);
        if (opIot.isPresent()) {
            Topic topic = new Topic(topicName, opIot.get());
            topicRepository.save(topic);
            return opIot.get();
        } else {
            throw new NotFoundException("Iot not found!");
        }
    }

    @MutationMapping
    public Iot deleteTopic(
            @Argument String iotId,
            @Argument String topicName
    ) {
        Topic topic = topicRepository.findTopicByTopicAndIot_IotId(topicName, iotId);

        Optional<Iot> opIot = iotRepository.findById(iotId);

        if (topic != null) {
            topicRepository.delete(topic);
        }
        if (opIot.isPresent()) {
            return opIot.get();
        } else {
            throw new NotFoundException("Iot not found");
        }
    }

    @QueryMapping
    public List<CountDistinctTopic> countDistinctTopic() {
        return messageRepository.countDistinctTopic();
    }


    @QueryMapping
    public List<Message> getMessagesByIotCategory(@Argument Category iotCategory) {
        return messageRepository.findMessagesByIot_Category(iotCategory);
    }

    @QueryMapping
    public List<Message> getMessagesByIotId(@Argument String iotId) {
        return messageRepository.findMessagesByIot_IotId(iotId);
    }

    @MutationMapping
    public Message deleteMessage(@Argument Integer messageId) {
        Optional<Message> opMessage = messageRepository.findById(messageId);
        if (opMessage.isPresent()) {
            messageRepository.deleteById(messageId);
            return opMessage.get();
        } else {
            throw new NotFoundException("Delete message not found!");
        }
    }

    @QueryMapping
    public List<Category> getAvailableCategories() {
        return Category.getAll();
    }

    @QueryMapping
    public List<IotTrace> getAllIotTraces() throws JsonProcessingException {
        // TODO: Not yet implement
        List<Message> messages = messageRepository.findAll();
        messages.sort(MqttUtils.messageComparator);

        HashMap<Iot, Vector<List<Double>>> tempIotTraces = new HashMap<>();
        for (Message m : messages) {
            Iot iot = m.getIot();
            if (!tempIotTraces.containsKey(iot)) {
                Vector<List<Double>> iotTrace = new Vector<>();
                tempIotTraces.put(iot, iotTrace);
            }
            Vector<List<Double>> iotTrace = tempIotTraces.get(iot);
            iotTrace.add(MqttUtils.extractCoordinate(m.getPayload()));
        }

        List<IotTrace> result = new ArrayList<>();
        for (Map.Entry<Iot, Vector<List<Double>>> e : tempIotTraces.entrySet()) {
            result.add(new IotTrace(e.getKey(), e.getValue()));
        }
        return result;
    }
}
