package com.example.BMS.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Vehicle {

    private int id;

    private String vid;

    private String batteryType;

    private int totalMileage;

    private int batteryHealth;

}
