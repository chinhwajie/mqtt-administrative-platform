package com.mqtt.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageCountPerDay {
    public Long messageCount;
    public Date date;
}
