package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.service.ICareLevelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/careLevelItem")
public class CareLevelItemController {

    @Autowired
    private ICareLevelItemService careLevelItemService;

    /**
     * 获取当前护理级别已绑定的护理项目列表
     */
    @GetMapping("/bound")
    public Map<String, Object> getBoundItems(@RequestParam Integer levelId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CareItem> items = careLevelItemService.getBoundItems(levelId);
            result.put("isOk", true);
            result.put("msg", "查询已绑定项目成功");
            result.put("items", items);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取当前护理级别可添加的护理项目列表
     */
    @GetMapping("/available")
    public Map<String, Object> getAvailableItems(@RequestParam Integer levelId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CareItem> items = careLevelItemService.getAvailableItems(levelId);
            result.put("isOk", true);
            result.put("msg", "查询可添加项目成功");
            result.put("items", items);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 将护理项目添加到某护理级别
     */
    @PostMapping("/add")
    public Map<String, Object> addItemsToLevel(@RequestBody Map<String, Object> reqBody) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer levelId = (Integer) reqBody.get("levelId");
            List<Integer> itemIds = (List<Integer>) reqBody.get("itemIds");
            boolean success = careLevelItemService.addItemsToLevel(levelId, itemIds);
            result.put("isOk", success);
            result.put("msg", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "添加失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 从某护理级别中移除一个护理项目
     */
    @DeleteMapping("/remove")
    public Map<String, Object> removeItemFromLevel(@RequestParam Integer levelId, @RequestParam Integer itemId) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = careLevelItemService.removeItemFromLevel(levelId, itemId);
            result.put("isOk", success);
            result.put("msg", success ? "移除成功" : "移除失败");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "移除失败: " + e.getMessage());
        }
        return result;
    }
}
