package com.mqtt.admin.db_entity;

import com.mqtt.admin.entity.Category;
import com.mqtt.admin.entity.CountDistinctTopic;
import com.mqtt.admin.entity.MessageCountPerDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findMessagesByAlert(boolean alert);
    List<Message> findMessagesByIot_Category(Category iotCategory);

    List<Message> findMessagesByIot_IotId(String iotId);

    @Query(value = "select m from Message m where m.iot.iotId like ?1")
    List<Message> findMessagesByIot_IotIdContaining(String iotId);

    @Query("select count(m.id) from Message m")
    Integer countAll();

    @Query("select new com.mqtt.admin.entity.CountDistinctTopic(count(m.topic), m.topic) from Message m group by m.topic order by count(m.topic) desc limit 20")
    List<CountDistinctTopic> countDistinctTopic();

    @Query("SELECT new com.mqtt.admin.entity.MessageCountPerDay(COUNT(*), DATE(m.createTime)) FROM Message m GROUP BY DATE(m.createTime) ORDER BY DATE(m.createTime) ASC " )
    List<MessageCountPerDay> messagesCountTrend();

    List<Message> findMessagesByTopicContainingIgnoreCase(String topic);
    List<Message> findMessagesByPayloadContainingIgnoreCase(String payload);
}
