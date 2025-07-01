package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.ClientCareConfig;
import com.neuhealth.demo.service.ICareConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientCareConfig")
public class CareConfigController {

    @Autowired
    private ICareConfigService clientCareConfigService;


    //找到客户对应护理项目（以及状态的计算）
    @GetMapping("/byClientId")
    public Map<String, Object> getCareConfigByClientId(
            @RequestParam int clientId,
            @RequestParam(required = false) String name) {

        // 如果 name 为 null，则将其设置为空字符串
        if (name == null) {
            name = "";
        }

        Map<String, Object> response = new HashMap<>();
        try {
            // 调用 Service 层方法，获取护理配置列表
            List<ClientCareConfig> configs = clientCareConfigService.getByClientId(clientId, name);

            if (configs == null || configs.isEmpty()) {
                response.put("isOk", false);
                response.put("msg", "未找到对应的护理配置");
                response.put("data", null);
                response.put("count", 0);
            } else {
                // 构建返回的响应
                response.put("isOk", true);
                response.put("msg", "查询成功");
                response.put("data", configs);
                response.put("count", configs.size());
            }
        } catch (Exception e) {
            response.put("isOk", false);
            response.put("msg", "查询失败: " + e.getMessage());
        }

        return response;
    }

    //添加项目
    @PostMapping("/save")
    public Map<String, Object> saveClientCareConfig(
            @RequestParam Integer clientId,
            @RequestParam Integer itemId,
            @RequestParam Integer quantity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, Object> response = new HashMap<>();
        try {
            clientCareConfigService.saveClientCareConfig(clientId, itemId, endDate, quantity);
            response.put("isOk", true);
            response.put("msg", "护理服务添加成功");
        } catch (Exception e) {
            response.put("isOk", false);
            response.put("msg", "添加失败: " + e.getMessage());
        }
        return response;
    }


    //客户未购买的项目列表（带名字查询）
    @GetMapping("/availableForClient/{clientId}")
    public List<CareItem> getAvailableItemsForClient(
            @PathVariable int clientId,
            @RequestParam(required = false) String name) {
        return clientCareConfigService.getEnabledItemsExcludingClientByName(clientId, name);
    }

    //续费，configId就是数据库里的id字段
    @PutMapping("/renew")
    public Map<String, Object> renewService(
            @RequestParam int configId,
            @RequestParam int addQuantity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newEndDate) {

        Map<String, Object> response = new HashMap<>();
        try {
            clientCareConfigService.renewService(configId, addQuantity, newEndDate);
            response.put("isOk", true);
            response.put("msg", "续费成功");
        } catch (Exception e) {
            response.put("isOk", false);
            response.put("msg", "续费失败: " + e.getMessage());
        }
        return response;
    }


    //删除护理项目
    @DeleteMapping("/{configId}")
    public Map<String, Object> removeService(@PathVariable int configId) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = clientCareConfigService.removeService(configId);
            if (result > 0) {
                response.put("isOk", true);
                response.put("msg", "护理配置项移除成功");
            } else {
                response.put("isOk", false);
                response.put("msg", "未找到对应护理配置项或移除失败");
            }
        } catch (Exception e) {
            response.put("isOk", false);
            response.put("msg", "移除失败: " + e.getMessage());
        }
        return response;
    }
}

