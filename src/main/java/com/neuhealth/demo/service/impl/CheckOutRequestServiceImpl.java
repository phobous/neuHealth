package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.CheckOutRequest;
import com.neuhealth.demo.domain.CheckOutRequestVO;
import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.domain.OutRequest;
import com.neuhealth.demo.mapper.CheckOutRequestMapper;
import com.neuhealth.demo.mapper.CheckOutRequestVOMapper;
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
    @Autowired
    private CheckOutRequestVOMapper voMapper;

    @Override
    public List<CheckOutRequestVO> findAll() {
        return mapper.selectAll();
    }

    public List<CheckOutRequestVO> findCheckoutByPage(int pageNum, int pageSize, String name) {
        int offset = (pageNum - 1) * pageSize;
        return voMapper.selectCheckOutRequestVOList("已提交", name, offset, pageSize);
    }

    public int countCheckout(String name) {
        return voMapper.countCheckOutRequestVO("已提交", name);
    }

    @Override
    public List<CheckOutRequestVO> searchByClientName(String name) {
        return mapper.searchByClientName(name);
    }
    @Override
    public void submitRequest(CheckOutRequest request) {
        mapper.insert(request);
    }
    @Override
    public boolean reviewRequest(int requestId, String status, int reviewerId, String detail) {
        CheckOutRequest request = this.getById(requestId);
        if (request == null) return false;

        request.setStatus(status);
        request.setReviewerId(reviewerId);
        request.setReviewTime(new Date());
        request.setDetail(detail);
        boolean updated = this.updateById(request);

        if (updated && "通过".equals(status) &&
            ("正常退住".equals(request.getType()) || "死亡退住".equals(request.getType()))) {
            mapper.updateBedStatus(request.getClientId(), "空闲");
        }
        return updated;
    }
}
