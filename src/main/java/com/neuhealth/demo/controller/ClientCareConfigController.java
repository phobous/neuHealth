package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.*;
import com.neuhealth.demo.service.IClientCareConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Tag(name="客户护理配置接口")
@RestController
@RequestMapping("/clientCare")
public class ClientCareConfigController {

    @Autowired
    private IClientCareConfigService clientCareConfigService;
    /** 查询所有客户 */
    @Operation(summary = "查询所有客户")
    @GetMapping("/listAllClients")
    public Map<String, Object> listAllClients() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ClientInfoDTO> clients = clientCareConfigService.getAllClientInfo();
            result.put("isOk", true);
            result.put("msg", "查询所有客户信息成功");
            result.put("data", clients);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }
    /** 根据客户姓名模糊查询 */
        @GetMapping("/listClientsByName")
        public Map<String, Object> listClientsByName(@RequestParam("name") String name) {
            Map<String, Object> result = new HashMap<>();
            try {
                // 调用服务层根据姓名模糊查询客户信息
                List<ClientInfoDTO> clients = clientCareConfigService.getClientsByName(name);
                result.put("isOk", true);
                result.put("msg", "查询客户信息成功");
                result.put("data", clients);
            } catch (Exception e) {
                result.put("isOk", false);
                result.put("msg", "查询失败: " + e.getMessage());
            }
            return result;
        }

    /** 查询客户当前护理配置 */
    @PostMapping("/list")
    public Map<String, Object> getCareConfigList(@RequestParam int clientId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ClientCareConfig> configs = clientCareConfigService.getByClientId(clientId);
            result.put("isOk", true);
            result.put("data", configs);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }
        /** 获取指定护理级别下的护理项目列表 */
        @PostMapping("/levelItems")
        public Map<String, Object> getItemsByCareLevel(@RequestParam int careLevelId) {
            Map<String, Object> result = new HashMap<>();
            try {
                // 调用已有 service 方法或 mapper 查询
                List<CareItem> list = clientCareConfigService.queryItemsByCareLevel(careLevelId);
                result.put("isOk", true);
                result.put("data", list);
            } catch (Exception e) {
                result.put("isOk", false);
                result.put("msg", "查询失败: " + e.getMessage());
            }
            return result;
        }

    /** 设置护理级别（添加护理服务） */
    @PostMapping("/assign")
    public Map<String, Object> assignCareLevel(@RequestParam int clientId,
                                               @RequestParam int careLevelId,
                                               @RequestBody List<Integer> itemIds) {
        Map<String, Object> result = new HashMap<>();
        try {
            clientCareConfigService.assignCareLevel(clientId, careLevelId, itemIds);
            result.put("isOk", true);
            result.put("msg", "护理级别设置成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "设置失败: " + e.getMessage());
        }
        return result;
    }

    /** 移除护理级别及其护理服务 */
    @PostMapping("/remove")
    public Map<String, Object> removeCareLevel(@RequestParam int clientId,
                                               @RequestParam int careLevelId) {
        Map<String, Object> result = new HashMap<>();
        try {
            clientCareConfigService.removeCareLevel(clientId, careLevelId);
            result.put("isOk", true);
            result.put("msg", "护理级别移除成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "移除失败: " + e.getMessage());
        }
        return result;
    }



    /** 移除护理服务 */
    @PostMapping("/removeService")
    public Map<String, Object> removeService(@RequestParam int configId) {
        Map<String, Object> result = new HashMap<>();
        try {
            clientCareConfigService.removeService(configId);
            result.put("isOk", true);
            result.put("msg", "护理服务移除成功");
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "移除失败: " + e.getMessage());
        }
        return result;
    }

    /** 查询可添加的护理项目（未拥有 + 状态启用 + 名字模糊） */
    @PostMapping("/availableItems")
    public Map<String, Object> getAvailableItems(@RequestParam int clientId,
                                                 @RequestParam(required = false) String name) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CareItem> list = clientCareConfigService.getAvailableItemsForClient(clientId, name);
            result.put("isOk", true);
            result.put("data", list);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }

    /** 根据状态和名称模糊查询护理项目（支持添加用） */
    @PostMapping("/queryItems")
    public Map<String, Object> queryItems(@RequestParam String status,
                                          @RequestParam(required = false) String name) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CareItem> list = clientCareConfigService.queryCareItemsByStatusAndName(status, name);
            result.put("isOk", true);
            result.put("data", list);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }
}
