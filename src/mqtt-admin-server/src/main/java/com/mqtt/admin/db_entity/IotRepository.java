package com.mqtt.admin.db_entity;

import com.mqtt.admin.entity.CountIotGroupByCategory;
import com.mqtt.admin.entity.SRB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IotRepository extends JpaRepository<Iot, String> {
    @Query("select count(i.iotId) from Iot i")
    Integer countAll();

    @Query(value = "select new com.mqtt.admin.entity.CountIotGroupByCategory(count(i.iotId), i.category) from Iot i group by i.category")
    List<CountIotGroupByCategory> countIotGroupByCategory();
}
