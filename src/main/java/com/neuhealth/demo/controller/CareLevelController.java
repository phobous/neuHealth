package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.CareLevel;
import com.neuhealth.demo.service.ICareLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careLevel")
public class CareLevelController {

    @Autowired
    private ICareLevelService careLevelService;

    // 一、根据状态查询护理级别信息列表（默认启用）
    @GetMapping("/list")
    public Map<String, Object> listCareLevels(@RequestParam(defaultValue = "启用") String status) {
        Map<String, Object> result = new HashMap<>();
        List<CareLevel> levels = careLevelService.listByStatus(status);
        result.put("isOk", true);
        result.put("msg", "查询成功");
        result.put("data", levels);
        return result;
    }

    // 二、修改护理级别状态
    @PostMapping("/updateStatus")
    public Map<String, Object> updateStatus(@RequestParam int id, @RequestParam String status) {
        Map<String, Object> result = new HashMap<>();
        try {
            careLevelService.updateStatus(id, status);
            result.put("isOk", true);
            result.put("msg", "状态更新成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "状态更新失败: " + e.getMessage());
        }
        return result;
    }

    // 三、添加护理级别
    @PostMapping("/add")
    public Map<String, Object> addCareLevel(@RequestBody CareLevel level) {
        Map<String, Object> result = new HashMap<>();
        try {
            careLevelService.addLevel(level);
            result.put("isOk", true);
            result.put("msg", "添加成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "添加失败: " + e.getMessage());
        }
        return result;
    }

    // 护理项目配置功能建议分开写在 CareLevelProjectController 中，如需我也可补充
}
