package com.neuhealth.demo.controller;


import com.neuhealth.demo.domain.BedUsageRecord;
import com.neuhealth.demo.service.IBedUsageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bedUsageRecord")
public class BedUsageRecordController {
    private final IBedUsageRecordService iBedUsageRecordService;

    @Autowired
    public BedUsageRecordController(IBedUsageRecordService iBedUsageRecordService) {
        this.iBedUsageRecordService = iBedUsageRecordService;
    }

    @GetMapping("/list")
    public Map getAllRecords(){
        Map result = new HashMap();
        List<BedUsageRecord> usageRecords = iBedUsageRecordService.getAllBedUsageRecords();
        if(usageRecords!=null){
            System.out.println(usageRecords);
            System.out.println(iBedUsageRecordService.getTotalCount());
            result.put("usageRecords",usageRecords);
            result.put("total",iBedUsageRecordService.getTotalCount());
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
            result.put("total",iBedUsageRecordService.getTotalCount());
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    };

    //调换
    @PostMapping("/swapBed")
    public Map swapBed(@RequestBody Map<String, Object> params){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaparams");
        Integer id=(int) params.get("id");
        int clientId=(int)params.get("clientId");
        int oldBedId = (int)params.get("oldBedId") ;
        int newBedId= (int)params.get("newBedId");
        System.out.println("——————收到的数据————"+id+clientId+oldBedId+newBedId);
        Map result = new HashMap();
        if(iBedUsageRecordService.swapBed(id,clientId, oldBedId, newBedId)){
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    };
    //修改
    @PostMapping("/updateCheckOutDate")
    public Map updateCheckOutDate(@RequestBody Map<String, Object> params){
        Integer recordId = (Integer) params.get("recordId");
        Long checkOutTimestamp = (Long) params.get("checkOutDate");
        System.out.println("————接收到的时间————"+checkOutTimestamp);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai")); // 设置为CST时区
        calendar.setTimeInMillis(checkOutTimestamp);
        // 获取CST时间对应的UTC时间戳（减去时区偏移量）
        long utcTimestamp = calendar.getTimeInMillis() + calendar.getTimeZone().getOffset(calendar.getTimeInMillis());
        Date checkOutDate = new Date(utcTimestamp);
        System.out.println("id+date："+recordId+checkOutDate);
        Map result =new HashMap();
        if(iBedUsageRecordService.updateCheckOutDate(recordId, checkOutDate)){
            result.put("if",1);
        }else {
            result.put("if",0);
        }
        return result;
    }
}
