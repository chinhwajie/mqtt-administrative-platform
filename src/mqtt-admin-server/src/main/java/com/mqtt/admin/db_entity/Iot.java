package com.mqtt.admin.db_entity;

import java.sql.Timestamp;
import java.util.List;

import com.mqtt.admin.entity.Category;
import com.mqtt.admin.entity.Msg;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenerationTime;


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
    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    private Timestamp createTime;

    @OneToMany(mappedBy = "iot", cascade = CascadeType.REMOVE)
    private List<Topic> topics;

    @OneToMany(mappedBy = "iot", cascade = CascadeType.REMOVE)
    private List<Message> messages;

    private Category category;
    private Boolean connectionState;

    public Iot(String iotId, String name, String info, Category category) {
        this.iotId = iotId;
        this.name = name;
        this.info = info;
        this.category = category;
        this.connectionState = false;
    }

    public String getCreateTime() {
        return createTime.toString();
    }
}
