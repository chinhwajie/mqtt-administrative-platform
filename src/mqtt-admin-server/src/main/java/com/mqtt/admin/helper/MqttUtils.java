package com.mqtt.admin.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.admin.db_entity.Message;
import com.mqtt.admin.entity.Coordinate;
import com.mqtt.admin.entity.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class MqttUtils {
    public static List<Double> extractCoordinate(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        Double lat = jsonNode.get("lat").asDouble();
        Double lng = jsonNode.get("lng").asDouble();
        // System.out.println(payload.substring(0, 20) + ", " + lat + ", " + lng);
        return List.of(lng, lat);
    }

    public static Comparator<Message> messageComparator = Comparator.comparing(Message::getCreateTime);
}
