package com.mqtt.admin.entity;

import com.mqtt.admin.db_entity.Iot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Vector;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IotTrace {
    private Iot iot;
    private Vector<List<Double>> coordinates;
}
