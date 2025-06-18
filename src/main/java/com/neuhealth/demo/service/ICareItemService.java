package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareItem;

import java.util.List;

public interface ICareItemService {
    List<CareItem> list(String status, String name);
    void addItem(CareItem item);
    void updateItem(CareItem item);
    void logicDelete(int id);
}
