package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neuhealth.demo.domain.CareRecord;
import com.neuhealth.demo.domain.CareRecordDTO;
import com.neuhealth.demo.mapper.CareRecordMapper;
import com.neuhealth.demo.mapper.ClientCareConfigMapper;
import com.neuhealth.demo.service.ICareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CareRecordServiceImpl implements ICareRecordService {

    private final CareRecordMapper careRecordMapper;
    private  ClientCareConfigMapper clientCareConfigMapper;
    @Autowired
    public CareRecordServiceImpl(CareRecordMapper careRecordMapper) {
        this.careRecordMapper = careRecordMapper;
    }

    @Override
    public List<CareRecordDTO> getByClientId(int clientId) {
        return careRecordMapper.findByClientId(clientId);
    }

    @Override
    public void deleteRecord(int recordId) {
        CareRecord record = new CareRecord();
        record.setId(recordId);
        record.setDeleted(true);
        careRecordMapper.updateById(record);
    }
     @Override
    public void addCareRecord(CareRecord record) {
        record.setDeleted(false);
        careRecordMapper.insert(record);

        // 同步更新护理数量
        clientCareConfigMapper.update(
            null,
            Wrappers.<com.neuhealth.demo.domain.ClientCareConfig>lambdaUpdate()
                .eq(com.neuhealth.demo.domain.ClientCareConfig::getClientId, record.getClientId())
                .eq(com.neuhealth.demo.domain.ClientCareConfig::getItemId, record.getItemId())
                .setSql("quantity = quantity - " + record.getCareQuantity())
        );
    }

    @Override
    public List<CareRecord> getByCaregiverId(int caregiverId) {
        return careRecordMapper.findByCaregiverId(caregiverId);
    }
}
