package com.mqtt.admin.helper;

import com.mqtt.admin.db_entity.Message;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class MySqlDbExecutor {
    private final MySqlConnector connector;

    public MySqlDbExecutor() {
        this.connector = new MySqlConnector();
    }

    public void insertMessage(Message message) {
        try {
            PreparedStatement ps = this.connector.getConnection().prepareStatement(
                    "INSERT INTO message (alert, iot_iot_id, payload, topic) VALUES (?, ?, ?, ?);",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, message.isAlert());
            ps.setString(2, message.getIot().getIotId());
            ps.setString(3, message.getPayload());
            ps.setString(4, message.getTopic());

            int rowAffected = ps.executeUpdate();

            // Get the auto-generated ID of the inserted record
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next() && rowAffected > 0) {
                log.debug("Message " + rs.getString(1) + " is inserted");
            } else {
                log.error("Message " + message.getPayload() + " should be inserted");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public boolean isKeyTopicValid(String topic, String iotId) {
        try {
            PreparedStatement ps = this.connector.getConnection().prepareStatement("select * from topic where topic=? and iot_iot_id=?");
            ps.setString(1, topic);
            ps.setString(2, iotId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                log.debug("Key " + topic + "&" + iotId + " is VALID");
                return true;
            } else {
                log.debug("Key " + topic + "&" + iotId + " is INVALID");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
