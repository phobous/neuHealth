package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.Bed;
import com.neuhealth.demo.domain.BedUsageRecord;
import com.neuhealth.demo.mapper.BedMapper;
import com.neuhealth.demo.mapper.BedUsageRecordMapper;
import com.neuhealth.demo.service.IBedUsageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BedUsageRecordServiceImpl extends ServiceImpl<BedUsageRecordMapper, BedUsageRecord>
        implements IBedUsageRecordService {

    @Autowired
    private BedMapper bedMapper;

    @Autowired
    private BedUsageRecordMapper usageRecordMapper;

    @Override
    public List<BedUsageRecord> getAllBedUsageRecords(){return usageRecordMapper.getAllBedUsageRecords();}
    @Override
    public List<BedUsageRecord> selectBedUsageRecordsList(BedUsageRecord bedUsageRecord)
    {
        return usageRecordMapper.selectBedUsageRecordsList(bedUsageRecord);
    }
    @Override
    public List<BedUsageRecord> queryUsageRecords(String name, String status, Date startDate) {
        return usageRecordMapper.queryUsageRecords(name, status, startDate);
    }

    @Override
    public boolean updateCheckOutDate(int recordId, Date checkOutDate) {
        BedUsageRecord record = this.getById(recordId);
        if (record == null) return false;
        record.setCheckOutDate(checkOutDate);
        return this.updateById(record);
    }
    //床位调整
    @Override
    public boolean swapBed(int clientId, int oldBedId, int newBedId) {
        Date now = new Date();
        // 1. 结束旧床位记录
        usageRecordMapper.endOldBedUsage(clientId, now);
        bedMapper.updateById(new Bed() {{
            setId(oldBedId);
            setStatus("空闲");
        }});
        // 2. 插入新记录
        BedUsageRecord newRecord = new BedUsageRecord();
        newRecord.setClientId(clientId);
        newRecord.setBedId(newBedId);
        newRecord.setCheckInDate(now);
        newRecord.setStatus("正在使用");
        newRecord.setCreatedAt(now);
        this.save(newRecord);
        usageRecordMapper.insertBedUsageRecords(newRecord);
        //需要添加修改clients表里对应的bed_id


        // 3. 更新新床位状态
        bedMapper.updateById(new Bed() {{
            setId(newBedId);
            setStatus("有人");
        }});
        return true;
    }
}
