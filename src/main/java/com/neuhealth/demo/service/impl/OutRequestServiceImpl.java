package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.OutRequest;
import com.neuhealth.demo.domain.OutRequestVO;
import com.neuhealth.demo.mapper.OutRequestMapper;
import com.neuhealth.demo.mapper.OutRequestVOMapper;
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
    @Autowired
    private OutRequestVOMapper voMapper;

    @Override
    public List<OutRequestVO> findAll() {
        return mapper.selectAll();
    }

    public List<OutRequestVO> findOutByPage(int pageNum, int pageSize, String name) {
        int offset = (pageNum - 1) * pageSize;
        return voMapper.selectOutRequestVOList("已提交", name, offset, pageSize);
    }

    public int countOut(String name) {
        return voMapper.countOutRequestVO("已提交", name);
    }
    @Override
    public List<OutRequestVO> searchByClientName(String name) {
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
    public boolean reviewRequest(int requestId, String status, int reviewerId, String detail) {
        OutRequest request = this.getById(requestId);
        if (request == null) return false;

        request.setStatus(status);
        request.setReviewerId(reviewerId);
        request.setReviewTime(new Date());
        request.setDetail(detail);
        boolean updated = this.updateById(request);

        if (updated && "通过".equals(status)) {
            mapper.updateBedStatus(request.getClientId(), "外出");
        }
        return updated;
    }
}
