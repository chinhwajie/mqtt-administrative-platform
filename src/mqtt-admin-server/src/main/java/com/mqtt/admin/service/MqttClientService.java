package com.mqtt.admin.service;

import com.alibaba.fastjson2.JSONObject;
import com.mqtt.admin.db_entity.*;
import com.mqtt.admin.entity.TopicKey;
import com.mqtt.admin.exception_handler.exception.ConnectionFoundException;
import com.mqtt.admin.exception_handler.exception.ConnectionNotFoundException;
import com.mqtt.admin.exception_handler.exception.NotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class MqttClientService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private IotRepository iotRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Value("${application.broker.host}")
    private String mqttHostUrl;

    private final Map<String, IMqttClient> mqttClients = new HashMap<>();

    public void connect(String clientId) throws MqttException {
        IMqttClient check = mqttClients.get(clientId);
        if (check != null) throw new ConnectionFoundException();

        Optional<Iot> optionalIot = iotRepository.findById(clientId);
        if (optionalIot.isEmpty()) throw new NotFoundException("Iot not found");

        IMqttClient mqttClient = new MqttClient(mqttHostUrl, clientId);
        mqttClient.connect();

        Iot iot = optionalIot.get();
        iot.setConnectionState(true);
        iotRepository.save(iot);

        mqttClients.put(clientId, mqttClient);
    }

    public void subscribe(TopicKey body) throws MqttException {
        String clientId = body.getIotId();
        String topic = body.getTopic();

        IMqttClient mqttClient = mqttClients.get(clientId);
        if (mqttClient == null) throw new ConnectionNotFoundException();

        Optional<Iot> optionalIot = iotRepository.findById(clientId);
        if (optionalIot.isEmpty()) throw new NotFoundException("Iot not found");

        Topic check = topicRepository.findTopicByTopicAndIot_IotId(body.getTopic(), body.getIotId());
        if (check == null) throw new NotFoundException();
        check.setConnectionState(true);
        topicRepository.save(check);

        mqttClient.subscribe(topic, (s, mqttMessage) -> {
            String payload = new String(mqttMessage.getPayload());

            try {
                JSONObject object = JSONObject.parseObject(payload);
                Boolean alert = (Boolean) object.get("alert");

                log.info("[MQTT MESSAGE] IOT:" + clientId + ",Topic:" + topic + "Payload:" + payload);

                Message msg = new Message();
                msg.setPayload(payload);
                msg.setAlert(alert);
                msg.setTopic(topic);
                msg.setIot(optionalIot.get());

                messageRepository.save(msg);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.warn("Insertion of message canceled due to invalid format of message or something else");
            }
        });
    }

    public void unsubscribe(TopicKey body) throws MqttException {
        String clientId = body.getIotId();
        String topic = body.getTopic();

        IMqttClient mqttClient = mqttClients.get(clientId);
        if (mqttClient == null) throw new ConnectionNotFoundException();

        Optional<Iot> optionalIot = iotRepository.findById(clientId);
        if (optionalIot.isEmpty()) throw new NotFoundException("Iot not found");

        Topic check = topicRepository.findTopicByTopicAndIot_IotId(body.getTopic(), body.getIotId());
        if (check == null) throw new NotFoundException("Topic not found!");
        check.setConnectionState(false);
        topicRepository.save(check);


        mqttClient.unsubscribe(topic);
    }

    public void disconnect(String clientId) throws MqttException {
        IMqttClient mqttClient = mqttClients.get(clientId);
        if (mqttClient == null) throw new ConnectionNotFoundException();

        Optional<Iot> optionalIot = iotRepository.findById(clientId);
        if (optionalIot.isEmpty()) throw new NotFoundException("Iot not found");

        mqttClient.disconnect();

        Iot iot = optionalIot.get();
        iot.setConnectionState(false);
        iotRepository.save(iot);

        List<Topic> topics = topicRepository.findTopicsByIot_IotId(iot.getIotId());
        for (Topic t : topics) {
            t.setConnectionState(false);
            topicRepository.save(t);
        }

        mqttClients.remove(clientId);
    }
}

