package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareLevel;

import java.util.List;

public interface ICareLevelService {
    List<CareLevel> listByStatus(String status);
    void updateStatus(int id, String status);
    void addLevel(CareLevel level);
}
