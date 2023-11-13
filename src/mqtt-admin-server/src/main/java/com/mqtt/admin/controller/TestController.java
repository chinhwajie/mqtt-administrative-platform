package com.mqtt.admin.controller;

import com.mqtt.admin.entity.Device;
import com.mqtt.admin.entity.DeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TestController {
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/hi/${id}")
    public Device hi(@PathVariable("id") Integer id) {
        deviceRepository.save(new Device(null, "name", "model", "brand", "1231293129837"));
        Optional<Device> device = deviceRepository.findById(id);
        System.out.println(device.get().toString());
        return device.get();
    }
}
