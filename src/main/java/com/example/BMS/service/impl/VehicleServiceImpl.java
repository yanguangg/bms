package com.example.BMS.service.impl;

import com.example.BMS.entity.Vehicle;
import com.example.BMS.properties.VehiclesProperties;
import com.example.BMS.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehiclesProperties vehiclesProperties;


    /**
     * 根据id获取车辆信息
     * @param carId
     * @return
     */
    public String getById(int carId) {

        return vehiclesProperties.getVehicles().stream()
                .filter(vehicle -> vehicle.getId() == carId)
                .map(Vehicle::getBatteryType)
                .findFirst()
                .orElse("Unknown");
    }
}
