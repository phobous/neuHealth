package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.Bed;
import com.neuhealth.demo.mapper.BedMapper;
import com.neuhealth.demo.service.IBedService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements IBedService {

    @Override
    public List<Map<String, Object>> getBedStatistics() {
        return baseMapper.countBedsSummary();
    }

    @Override
    public List<Bed> getBedsByRoom(int roomId) {
        return baseMapper.getBedsByRoom(roomId);
    }

    @Override
    public List<Bed> getAvailableBeds(int roomId) {
        return baseMapper.getAvailableBeds(roomId);
    }
}
