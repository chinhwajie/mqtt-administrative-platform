package com.mqtt.admin.db_entity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Integer> {
    List<Topic> findTopicsByIot_IotId(String iotId);

    Topic findTopicByTopicAndIot_IotId(String topic, String iotId);

    void deleteTopicByTopicAndIot_IotId(String topic, String iotId);

    @Modifying
    @Query("UPDATE Topic t SET t.connectionState = false ")
    void setFalseAllConnectionState();
}
