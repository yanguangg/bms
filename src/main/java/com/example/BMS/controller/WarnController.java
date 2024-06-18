package com.example.BMS.controller;

import com.example.BMS.dto.WarnDTO;
import com.example.BMS.entity.Rule;
import com.example.BMS.result.Result;
import com.example.BMS.service.RuleService;
import com.example.BMS.service.VehicleService;
import com.example.BMS.vo.WarnVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/warn")
@Tag(name = "预警接口")
public class WarnController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 预警接口
     * @return
     */
    @PostMapping
    @Operation(summary = "上报接口")
    public Result<List<WarnVO>> warnBySingal(@RequestBody List<WarnDTO> warnDTOList) throws JSONException {
        log.info("上报接口：{}",warnDTOList);
        List<WarnVO> warnVOS = new ArrayList<>();
        for (WarnDTO warnDTO : warnDTOList) {
            //根据id获取车辆电池信息
            String keyV = "car_"+warnDTO.getCarId();

            //先在redis中查看有无数据，没有再查找数据库，把结果存入redis
            String battery_type = (String) redisTemplate.opsForValue().get(keyV);
            if(battery_type == null || battery_type == ""){
                battery_type = vehicleService.getById(warnDTO.getCarId());
                redisTemplate.opsForValue().set(keyV,battery_type);
            }
            warnDTO.setBatteryType(battery_type);

            //解析signal信号
            String signal = warnDTO.getSignal();
            JSONObject jsonObject = new JSONObject(signal);
            double mx = jsonObject.optDouble("Mx", Double.NaN);
            double mi = jsonObject.optDouble("Mi", Double.NaN);
            double ix = jsonObject.optDouble("Ix", Double.NaN);
            double ii = jsonObject.optDouble("Ii", Double.NaN);

            //分别计算电流差和电压差
            if(!Double.isNaN(mx) && !Double.isNaN(mi)){
                warnDTO.setWarnId(1);

                //先在redis中查看有无数据，没有再查找数据库，把结果存入redis
                String key = "rule_"+warnDTO.getBatteryType()+"_"+warnDTO.getWarnId();
                List<Rule> rules = (List<Rule>) redisTemplate.opsForValue().get(key);
                if(rules == null || rules.size() == 0){
                    rules = ruleService.getRules(warnDTO);
                    redisTemplate.opsForValue().set(key,rules);
                }
                double md = mx - mi;
                WarnVO warnVO = new WarnVO();
                for (Rule rule : rules) {
                    if(md >= rule.getWarningRule()){
                        warnVO.setWarnLevel(rule.getWarningLevel());
                        warnVO.setWarnName(rule.getName());
                        warnVO.setBatteryType(warnDTO.getBatteryType());
                        warnVO.setId(warnDTO.getCarId());
                        break;
                    }
                }
                if(warnVO.getWarnName() == null){
                    warnVO = WarnVO.builder()
                            .id(-1)
                            .warnName("不报警")
                            .batteryType(warnDTO.getBatteryType())
                            .warnLevel(-1)
                            .build();
                }
                warnVOS.add(warnVO);
            }
            if(!Double.isNaN(ix) && !Double.isNaN(ii)){
                warnDTO.setWarnId(2);
                //先在redis中查看有无数据，没有再查找数据库，把结果存入redis
                String key = "rule_"+warnDTO.getBatteryType()+"_"+warnDTO.getWarnId();
                List<Rule> rules = (List<Rule>) redisTemplate.opsForValue().get(key);
                if(rules == null || rules.size() == 0){
                    rules = ruleService.getRules(warnDTO);
                    redisTemplate.opsForValue().set(key,rules);
                }
                double id = ix - ii;
                WarnVO warnVO = new WarnVO();
                //判断是否报警
                for (Rule rule : rules) {
                    if(id >= rule.getWarningRule()){
                        warnVO.setWarnLevel(rule.getWarningLevel());
                        warnVO.setWarnName(rule.getName());
                        warnVO.setBatteryType(warnDTO.getBatteryType());
                        warnVO.setId(warnDTO.getCarId());
                        break;
                    }
                }
                if(warnVO.getWarnName() == null){
                    warnVO = WarnVO.builder()
                            .id(-1)
                            .warnName("不报警")
                            .batteryType(warnDTO.getBatteryType())
                            .warnLevel(-1)
                            .build();
                }
                warnVOS.add(warnVO);
            }
        }
        return Result.success(warnVOS);
    }

}
