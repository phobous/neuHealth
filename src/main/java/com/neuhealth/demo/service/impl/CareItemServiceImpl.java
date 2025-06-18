package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.mapper.CareItemMapper;
import com.neuhealth.demo.mapper.CareLevelItemMapper;
import com.neuhealth.demo.service.ICareItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareItemServiceImpl implements ICareItemService {

    private CareItemMapper careItemMapper;

    private CareLevelItemMapper careLevelItemMapper;

    @Override
    public List<CareItem> list(String status, String name) {
        return careItemMapper.queryByConditions(status, name);
    }

    @Override
    public void addItem(CareItem item) {
        careItemMapper.insert(item);
    }

    @Override
    public void updateItem(CareItem item) {
        careItemMapper.updateById(item);
        if ("停用".equals(item.getStatus())) {
            careLevelItemMapper.deleteItemFromAllLevels(item.getId());
        }
    }

    @Override
    public void logicDelete(int id) {
        careItemMapper.logicDelete(id);
        careLevelItemMapper.deleteItemFromAllLevels(id);
    }
}
