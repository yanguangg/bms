package com.example.BMS.service;

import com.example.BMS.dto.WarnDTO;
import com.example.BMS.entity.Rule;

import java.util.List;

public interface RuleService {


    /**
     * 获取规则信息
     * @param warnDTO
     * @return
     */
    List<Rule> getRules(WarnDTO warnDTO);
}
