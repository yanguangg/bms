package com.example.BMS.properties;

import com.example.BMS.entity.Vehicle;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "bms-vehicles")
@Data
public class VehiclesProperties {

    private List<Vehicle> vehicles;

}
