package com.neuhealth.demo.controller;


import com.neuhealth.demo.domain.BedUsageRecord;
import com.neuhealth.demo.service.IBedUsageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bedUsageRecord")
public class BedUsageRecordController {
    private final IBedUsageRecordService iBedUsageRecordService;

    @Autowired
    public BedUsageRecordController(IBedUsageRecordService iBedUsageRecordService) {
        this.iBedUsageRecordService = iBedUsageRecordService;
    }

    @GetMapping("/getAll")
    public Map getAllRecords(){
        Map result = new HashMap();
        List<BedUsageRecord> usageRecords = iBedUsageRecordService.getAllBedUsageRecords();
        if(usageRecords!=null){
            result.put("usageRecords",usageRecords);
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    };
    //查询
    @PostMapping("/selectRecords")
    public Map getAllRecords(BedUsageRecord bedUsageRecorde){
        Map result = new HashMap();
        List<BedUsageRecord> usageRecords = iBedUsageRecordService.selectBedUsageRecordsList(bedUsageRecorde);
        if(usageRecords!=null){
            result.put("usageRecords",usageRecords);
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    };

    //调换
    @PostMapping("/swapBed")
    public Map swapBed(int clientId, int oldBedId, int newBedId){
        Map result = new HashMap();
        if(iBedUsageRecordService.swapBed(clientId, oldBedId, newBedId)){
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    };
    //修改
    @PostMapping("/updateCheckOutDate")
    public Map updateCheckOutDate(int recordId, Date checkOutDate){
        Map result =new HashMap();
        if(iBedUsageRecordService.updateCheckOutDate(recordId, checkOutDate)){
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    }
}
