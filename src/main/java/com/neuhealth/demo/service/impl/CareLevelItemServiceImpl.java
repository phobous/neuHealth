package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.CareLevelItem;
import com.neuhealth.demo.mapper.CareLevelItemMapper;
import com.neuhealth.demo.service.ICareLevelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CareLevelItemServiceImpl implements ICareLevelItemService {

    @Autowired
    private CareLevelItemMapper careLevelItemMapper;

    @Override
    public List<CareItem> getBoundItems(Integer levelId) {
        return careLevelItemMapper.getBoundItems(levelId);
    }

    @Override
    public List<CareItem> getAvailableItems(Integer levelId) {
        return careLevelItemMapper.getAvailableItems(levelId);
    }

    @Override
    @Transactional
    public boolean addItemsToLevel(Integer levelId, List<Integer> itemIds) {
        for (Integer itemId : itemIds) {
            CareLevelItem entity = new CareLevelItem();
            entity.setLevelId(levelId);
            entity.setItemId(itemId);
            careLevelItemMapper.insert(entity);
        }
        return true;
    }

    @Override
    public boolean removeItemFromLevel(Integer levelId, Integer itemId) {
        return careLevelItemMapper.deleteByLevelIdAndItemId(levelId, itemId) > 0;
    }
}
