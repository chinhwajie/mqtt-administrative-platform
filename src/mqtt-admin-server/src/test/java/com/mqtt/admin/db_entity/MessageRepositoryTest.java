package com.mqtt.admin.db_entity;

import org.hibernate.internal.log.SubSystemLogging;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void test() {
        List<Message> messages = messageRepository.findMessagesByPayloadContainingIgnoreCase(":FALSE");
        System.out.println(messages.size());
        messages = messageRepository.findMessagesByIot_IotIdContaining("iot10");
        System.out.println(messages.size());
    }

}
