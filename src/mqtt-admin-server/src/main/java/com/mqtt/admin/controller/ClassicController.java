package com.mqtt.admin.controller;

import com.mqtt.admin.entity.API_STATE;
import com.mqtt.admin.entity.SRB;
import com.mqtt.admin.iot.IotListenerControlUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classic")
public class ClassicController {
    @Value("${application.broker.host}")
    private String broker;

    // TODO: listen
    @PostMapping("/connect-listener/{iotId}/{topicName}")
    public SRB.ListenerResult connectIotListener(
            @PathVariable("iotId") String iotId,
            @PathVariable("topicName") String topicName
    ) {
        // TODO: Connect 之前需要到数据库验证iot和topic的并存
        IotListenerControlUnit.connect(topicName, iotId, broker);
        return new SRB.ListenerResult(API_STATE.SUCCESS);
    }

    @PostMapping("/disconnect-listener/{iotId}/{topicName}")
    public SRB.ListenerResult disconnectIotListener(
            @PathVariable("iotId") String iotId,
            @PathVariable("topicName") String topicName
    ) {
        // TODO: Disconnect 之前需要到数据库验证iot和topic的并存
        IotListenerControlUnit.disconnect(topicName, iotId);
        return new SRB.ListenerResult(API_STATE.SUCCESS);
    }
}
