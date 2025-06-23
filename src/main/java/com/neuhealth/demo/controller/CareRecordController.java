package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.CareRecord;
import com.neuhealth.demo.domain.CareRecordDTO;
import com.neuhealth.demo.service.ICareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/careRecord")
public class CareRecordController {

    private final ICareRecordService careRecordService;

    @Autowired
    public CareRecordController(ICareRecordService careRecordService) {
        this.careRecordService = careRecordService;
    }

    /**
     * 根据客户ID查询护理记录
     */
    @GetMapping("/listByClient")
    public Map<String, Object> listByClient(@RequestParam int clientId) {
         Map<String, Object> result = new HashMap<>();
        try {
            List<CareRecordDTO> records = careRecordService.getByClientId(clientId);
            result.put("isOk", true);
            result.put("msg", "查询护理记录成功");
            result.put("data", records);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 逻辑删除护理记录
     */
    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestParam int recordId) {
        Map<String, Object> result = new HashMap<>();
        try {
            careRecordService.deleteRecord(recordId);
            result.put("isOk", true);
            result.put("msg", "删除护理记录成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "删除失败: " + e.getMessage());
        }
        return result;
    }
}
