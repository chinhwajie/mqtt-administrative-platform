package com.mqtt.admin.db_entity;

import com.mqtt.admin.entity.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import com.alibaba.fastjson2.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Category iotCategory;

    @ManyToOne
    private Iot iot;
    @Column(columnDefinition = "blob")
    private String payload;
}
