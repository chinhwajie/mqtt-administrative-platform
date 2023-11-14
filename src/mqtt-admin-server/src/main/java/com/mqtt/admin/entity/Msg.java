package com.mqtt.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Msg {
    // 设备ID
    private String iotId;
    // 上报信息
    private String info;
    // 设备数据
    private String value;
    // 是否告警，0-正常，1-告警
    private boolean alert;
    // 设备位置，经度
    private double lng;
    // 设备位置，纬度
    private double lat;
    // 上报时间，ms
    private long timestamp;
}