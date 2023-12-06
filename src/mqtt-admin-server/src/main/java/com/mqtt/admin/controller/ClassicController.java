package com.mqtt.admin.controller;

import com.mqtt.admin.PreLoaded;
import com.mqtt.admin.entity.TopicKey;

import com.mqtt.admin.db_entity.TopicRepository;
import com.mqtt.admin.entity.ResultBox;

import com.mqtt.admin.service.MqttClientService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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
    @Autowired
    private MqttClientService mqttClientService;

    // TODO: listen
    @PostMapping("/connect")
    public ResultBox connectIotListener(
            @RequestBody TopicKey body
    ) throws MqttException {
        mqttClientService.connect(body.getIotId());
        log.info("[CONNECT] ID: " + body.getIotId());
        return ResultBox.success();
    }

    @PostMapping("/subscribe")
    public ResultBox subscribe(
            @RequestBody TopicKey body
    ) throws MqttException {
        mqttClientService.subscribe(body);
        log.info("[SUBSCRIBE] Topic: " + body.getTopic() + ",ID: " + body.getIotId());
        return ResultBox.success();
    }

    @PostMapping("/disconnect")
    public ResultBox disconnectIotListener(
            @RequestBody TopicKey body
    ) throws MqttException {
        mqttClientService.disconnect(body.getIotId());
        log.info("[DISCONNECT] ID: " + body.getIotId());
        return ResultBox.success();
    }

    @PostMapping("/unsubscribe")
    public ResultBox unsubscribe(
            @RequestBody TopicKey body
    ) throws MqttException {
        mqttClientService.unsubscribe(body);
        log.info("[UNSUBSCRIBE] Topic: " + body.getTopic() + ",ID: " + body.getIotId());
        return ResultBox.success();
    }

    @GetMapping("/pre-load")
    public void load() {
        preLoaded.run();
    }
}
