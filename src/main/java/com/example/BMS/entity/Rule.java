package com.example.BMS.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class Rule implements Serializable {

    private int id;

    private int warnId;

    private String name;

    private String batteryType;

    private double warningRule;

    private int warningLevel;

}
