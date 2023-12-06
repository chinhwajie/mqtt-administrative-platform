package com.mqtt.admin.config;

import com.mqtt.admin.db_entity.IotRepository;
import com.mqtt.admin.db_entity.TopicRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Service
public class ShutdownService implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // Code to be executed before shutdown
        System.out.println("Set false to all connection states in database before shutdown...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://10.73.103.130:3306/mqtt", "dev", "dev");
            PreparedStatement ps = connection.prepareStatement("update iot set connection_state = false");
            int check = ps.executeUpdate();
            System.out.println("[SHUTDOWN] iot table affected row: " + check);
            ps = connection.prepareStatement("update topic set connection_state = false");
            check = ps.executeUpdate();
            System.out.println("[SHUTDOWN] topic table affected row: " + check);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
