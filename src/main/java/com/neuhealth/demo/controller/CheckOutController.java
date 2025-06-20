package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.CheckOutRequest;
import com.neuhealth.demo.domain.CheckOutRequestVO;
import com.neuhealth.demo.service.ICheckOutRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("checkout")
public class CheckOutController {
    @Autowired
    ICheckOutRequestService checkout;

    @GetMapping("findAll")
    public List<CheckOutRequestVO> findAll(){
        return checkout.findAll();
    }
    @GetMapping
    //public Map getInfo(@PathVariable("id") Long id)
    public Map getInfo(
            @RequestParam String name
    )
    {
        List<CheckOutRequestVO> checkout = this.checkout.searchByClientName(name);
        Map res = new HashMap();
        res.put("isOk",true);
        res.put("msg","信息查询成功");
        res.put("checkout",checkout);
        return res;
    }

    //审核
    @PostMapping("/review")
    public Map reviewRequest(
            @RequestParam Integer requestId,
            @RequestParam String status,
            @RequestParam Integer reviewerId) {

        boolean success = checkout.reviewRequest(requestId, status, reviewerId);
        System.out.print("status:"+status);
        Map res = new HashMap();
        res.put("isOk",true);
        try {
            if (success) {
                res.put("msg","审核已提交！");
            } else {
                res.put("msg","审核失败");
            }
        } catch (Exception e) {
            //记录日志
            e.printStackTrace();
            //返回错误提示
            res.put("msg","审核已提交！");
        }
        return res;
    }
}
