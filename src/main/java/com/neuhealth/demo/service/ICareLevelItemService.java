package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareLevelItem;

import java.util.List;

public interface ICareLevelItemService {
    List<Integer> getItemIdsByLevel(int levelId);
    void addItemsToLevel(int levelId, List<Integer> itemIds);
    void removeItemFromLevel(int levelId, int itemId);
}
