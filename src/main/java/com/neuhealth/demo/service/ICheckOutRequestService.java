package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.CheckOutRequest;
import com.neuhealth.demo.domain.CheckOutRequestVO;

import java.util.List;

public interface ICheckOutRequestService extends IService<CheckOutRequest> {
    List<CheckOutRequestVO> findAll();
    List<CheckOutRequestVO> searchByClientName(String name);
    boolean reviewRequest(int requestId, String status, int reviewerId, String detail);
    void submitRequest(CheckOutRequest request);
    List<CheckOutRequestVO> findCheckoutByPage(int pageNum, int pageSize, String name);
    int countCheckout(String name);
}
