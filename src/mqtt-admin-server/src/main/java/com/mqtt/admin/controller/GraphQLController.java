package com.mqtt.admin.controller;

import com.mqtt.admin.db_entity.*;
import com.mqtt.admin.entity.*;
import com.mqtt.admin.iot.IotListener;
import com.mqtt.admin.iot.IotListenerControlUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        return IotListenerControlUnit.activeConnection();
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
            for (String t : topics) {
                topicRepository.save(new Topic(t, iot));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return iot;
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
}
