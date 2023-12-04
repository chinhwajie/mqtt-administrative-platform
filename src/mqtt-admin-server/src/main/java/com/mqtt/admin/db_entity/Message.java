package com.mqtt.admin.db_entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String topic;
    private boolean alert;
    // @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Column(name="create_time",columnDefinition="TIMESTAMP",insertable = true,updatable = true)
    private Timestamp createTime;

    @ManyToOne
    private Iot iot;
    @Column(columnDefinition = "text")
    private String payload;
}
