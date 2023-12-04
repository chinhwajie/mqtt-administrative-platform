package com.mqtt.admin.db_entity;

import com.mqtt.admin.entity.Category;
import com.mqtt.admin.entity.CountDistinctTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findMessagesByAlert(boolean alert);

    List<Message> findMessagesByIot_Category(Category iotCategory);

    List<Message> findMessagesByIot_IotId(String iotId);

    @Query("select count(m.id) from Message m")
    Integer countAll();

    @Query("select new com.mqtt.admin.entity.CountDistinctTopic(count(m.topic), m.topic) from Message m group by m.topic order by count(m.topic) desc limit 20")
    List<CountDistinctTopic> countDistinctTopic();
}
