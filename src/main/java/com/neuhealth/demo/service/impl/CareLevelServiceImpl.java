package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.CareLevel;
import com.neuhealth.demo.mapper.CareLevelMapper;
import com.neuhealth.demo.service.ICareLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareLevelServiceImpl implements ICareLevelService {

    private CareLevelMapper careLevelMapper;

    @Override
    public List<CareLevel> listByStatus(String status) {
        return careLevelMapper.getByStatus(status);
    }

    @Override
    public void updateStatus(int id, String status) {
        CareLevel level = new CareLevel();
        level.setId(id);
        level.setStatus(status);
        careLevelMapper.updateById(level);
    }

    @Override
    public void addLevel(CareLevel level) {
        careLevelMapper.insert(level);
    }
}
