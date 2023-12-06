package com.mqtt.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mqtt.admin.db_entity.*;
import com.mqtt.admin.entity.*;
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
import java.util.logging.Logger;

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
        try {
            ArrayList<Iot> iots = new ArrayList<>();
            iotRepository.findAll().forEach(iots::add);
            return iots;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @QueryMapping
    public Iot getIot(
            @Argument String iotId
    ) {
        try {
            Optional<Iot> opIot = iotRepository.findById(iotId);
            return opIot.orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @MutationMapping
    public Iot deleteIot(
            @Argument String iotId
    ) {
        try {
            Optional<Iot> opIot = iotRepository.findById(iotId);
            if (opIot.isPresent()) {
                iotRepository.delete(opIot.get());
                return opIot.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @MutationMapping
    public Iot createIot(
            @Argument String iotId,
            @Argument String iotName,
            @Argument String info,
            @Argument Category iotCategory
    ) {
        Iot iot = new Iot(iotId, iotName, info, iotCategory);
        try {
            iotRepository.save(iot);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
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
        Iot iot = new Iot(iotId, iotName, info, iotCategory);
        try {
            iotRepository.save(iot);
            Optional<Iot> opIot;
            if ((opIot = iotRepository.findById(iotId)).isPresent()) {
                for (String t : topics) {
                    topicRepository.save(new Topic(t, opIot.get()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
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
        try {
            Optional<Iot> optionalIot = iotRepository.findById(iotId);
            if (optionalIot.isPresent()) {
                Iot iot = optionalIot.get();
                iot.setInfo(info);
                iot.setCategory(iotCategory);
                iot.setName(iotName);
                List<Topic> oldList = topicRepository.findTopicsByIot_IotId(iotId);

                iotRepository.save(iot);
                for (String t : topics) {
                    Topic check = new Topic(t, iot);
                    if (!oldList.contains(check)) {
                        topicRepository.save(check);
                    }
                }

                for (Topic t : oldList) {
                    if (!topics.contains(t.getTopic())) {
                        topicRepository.delete(t);
                    }
                }
                return iot;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    @QueryMapping
    public List<Topic> getTopicsByIotId(
            @Argument String iotId
    ) {
        List<Topic> topics;
        try {
            topics = topicRepository.findTopicsByIot_IotId(iotId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return topics;
    }

    @QueryMapping
    public List<CountIotGroupByCategory> getCountIotGroupByCategory() {
        try {
            return iotRepository.countIotGroupByCategory();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @MutationMapping
    public Iot createTopic(
            @Argument String iotId,
            @Argument String topicName
    ) {
        try {
            Optional<Iot> opIot = iotRepository.findById(iotId);
            if (opIot.isPresent()) {
                Topic topic = new Topic(topicName, opIot.get());
                topicRepository.save(topic);
                return opIot.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @MutationMapping
    public Iot deleteTopic(
            @Argument String iotId,
            @Argument String topicName
    ) {
        try {
            Topic topic = topicRepository.findTopicByTopicAndIot_IotId(topicName, iotId);

            Optional<Iot> opIot = iotRepository.findById(iotId);

            if (topic != null) {
                topicRepository.delete(topic);
            }
            if (opIot.isPresent()) {
                return opIot.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @QueryMapping
    public List<CountDistinctTopic> countDistinctTopic() {
        try {
            return messageRepository.countDistinctTopic();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }


    @QueryMapping
    public List<Message> getMessagesByIotCategory(@Argument Category iotCategory) {
        try {
            List<Message> messages = messageRepository.findMessagesByIot_Category(iotCategory);
            return messages;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @QueryMapping
    public List<Message> getMessagesByIotId(@Argument String iotId) {
        try {
            List<Message> messages = messageRepository.findMessagesByIot_IotId(iotId);
            return messages;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @MutationMapping
    public Message deleteMessage(@Argument Integer messageId) {
        try {
            Optional<Message> opMessage = messageRepository.findById(messageId);
            if (opMessage.isPresent()) {
                messageRepository.deleteById(messageId);
                return opMessage.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
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
        for (Message m: messages) {
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
