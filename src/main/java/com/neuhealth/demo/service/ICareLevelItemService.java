package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.CareLevelItem;

import java.util.List;

public interface ICareLevelItemService {

    List<CareItem> getBoundItems(Integer levelId);
    List<CareItem> getAvailableItems(Integer levelId);
    boolean addItemsToLevel(Integer levelId, List<Integer> itemIds);
    boolean removeItemFromLevel(Integer levelId, Integer itemId);
}
