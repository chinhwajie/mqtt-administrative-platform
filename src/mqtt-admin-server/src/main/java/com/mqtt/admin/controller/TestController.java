package com.mqtt.admin.controller;

import com.mqtt.admin.entity.Device;
import com.mqtt.admin.entity.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private DeviceRepository deviceRepository;
    @GetMapping("/hi")
    public List<Device> hi() {
        deviceRepository.save(new Device(null, "name", "model", "brand", "1231293129837"));
        List<Device> devices = (List<Device>) deviceRepository.findAll();
        for (Device device : devices) {
            System.out.println(device.getName());
        }
        return devices;
    }
}
