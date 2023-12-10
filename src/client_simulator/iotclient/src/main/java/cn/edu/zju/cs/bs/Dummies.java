package cn.edu.zju.cs.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dummies {
    private static final String[] TOPIC_LIST = {
            "home/living_room/temperature",
            "home/kitchen/light",
            "office/meeting_room/security",
            "factory/production_line/machine1",
            "city/traffic/sensor",
            "weather/temperature",
            "sports/football/score",
            "health/heart_rate",
            "finance/stock_price",
            "education/classroom/smartboard"
    };

    private static final int DEVICE_LIST_SIZE = 10;
    private static final int DEVICE_ID_LENGTH = 8;

    public static String getRandomTopic() {
        // Randomly choose a topic for MQTT from a topic list
        Random random = new Random();
        int index = random.nextInt(TOPIC_LIST.length);
        return TOPIC_LIST[index];
    }

    public static List<String> getRandomDeviceList() {
        // Randomly return a list of devices, with very unique device IDs
        List<String> deviceList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < DEVICE_LIST_SIZE; i++) {
            String deviceId = generateRandomDeviceId(random, DEVICE_ID_LENGTH);
            deviceList.add(deviceId);
        }

        return deviceList;
    }

    private static String generateRandomDeviceId(Random random, int length) {
        // Generate a random device ID of the specified length
        StringBuilder deviceIdBuilder = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            deviceIdBuilder.append(characters.charAt(index));
        }

        return deviceIdBuilder.toString();
    }
}
