package com.neuhealth.demo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuhealth.demo.domain.CheckOutRequestVO;
import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.domain.OutRequestVO;
import com.neuhealth.demo.domain.ReviewRequest;
import com.neuhealth.demo.service.IOutRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("out")
public class OutController {
    @Autowired
    IOutRequestService out;

    @GetMapping("findAll")
    public List<OutRequestVO> findAll(){
        return out.findAll();
    }
    @GetMapping
    //public Map getInfo(@PathVariable("id") Long id)
    public Map getInfo(
            @RequestParam String name
    )
    {
        List<OutRequestVO> out = this.out.searchByClientName(name);
        Map res = new HashMap();
        res.put("isOk",true);
        res.put("msg","信息查询成功");
        res.put("out",out);
        return res;
    }
    @GetMapping("/page")
    public Map<String, Object> page(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name
    ) {
        List<OutRequestVO> list = out.findOutByPage(pageNum, pageSize, name);
        int total = out.countOut(name);

        Map<String, Object> res = new HashMap<>();
        res.put("records", list);
        res.put("total", total);
        res.put("pageNum", pageNum);
        res.put("pageSize", pageSize);

        return res;
    }

    //审核
    @PostMapping("/review")
    public Map reviewRequest(@RequestBody ReviewRequest request) {
        Integer requestId = request.getRequestId();
        String status = request.getStatus();
        Integer reviewerId = request.getReviewerId();
        String detail = request.getDetail();

        boolean success = out.reviewRequest(requestId, status, reviewerId,detail);
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
