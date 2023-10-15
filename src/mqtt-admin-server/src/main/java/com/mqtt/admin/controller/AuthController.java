package com.mqtt.admin.controller;

import com.mqtt.admin.config.MqttUserDetails;
import com.mqtt.admin.config.MqttUserDetailsService;
import com.mqtt.admin.entity.User;
import com.mqtt.admin.entity.UserRepository;
import com.mqtt.admin.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/login")
    public String login(@RequestBody LoginBody body) {
        System.out.println("Username: " + body.username + ", password: " + body.password);
        Optional<User> user = userRepository.findByUsername(body.username);
        if (user.isPresent()) {
            return Jwt.generateJwt(user.get());
        }
        return "No token.";
    }
}
