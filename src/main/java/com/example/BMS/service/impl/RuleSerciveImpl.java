package com.example.BMS.service.impl;

import com.example.BMS.dto.WarnDTO;
import com.example.BMS.entity.Rule;
import com.example.BMS.properties.RulesProperties;
import com.example.BMS.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleSerciveImpl implements RuleService {

    @Autowired
    private RulesProperties rulesProperties;

    /**
     * 获取规则
     * @param warnDTO
     * @return
     */
    public List<Rule> getRules(WarnDTO warnDTO) {

        return rulesProperties.getRules().stream()
                .filter(rule -> rule.getWarnId() == warnDTO.getWarnId() && rule.getBatteryType().equals(warnDTO.getBatteryType()) )
                .sorted(Comparator.comparing(Rule::getWarningRule).reversed())
                .collect(Collectors.toList());
    }
}
