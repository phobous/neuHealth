package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.CareLevel;
import com.neuhealth.demo.mapper.CareLevelMapper;
import com.neuhealth.demo.service.ICareLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareLevelServiceImpl implements ICareLevelService {
    @Autowired
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
    @Override
    public List<CareLevel> listAll() {
       return careLevelMapper.selectAll(); // Mapper 中提供该方法
     }
 @Override
    public boolean deleteById(int id) {
        return careLevelMapper.deleteById(id) > 0;
    }


}
