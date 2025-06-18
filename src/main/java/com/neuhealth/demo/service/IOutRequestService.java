package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.OutRequest;

import java.util.Date;
import java.util.List;

public interface IOutRequestService extends IService<OutRequest> {
    void submitRequest(OutRequest request);
    List<OutRequest> searchByClientName(String name);
    boolean reviewRequest(int requestId, String status, int reviewerId);
    void registerReturn(int requestId, Date actualReturnTime);
}
