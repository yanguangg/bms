package com.example.BMS.properties;

import com.example.BMS.entity.Rule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "bms-rules")
@Data
public class RulesProperties {
    private List<Rule> rules;
}
