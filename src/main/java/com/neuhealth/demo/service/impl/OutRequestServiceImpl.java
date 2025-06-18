package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.OutRequest;
import com.neuhealth.demo.mapper.OutRequestMapper;
import com.neuhealth.demo.service.IOutRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OutRequestServiceImpl extends ServiceImpl<OutRequestMapper, OutRequest>
        implements IOutRequestService {

    @Autowired
    private OutRequestMapper mapper;

    @Override
    public List<OutRequest> searchByClientName(String name) {
        return mapper.searchByClientName(name);
    }
    @Override
    public void submitRequest(OutRequest request) {
        mapper.insert(request);
    }
   @Override
    public void registerReturn(int requestId, Date actualReturnTime) {
        mapper.registerReturn(requestId, actualReturnTime);
    }
    @Override
    public boolean reviewRequest(int requestId, String status, int reviewerId) {
        OutRequest request = this.getById(requestId);
        if (request == null) return false;

        request.setStatus(status);
        request.setReviewerId(reviewerId);
        request.setReviewTime(new Date());
        boolean updated = this.updateById(request);

        if (updated && "通过".equals(status)) {
            mapper.updateBedStatus(request.getClientId(), "外出");
        }
        return updated;
    }
}
