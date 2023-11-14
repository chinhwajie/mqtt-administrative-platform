package com.mqtt.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IotActivityState {
    private Integer online;
    private Integer offline;
}
