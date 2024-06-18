package com.example.BMS.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarnVO {

    private int id;

    private String batteryType;

    private String warnName;

    private int warnLevel;

}
