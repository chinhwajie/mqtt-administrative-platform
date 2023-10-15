package com.mqtt.admin.entity;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void saveUserTest() {
        User u = new User("chinhwajie", "jiejie02.chj@gmail.com", passwordEncoder.encode("123"), "Chin", "Hwa Jie", "18658162086");
        userRepository.save(u);
        roleRepository.save(new Role("USER", u));
    }

}
