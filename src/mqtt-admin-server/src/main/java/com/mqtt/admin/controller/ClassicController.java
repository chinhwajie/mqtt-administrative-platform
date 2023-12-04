package com.mqtt.admin.controller;

import com.mqtt.admin.PreLoaded;
import com.mqtt.admin.exception_handler.ExceptionEnum;
import com.mqtt.admin.db_entity.Topic;
import com.mqtt.admin.db_entity.TopicRepository;
import com.mqtt.admin.entity.ResultBox;
import com.mqtt.admin.iot.IotListenerControlUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    public ResultBox connectIotListener(
            @PathVariable("iotId") String iotId,
            @PathVariable("topicName") String topicName
    ) {
        Topic topic = topicRepository.findTopicByTopicAndIot_IotId(topicName, iotId);
        if (topic != null) {
            IotListenerControlUnit.connect(topicName, iotId, broker);
            return ResultBox.success();
        }
        return ResultBox.error(ExceptionEnum.SERVER_BUSY);
    }

    @PostMapping("/disconnect-listener/{iotId}/{topicName}")
    public ResultBox disconnectIotListener(
            @PathVariable("iotId") String iotId,
            @PathVariable("topicName") String topicName
    ) {
        Topic topic = topicRepository.findTopicByTopicAndIot_IotId(topicName, iotId);
        if (topic != null) {
            IotListenerControlUnit.disconnect(topicName, iotId);
            return ResultBox.success();
        }
        return ResultBox.error(ExceptionEnum.NOT_FOUND, "Topic not found");
    }

    @GetMapping("/pre-load")
    public void load() {
        preLoaded.run();
    }
}
