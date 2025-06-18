package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.CareLevelItem;
import com.neuhealth.demo.mapper.CareLevelItemMapper;
import com.neuhealth.demo.service.ICareLevelItemService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CareLevelItemServiceImpl implements ICareLevelItemService {

    private CareLevelItemMapper careLevelItemMapper;

    @Override
    public List<Integer> getItemIdsByLevel(int levelId) {
        return careLevelItemMapper.getItemIdsByLevel(levelId);
    }

    @Override
    public void addItemsToLevel(int levelId, List<Integer> itemIds) {
        for (int itemId : itemIds) {
            CareLevelItem cli = new CareLevelItem();
            cli.setLevelId(levelId);
            cli.setItemId(itemId);
            careLevelItemMapper.insert(cli);
        }
    }

    @Override
    public void removeItemFromLevel(int levelId, int itemId) {
        careLevelItemMapper.deleteByLevelAndItem(levelId, itemId);
    }
}
