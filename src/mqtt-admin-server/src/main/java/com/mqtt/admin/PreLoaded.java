package com.mqtt.admin;

import com.alibaba.fastjson2.JSONObject;
import com.mqtt.admin.db_entity.*;
import com.mqtt.admin.entity.Category;
import com.mqtt.admin.entity.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.*;
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

    public static Timestamp generateRandomDate(int startYear, int endYear) {
        Random random = new Random();

        // Generate a random year within the specified range
        int year = startYear + random.nextInt(endYear - startYear + 1);

        // Generate a random month (0-11)
        int month = random.nextInt(12);

        // Create a calendar instance and set the year and month
        Calendar calendar = new GregorianCalendar(year, month, 1);

        // Get the maximum day of the month to ensure a valid day is generated
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Generate a random day within the month
        int day = random.nextInt(maxDay) + 1;

        // Set the generated year, month, and day
        calendar.set(year, month, day);

        // Return the generated date
        return new Timestamp(calendar.getTime().getTime());
    }

    public void run(String... args) {
        for (int k = 0; k < 20; k++) {
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

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Timestamp randomDate = generateRandomDate(2023, 2023);
                            int value = rand.nextInt(100);

                            Msg payload = new Msg();
                            payload.setIotId(iotId);
                            payload.setInfo("IOT Data " + sdf.format(randomDate));
                            payload.setValue(String.valueOf(value));
                            payload.setAlert(value > 80);
                            rand.nextFloat();
                            //根据杭州经纬度随机生成设备位置信息
                            payload.setLng(119.9 + rand.nextFloat() * 0.6);
                            payload.setLat(30.1 + rand.nextFloat() * 0.4);
                            payload.setTimestamp(randomDate.getTime());
                            message.setCreateTime(randomDate);
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
