package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.*;
import com.neuhealth.demo.service.IHelpGiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/helpGiver")
public class HelpGiverController {

    @Autowired
    private IHelpGiverService helpGiverService;

    @GetMapping("/clientList")
    public Map<String, Object> getClientListByCaregiver(
            @RequestParam Long caregiverId,
            @RequestParam(required = false) String name) {

        Map<String, Object> result = new HashMap<>();
        try {
            List<ClientInfoDTO> items = helpGiverService.getClientInfoByCaregiverIdAndName(caregiverId, name);
            result.put("isOk", true);
            result.put("msg", "查询成功");
            result.put("items", items);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/careRecords")
    public Map<String, Object> getCareRecordsByClientId(@RequestParam Long clientId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CareRecordVO> records = helpGiverService.getRecordsByClientId(clientId);
            result.put("isOk", true);
            result.put("msg", "查询成功");
            result.put("items", records);
        } catch (Exception e) {
            result.put("isOk", false);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
}

      @GetMapping("/careItems")
        public Map<String, Object> getClientCareItems(@RequestParam Long clientId) {
            List<ClientCareItemVO> list = helpGiverService.getCareItemsByClientId(clientId);
            Map<String, Object> result = new HashMap<>();
            result.put("isOk", true);
            result.put("items", list);
            return result;
        }

        /**
         * 添加护理记录接口
         */
        @PostMapping("/addRecord")
        public Map<String, Object> addCareRecord(@RequestBody CareRecord record) {
            Map<String, Object> result = new HashMap<>();
            try {
                helpGiverService.addCareRecordAndUpdateQuantity(record);
                result.put("success", true);
                result.put("message", "护理记录添加成功");
            } catch (Exception e) {
                result.put("success", false);
                result.put("message", "添加护理记录失败: " + e.getMessage());
            }
            return result;
        }

        /**
         * 根据客户ID查询护理记录详情
         */
        @GetMapping("/recordDetails")
        public Map<String, Object> getCareRecordDetails(@RequestParam Long clientId) {
            Map<String, Object> result = new HashMap<>();
            try {
                List<CareRecordVO> list = helpGiverService.getCareRecordDetailsByClientId(clientId);
                result.put("success", true);
                result.put("data", list);
            } catch (Exception e) {
                result.put("success", false);
                result.put("message", "查询失败: " + e.getMessage());
            }
            return result;
        }

         // 删除护理记录接口
        @DeleteMapping("/deleteRecord/{id}")
        public Map<String, Object> deleteCareRecord(@PathVariable("id") Integer id) {
            boolean success = helpGiverService.deleteCareRecordById(id);
            Map<String, Object> result = new HashMap<>();
            result.put("isOk", success);
            result.put("msg", success ? "删除成功" : "删除失败");
            return result;
        }

        @GetMapping("/listByClientId")
        public Map<String, Object> listByClientId(@RequestParam Long clientId) {
            Map<String, Object> result = new HashMap<>();
            try {
                List<OutRequestVO> list = helpGiverService.getOutRequestsByClientId(clientId);
                result.put("isOk", true);
                result.put("data", list);
                result.put("msg", "查询成功");
            } catch (Exception e) {
                result.put("isOk", false);
                result.put("msg", "查询异常：" + e.getMessage());
                result.put("data", null);
            }
            return result;
        }
         @PostMapping("/registerReturnTime")
        public Map<String, Object> registerReturnTime(
            @RequestParam Long id,
            @RequestParam Long actualReturnTime  // 时间戳，单位毫秒
        ) {
            Date actualReturnDate = new Date(actualReturnTime);

            boolean success = helpGiverService.registerReturnTime(id.intValue(), actualReturnDate);

            Map<String, Object> result = new HashMap<>();
            result.put("isOk", success);
            result.put("msg", success ? "登记成功" : "登记失败");
            return result;
        }
         // 撤销申请记录接口
        @DeleteMapping("/deleteOutRequest/{id}")
        public Map<String, Object> deleteOutRequest(@PathVariable("id") Integer id) {
            boolean success = helpGiverService.logicalDeleteOutRequest(id);
            Map<String, Object> result = new HashMap<>();
            result.put("isOk", success);
            result.put("msg", success ? "删除成功" : "删除失败");
            return result;
        }

         /**
         * 添加外出登记申请
         */
        @PostMapping("/add")
        public Map<String, Object> addOutRequest(@RequestBody OutRequest outRequest) {
            Map<String, Object> result = new HashMap<>();
            int insertCount = helpGiverService.addOutRequest(outRequest);
            if (insertCount > 0) {
                result.put("isOk", true);
                result.put("msg", "添加成功");
            } else {
                result.put("isOk", false);
                result.put("msg", "添加失败");
            }
            return result;
        }

        @GetMapping("/checkout/listByClientId")
        public Map<String, Object> getCheckOutListByClientId(@RequestParam Integer clientId) {
            List<CheckOutRequestVO> list = helpGiverService.getCheckOutRequestsByClientId(clientId);
            Map<String, Object> result = new HashMap<>();
            result.put("isOk", true);
            result.put("data", list);
            return result;
        }
        /**
         * 添加退住申请
         */
        @PostMapping("/addOutRoom")
        public Map<String, Object> addCheckOutRequest(@RequestBody CheckOutRequest checkOutRequest) {
            Map<String, Object> result = new HashMap<>();
            try {
                // 设置当前时间为申请时间
                checkOutRequest.setRequestedAt(new Date());

                // 调用 Service 层插入数据
                int insertCount = helpGiverService.insertCheckOutRequest(checkOutRequest);

                if (insertCount > 0) {
                    result.put("isOk", true);
                    result.put("msg", "退住申请提交成功");
                } else {
                    result.put("isOk", false);
                    result.put("msg", "退住申请提交失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("isOk", false);
                result.put("msg", "服务器错误" + e.getMessage());
            }
            return result;
        }

         // 撤销退住申请记录接口
        @DeleteMapping("/deleteCheckOutRequest/{id}")
        public Map<String, Object> deleteCheckOutRequest(@PathVariable("id") Integer id) {
            boolean success = helpGiverService.logicalDeleteCheckOutRequest(id);
            Map<String, Object> result = new HashMap<>();
            result.put("isOk", success);
            result.put("msg", success ? "删除成功" : "删除失败");
            return result;
        }
}
