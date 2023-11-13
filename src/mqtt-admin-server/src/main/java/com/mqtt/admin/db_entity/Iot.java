package com.mqtt.admin.db_entity;

import java.util.List;

import com.mqtt.admin.entity.Category;
import com.mqtt.admin.entity.Msg;

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

    @OneToMany(mappedBy = "iot")
    private List<Topic> topics;

    private Category category;
    private boolean online;
}
