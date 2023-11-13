package com.mqtt.admin.db_entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Integer>{
    public List<Topic> findTopicsByIot_IotId(String iotId);
    public Topic findTopicByTopicAndIot_IotId(String topic, String iotId);
    public void deleteTopicByTopicAndIot_IotId(String topic, String iotId);
}
