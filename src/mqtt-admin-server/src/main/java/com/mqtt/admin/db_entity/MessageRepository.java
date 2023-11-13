package com.mqtt.admin.db_entity;

import com.mqtt.admin.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    public List<Message> findMessagesByAlert(boolean alert);

    public List<Message> findMessagesByIotCategory(Category iotCategory);
    public List<Message> findMessagesByIot_IotId(String iotId);

    @Query("select count(m.id) from Message m")
    public Integer countAll();

    public List<Message> countDistinctByTopic(String topic);
}
