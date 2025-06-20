package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.CheckOutRequest;
import com.neuhealth.demo.domain.CheckOutRequestVO;

import java.util.List;

public interface ICheckOutRequestService extends IService<CheckOutRequest> {
    List<CheckOutRequestVO> findAll();
    List<CheckOutRequestVO> searchByClientName(String name);
    boolean reviewRequest(int requestId, String status, int reviewerId);
     void submitRequest(CheckOutRequest request);
}
