package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.Bed;
import com.neuhealth.demo.domain.BedUsageRecord;

import java.util.Date;
import java.util.List;

public interface IBedUsageRecordService extends IService<BedUsageRecord> {

    public List<BedUsageRecord> getAllBedUsageRecords();
    public int getTotalCount();
    public List<BedUsageRecord> selectBedUsageRecordsList(BedUsageRecord bedUsageRecord);
    List<BedUsageRecord> queryUsageRecords(String name, String status, Date startDate);
    boolean updateCheckOutDate(int recordId, Date checkOutDate);
    boolean swapBed(int id, int clientId, int oldBedId, int newBedId);
}
