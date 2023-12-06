package com.mqtt.admin.db_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@IdClass(Topic.TopicId.class)
public class Topic {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TopicId implements Serializable {
        private String topic;
        private Iot iot;
    }

    public Topic(String topic, Iot iot) {
        this.topic = topic;
        this.iot = iot;
        this.connectionState = false;
    }

    public Topic(String topic) {
        this.topic = topic;
    }

    @Id
    private String topic;

    @Id
    @ManyToOne
    private Iot iot;

    private Boolean connectionState;
}
