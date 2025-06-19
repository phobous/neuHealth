package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.Bed;
import com.neuhealth.demo.service.IBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bed")
public class BedController {
    private final IBedService iBedService;

    @Autowired
    public BedController(IBedService iBedService) {
        this.iBedService = iBedService;
    }

    //遍历床位
    @GetMapping("/getAllBeds")
    public Map getAllBeds() {
        Map result = new HashMap<>();
        List<Bed> beds = iBedService.getAllBeds();
        if(beds != null){
        result.put("beds",beds);
        result.put("if",1);
            }else {
            result.put("if",0);
        }
        return result;
    };
//    //查找某个床位
//    @GetMapping("/selectBed")
//    public Map selectBed
    //统计不同状态床位数
    @GetMapping("/getBedStatistics")
    public Map getBedStatistics(){
        Map result = new HashMap<>();
        List< Map<String, Object> >stats = iBedService.getBedStatistics();
        if(stats != null){
            result.put("beds",stats);
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    };
}
