package com.example.BMS.service;

import com.example.BMS.entity.Vehicle;

import java.util.List;
import java.util.Map;

public interface VehicleService {

    /**
     * 根据id获取车辆信息
     * @param carId
     * @return
     */
    String getById(int carId);
}
