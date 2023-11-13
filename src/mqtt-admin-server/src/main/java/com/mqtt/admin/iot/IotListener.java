package com.mqtt.admin.iot;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class IotListener implements Runnable {
    String broker; // MQTT broker URL
    String clientId;
    String topic; // The topic you want to subscribe to
    boolean exit = false;
    Thread t;

    public void stop() {
        this.exit = true;
    }

    public void restart() {
        this.t.start();
    }

    public IotListener(String topic, String clientId, String broker) {
        this.topic = topic;
        this.clientId = clientId;
        this.broker = broker;
        t = new Thread(this, clientId);
        System.out.println("New thread: " + t);
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
            System.out.println("Connection successfully: " + clientId);
            // Subscribe to the topic
            client.subscribe(topic, (topicName, message) -> {
                // This is the callback for received messages
                String payload = new String(message.getPayload());
                System.out.println("Received message on topic " + topicName + ": " + payload);
            });
            while (true) {
                Thread.sleep(5000);
                if (this.exit) {
                    System.out.println("Disconnect");
                    client.disconnect();
                    client.close();
                    break;
                }
            }
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
