package com.mqtt.admin.iot;

import com.alibaba.fastjson2.JSONObject;
import com.mqtt.admin.db_entity.Iot;
import com.mqtt.admin.db_entity.Message;
import com.mqtt.admin.helper.MySqlDbExecutor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Slf4j
public class IotListener implements Runnable {
    String broker; // MQTT broker URL
    String clientId;
    String topic; // The topic you want to subscribe to
    boolean exit = false;
    Thread t;
    MySqlDbExecutor executor;

    public void stop() {
        this.exit = true;
    }

    public IotListener(String topic, String clientId, String broker) {
        this.topic = topic;
        this.clientId = clientId;
        this.broker = broker;
        this.executor = new MySqlDbExecutor();
        t = new Thread(this, clientId);
        log.info("New thread: " + t);
        t.start();
    }

    @Override
    public void run() {
        try {
            IMqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true); // Set other options as needed

            // Connect to the MQTT broker
            client.connect(options);
            log.info("[CONNECT] IOT:" + clientId + ",Topic:" + topic);
            // Subscribe to the topic
            client.subscribe(topic, (topicName, message) -> {

                // This is the callback for received messages
                String payload = new String(message.getPayload());

                try {
                    JSONObject object = JSONObject.parseObject(payload);
                    Boolean alert = (Boolean) object.get("alert");
                    String iotId = (String) object.get("iotId");

                    Message msg = new Message();
                    msg.setPayload(payload);
                    msg.setAlert(alert);
                    msg.setTopic(topic);

                    Iot iot = new Iot();
                    iot.setIotId(iotId);
                    msg.setIot(iot);

                    executor.insertMessage(msg);
                    log.info("[MQTT MESSAGE] IOT:" + clientId + ",Topic:" + topic + "Payload:" + payload);

                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.warn("Insertion of message canceled due to invalid format of message");
                }
            });
            while (true) {
                Thread.sleep(5000);
                if (this.exit) {
                    log.info("[DISCONNECT] IOT:" + clientId + ",Topic: " + topic);
                    client.disconnect();
                    client.close();
                    break;
                }
            }
        } catch (MqttException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
