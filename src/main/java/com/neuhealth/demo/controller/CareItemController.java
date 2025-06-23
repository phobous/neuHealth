package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.service.ICareItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/careItem")
public class CareItemController {

    @Autowired
    private ICareItemService careItemService;

    /**
     * 一、查询护理项目信息列表（可选按状态和名称模糊查询）
     */
    @GetMapping("/list")
    public Map<String, Object> listCareItems(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String name) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CareItem> items = careItemService.list(status, name);
            result.put("isOk", true);
            result.put("msg", "查询成功");
            result.put("items", items);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 二、添加护理项目
     */
    @PostMapping("/add")
    public Map<String, Object> addCareItem(@RequestBody CareItem item) {
        Map<String, Object> result = new HashMap<>();
        try {
            careItemService.addItem(item);
            result.put("isOk", true);
            result.put("msg", "添加成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "添加失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 三、修改护理项目（若状态改为停用，自动剔除护理级别绑定项）
     */
    @PostMapping("/update")
    public Map<String, Object> updateCareItem(@RequestBody CareItem item) {
        Map<String, Object> result = new HashMap<>();
        try {
            careItemService.updateItem(item);
            result.put("isOk", true);
            result.put("msg", "修改成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "修改失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 四、逻辑删除护理项目（若存在于级别中同时移除）
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteCareItem(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            careItemService.logicDelete(id);
            result.put("isOk", true);
            result.put("msg", "逻辑删除成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "删除失败: " + e.getMessage());
        }
        return result;
    }
}
