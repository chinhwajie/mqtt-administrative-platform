package com.mqtt.admin.db_entity;

import com.mqtt.admin.entity.CountIotGroupByCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IotRepository extends JpaRepository<Iot, String>, JpaSpecificationExecutor<Iot> {
    @Query("select count(i.iotId) from Iot i")
    Integer countAll();

    @Query(value = "select new com.mqtt.admin.entity.CountIotGroupByCategory(count(i.iotId), i.category) from Iot i group by i.category")
    List<CountIotGroupByCategory> countIotGroupByCategory();

    @Query("select count(i.connectionState) from Iot i where i.connectionState = true")
    Integer activeDevicesCount();

    @Modifying
    @Query("UPDATE Iot t SET t.connectionState = false")
    void setFalseAllConnectionState();
}
