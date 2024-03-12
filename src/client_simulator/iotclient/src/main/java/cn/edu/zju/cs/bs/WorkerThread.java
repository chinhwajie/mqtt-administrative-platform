package cn.edu.zju.cs.bs;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.alibaba.fastjson.JSONObject;

public class WorkerThread extends Thread {
    private boolean running = true;
    private String iotId;
    private String mqttServer;
    private String topic;

    public void run() {
        try {
            String content;
            int qos = 2;
            MemoryPersistence persistence = new MemoryPersistence();

            Random rand = new Random();

            MqttClient mqttClient = new MqttClient(mqttServer, iotId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + mqttServer);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
            while (running) {
                //随机等待10秒
                int interval = rand.nextInt(10);
                Thread.sleep(interval * 1000);

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date now = new Date();
                int value = rand.nextInt(100);
                IOTMessage msg = new IOTMessage();
                msg.setIotId(iotId);
                msg.setInfo("Device Data " + sdf.format(now));
                msg.setValue(value);
                //超过80告警
                msg.setAlert(value > 80);
                rand.nextFloat();
                //根据杭州经纬度随机生成设备位置信息
                msg.setLng(119.9 + rand.nextFloat() * 0.6);
                msg.setLat(30.1 + rand.nextFloat() * 0.4);
                msg.setTimestamp(now.getTime());
                content = JSONObject.toJSONString(msg);
                System.out.println("[PUBLISHING]: " + content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                mqttClient.publish(topic, message);
                System.out.println("[PUBLISHED] Topic: " + topic);
            }
            mqttClient.disconnect();
            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getMqttServer() {
        return mqttServer;
    }

    public void setMqttServer(String mqttServer) {
        this.mqttServer = mqttServer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}