package com.mqtt.admin.iot;

import java.util.ArrayList;
import java.util.HashMap;

public class IotListenerControlUnit {
    // TODO: Test first in mqtt_test project
    private static final HashMap<String, IotListener> iotListenersThreads;

    static {
        iotListenersThreads = new HashMap<>();
    }

    public static void connect(String topic, String iotId, String broker) {
        IotListener iotListener = new IotListener(topic, iotId, broker);
        iotListenersThreads.put(topic + iotId, iotListener);
    }

    public static void disconnect(String topic, String iotId) {
        iotListenersThreads.remove(topic + iotId).stop();
    }

    public static Integer activeConnection() {
        return iotListenersThreads.size();
    }
}
