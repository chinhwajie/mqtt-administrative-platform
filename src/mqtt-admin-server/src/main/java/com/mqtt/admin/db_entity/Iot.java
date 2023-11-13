package com.mqtt.admin.db_entity;

import java.util.List;

import com.mqtt.admin.entity.Category;
import com.mqtt.admin.entity.Msg;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Iot {
    @Id
    private String iotId;
    private String name;
    private String info;

    @OneToMany(mappedBy = "iot", cascade = CascadeType.REMOVE)
    private List<Topic> topics;

    @OneToMany(mappedBy = "iot", cascade = CascadeType.REMOVE)
    private List<Message> messages;

    private Category category;

    public Iot(String iotId, String name, String info, Category category) {
        this.iotId = iotId;
        this.name = name;
        this.info = info;
        this.category = category;
    }
}
