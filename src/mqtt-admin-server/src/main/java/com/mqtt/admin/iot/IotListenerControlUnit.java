package com.mqtt.admin.iot;

import com.mqtt.admin.exception_handler.exception.NotFoundException;

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
        IotListener iotListener = iotListenersThreads.get(topic + iotId);
        if (iotListener == null) throw new NotFoundException("Requested IotListener not found.");
        iotListenersThreads.remove(topic + iotId).stop();
    }

    public static Integer activeConnection() {
        return iotListenersThreads.size();
    }
}
