package com.mqtt.admin;

import com.alibaba.fastjson2.JSONObject;
import com.mqtt.admin.db_entity.*;
import com.mqtt.admin.entity.Category;
import com.mqtt.admin.entity.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class PreLoaded {
    private static final Logger logger = Logger.getLogger(PreLoaded.class.getName());
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private IotRepository iotRepository;
    @Autowired
    private TopicRepository topicRepository;

    private Random rand = new Random();

    public void run(String... args) {
        for (int k = 0; k < 100; k++) {
            try {
                String iotId = "iot" + (k + 1);
                String iotName = "IOT " + (k + 1);
                String iotInfo = "IOT " + (k + 1) + " Info";
                String topicPrefix = "topic";
                Optional<Iot> opIot = iotRepository.findById(iotId);
                if (opIot.isEmpty()) {
                    Iot iot = new Iot(
                            iotId,
                            iotName,
                            iotInfo,
                            Category.DEFAULT
                    );
                    iotRepository.save(iot);

                    for (int i = 0; i < 10; i++) {
                        opIot = iotRepository.findById(iot.getIotId());
                        if (opIot.isPresent()) {
                            topicRepository.save(new Topic(topicPrefix + iotId + i, opIot.get()));

                            Message message = new Message();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date now = new Date();
                            int value = rand.nextInt(100);

                            Msg payload = new Msg();
                            payload.setIotId(iotId);
                            payload.setInfo("IOT Data " + sdf.format(now));
                            payload.setValue(String.valueOf(value));
                            payload.setAlert(value > 80);
                            rand.nextFloat();
                            //根据杭州经纬度随机生成设备位置信息
                            payload.setLng(119.9 + rand.nextFloat() * 0.6);
                            payload.setLat(30.1 + rand.nextFloat() * 0.4);
                            payload.setTimestamp(now.getTime());

                            message.setAlert(payload.isAlert());
                            message.setPayload(JSONObject.toJSONString(payload));
                            message.setTopic(topicPrefix + iotId + i);
                            message.setIot(opIot.get());
                            messageRepository.save(message);
                        }

                    }
                    logger.info("Insert" + iot);
                }
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
    }

}
