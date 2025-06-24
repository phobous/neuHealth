package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.Caregiver;

import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.service.ICaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/caregiver")
public class CaregiverController {

    @Autowired
    private ICaregiverService caregiverService;

    /*得到所有健康管家列表
    @GetMapping("/all")
    @ResponseBody
    public Map<String, Object> getAllCaregivers() {
        List<Caregiver> caregivers = caregiverService.searchCaregiversByName(""); // 空字符串查所有

        Map<String, Object> response = new HashMap<>();
        response.put("isOk", true);
        response.put("count", caregivers.size());
        response.put("data", caregivers);
        response.put("msg", "查询所有护理人员成功");
        return response;
    }
    */

    // 查询所有健康管家，支持模糊搜索
    @GetMapping("/search")
    @ResponseBody
    public Map<String, Object> searchCaregivers(@RequestParam(required = false, defaultValue = "") String name) {
        List<Caregiver> caregivers = caregiverService.searchCaregiversByName(name);
        Map<String, Object> response = new HashMap<>();
        response.put("isOk", true);
        response.put("count", caregivers.size());
        response.put("data", caregivers);
        response.put("msg", "模糊查询护理人员成功");
        return response;
    }

/*
    //通过caregiverId得到对应客户列表
    @GetMapping("/{caregiverId}/clients")
    @ResponseBody
    public Map<String, Object> getClientsByCaregiver(@PathVariable int caregiverId) {
        List<Object> clients = caregiverService.getClientsByCaregiver(caregiverId);

        Map<String, Object> response = new HashMap<>();
        if (clients == null || clients.isEmpty()) {
            response.put("isOk", false);
            response.put("count", 0);
            response.put("data", null);
            response.put("msg", "没有找到对应的客户");
        } else {
            response.put("isOk", true);
            response.put("count", clients.size());
            response.put("data", clients);
            response.put("msg", "根据护理人员ID查询客户成功");
        }
        return response;
    }*/
    //通过caregiverId得到对应客户列表（支持模糊搜索）
    @GetMapping("/clients/byCaregiver")
    @ResponseBody
    public Map<String, Object> getClientsByCaregiver(
            @RequestParam int caregiverId,
            @RequestParam(required = false, defaultValue = "") String name) {
        List<Client> clients = caregiverService.getClientsByCaregiverIdAndName(caregiverId, name);
        return Map.of(
                "isOk", true,
                "count", clients.size(),
                "data", clients,
                "msg", "查询成功"
        );
    }

    // 分配客户给护理人员
    @PostMapping("/{caregiverId}/clients/{clientId}/assign")
    @ResponseBody
    public Map<String, Object> assignClientToCaregiver(@PathVariable int caregiverId, @PathVariable int clientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            caregiverService.assignClientToCaregiver(caregiverId, clientId);
            response.put("isOk", true);
            response.put("msg", "客户分配成功");
        } catch (Exception e) {
            response.put("isOk", false);
            response.put("msg", "客户分配失败: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/clients/unassigned")
    @ResponseBody
    public Map<String, Object> getUnassignedClients(@RequestParam(required = false, defaultValue = "") String name) {
        List<Client> clients = caregiverService.getUnassignedClientsByName(name);
        return Map.of(
                "isOk", true,
                "count", clients.size(),
                "data", clients,
                "msg", "查询成功"
        );
    }

    // 从护理人员移除客户（注意前端测试时不要用get，是delete
    @DeleteMapping("/{caregiverId}/clients/{clientId}/remove")
    @ResponseBody
    public Map<String, Object> removeClientFromCaregiver(@PathVariable int caregiverId, @PathVariable int clientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            caregiverService.removeClientFromCaregiver(caregiverId, clientId);
            response.put("isOk", true);
            response.put("msg", "客户移除成功");
        } catch (Exception e) {
            response.put("isOk", false);
            response.put("msg", "客户移除失败: " + e.getMessage());
        }
        return response;
    }
}
