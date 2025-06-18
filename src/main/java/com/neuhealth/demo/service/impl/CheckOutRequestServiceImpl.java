package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.CheckOutRequest;
import com.neuhealth.demo.mapper.CheckOutRequestMapper;
import com.neuhealth.demo.service.ICheckOutRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CheckOutRequestServiceImpl extends ServiceImpl<CheckOutRequestMapper, CheckOutRequest>
        implements ICheckOutRequestService {

    @Autowired
    private CheckOutRequestMapper mapper;

    @Override
    public List<CheckOutRequest> searchByClientName(String name) {
        return mapper.searchByClientName(name);
    }
   @Override
    public void submitRequest(CheckOutRequest request) {
        mapper.insert(request);
    }
    @Override
    public boolean reviewRequest(int requestId, String status, int reviewerId) {
        CheckOutRequest request = this.getById(requestId);
        if (request == null) return false;

        request.setStatus(status);
        request.setReviewerId(reviewerId);
        request.setReviewTime(new Date());
        boolean updated = this.updateById(request);

        if (updated && "通过".equals(status) &&
            ("正常退住".equals(request.getType()) || "死亡退住".equals(request.getType()))) {
            mapper.updateBedStatus(request.getClientId(), "可用");
        }
        return updated;
    }
}
