package com.mqtt.admin.config;

import com.mqtt.admin.entity.User;
import com.mqtt.admin.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MqttUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public MqttUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> u = userRepository.findByUsername(username);
        if (u.isEmpty()) throw new UsernameNotFoundException(username + " not found.");
        return new MqttUserDetails(u.get());
    }
}
