package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.OutRequest;
import com.neuhealth.demo.domain.OutRequestVO;

import java.util.Date;
import java.util.List;

public interface IOutRequestService extends IService<OutRequest> {
    List<OutRequestVO> findAll();
    void submitRequest(OutRequest request);
    List<OutRequestVO> searchByClientName(String name);
    boolean reviewRequest(int requestId, String status, int reviewerId);
    void registerReturn(int requestId, Date actualReturnTime);
}
