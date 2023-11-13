package com.mqtt.admin.controller;

import com.mqtt.admin.db_entity.Iot;
import com.mqtt.admin.db_entity.IotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TestController {
    @Autowired
    private IotRepository deviceRepository;

    @GetMapping("/hi/{id}")
    public Iot hi(@PathVariable("id") Integer id) {
        // deviceRepository.save(new Iot("MyIot", "MyIotInfo", null, true));
        Optional<Iot> device = deviceRepository.findById(id);
        System.out.println(device.get().toString());
        return device.get();
    }
}
