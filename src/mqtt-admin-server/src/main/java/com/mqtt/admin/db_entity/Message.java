package com.mqtt.admin.db_entity;

import com.mqtt.admin.entity.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import com.alibaba.fastjson2.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    private Timestamp createTime;

    @ManyToOne
    private Iot iot;
    @Column(columnDefinition = "text")
    private String payload;
}
