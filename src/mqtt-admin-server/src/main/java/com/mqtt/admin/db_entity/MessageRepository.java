package com.mqtt.admin.db_entity;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {

}
