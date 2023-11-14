package com.mqtt.admin.controller;

import com.mqtt.admin.PreLoaded;
import com.mqtt.admin.db_entity.Topic;
import com.mqtt.admin.db_entity.TopicRepository;
import com.mqtt.admin.entity.API_STATE;
import com.mqtt.admin.entity.SRB;
import com.mqtt.admin.iot.IotListenerControlUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/classic")
public class ClassicController {
    @Value("${application.broker.host}")
    private String broker;

    @Autowired
    private PreLoaded preLoaded;
    @Autowired
    private TopicRepository topicRepository;

    // TODO: listen
    @PostMapping("/connect-listener/{iotId}/{topicName}")
    public SRB.ListenerResult connectIotListener(
            @PathVariable("iotId") String iotId,
            @PathVariable("topicName") String topicName
    ) {
        try {
            Topic topic = topicRepository.findTopicByTopicAndIot_IotId(topicName, iotId);
            if (topic != null) {
                IotListenerControlUnit.connect(topicName, iotId, broker);
                return new SRB.ListenerResult(API_STATE.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new SRB.ListenerResult(API_STATE.FAILED);
    }

    @PostMapping("/disconnect-listener/{iotId}/{topicName}")
    public SRB.ListenerResult disconnectIotListener(
            @PathVariable("iotId") String iotId,
            @PathVariable("topicName") String topicName
    ) {
        try {
            Topic topic = topicRepository.findTopicByTopicAndIot_IotId(topicName, iotId);
            if (topic != null) {
                IotListenerControlUnit.disconnect(topicName, iotId);
                return new SRB.ListenerResult(API_STATE.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new SRB.ListenerResult(API_STATE.FAILED);
    }

    @GetMapping("/pre-load")
    public void load() {
        preLoaded.run();
    }
}
