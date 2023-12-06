package com.mqtt.admin.config;

import com.mqtt.admin.db_entity.IotRepository;
import com.mqtt.admin.db_entity.TopicRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

@Service
public class ShutdownService implements ApplicationListener<ContextClosedEvent> {

    //    @Autowired
//    private IotRepository iotRepository;
//    @Autowired
//    private TopicRepository topicRepository;
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // Code to be executed before shutdown
        System.out.println("Set false to all connection states in database before shutdown...");
//        topicRepository.setFalseAllConnectionState();
//        iotRepository.setFalseAllConnectionState();
    }
}
