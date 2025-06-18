package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.Bed;

import java.util.List;
import java.util.Map;

public interface IBedService extends IService<Bed> {
    List<Map<String, Object>> getBedStatistics();
    List<Bed> getBedsByRoom(int roomId);
    List<Bed> getAvailableBeds(int roomId);
}
