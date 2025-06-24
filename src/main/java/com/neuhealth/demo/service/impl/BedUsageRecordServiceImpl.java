package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.domain.ClientBedMapping;
import com.neuhealth.demo.mapper.ClientBedMappingMapper;
import com.neuhealth.demo.util.TimeAdditionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.Bed;
import com.neuhealth.demo.domain.BedUsageRecord;
import com.neuhealth.demo.mapper.BedMapper;
import com.neuhealth.demo.mapper.BedUsageRecordMapper;
import com.neuhealth.demo.mapper.ClientMapper;
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
    private ClientMapper clientMapper;
    @Autowired
    private ClientBedMappingMapper clientBedMappingMapper;

    @Autowired
    private BedUsageRecordMapper usageRecordMapper;


    @Override
    public List<BedUsageRecord> getAllBedUsageRecords(){
        List<BedUsageRecord> list =usageRecordMapper.selectAllWithBedNumber();
        System.out.println(list);
        return list;
    }
    @Override
    public int getTotalCount(){
      return usageRecordMapper.getTotalCount();
    };
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
        record.setCheckInDate(TimeAdditionUtil.add8Hours(record.getCheckInDate()));
        record.setCheckOutDate(checkOutDate);
        Date now = new Date();
        record.setUpdatedAt(TimeAdditionUtil.add8Hours(now));
        return this.updateById(record);
    }
    //床位调整
    @Override
    public boolean swapBed(int id,int clientId, int oldBedId, int newBedId) {
        Date now = new Date();
        System.out.println(id+clientId+"service收到的数据"+oldBedId+newBedId);
        int result ;
        // 1. 结束旧床位记录
        BedUsageRecord oldBedUsage =usageRecordMapper.selectById(id);
        Date checkOut = TimeAdditionUtil.add8Hours(oldBedUsage.getCheckOutDate());
        oldBedUsage.setCheckInDate(TimeAdditionUtil.add8Hours(oldBedUsage.getCheckInDate()));
        oldBedUsage.setCheckOutDate(TimeAdditionUtil.add8Hours(now));
        oldBedUsage.setStatus("使用历史");
        usageRecordMapper.updateById(oldBedUsage);
        //原来的床设为空闲
        Bed oldDed = bedMapper.selectById(oldBedId);
        oldDed.setStatus("空闲");
        result= bedMapper.updateById(oldDed);

        // 2. 插入新记录
        BedUsageRecord newRecord = new BedUsageRecord();
        //newRecord.setId(null);
        newRecord.setClientId(clientId);
        newRecord.setBedId(newBedId);
        newRecord.setBedNumber(bedMapper.selectById(newBedId).getBedNumber());
        newRecord.setCheckInDate(TimeAdditionUtil.add8Hours(now));
        newRecord.setStatus("正在使用");
        newRecord.setCreatedAt(TimeAdditionUtil.add8Hours(now));
        newRecord.setCheckOutDate(TimeAdditionUtil.add8Hours(checkOut));
        newRecord.setUpdatedAt(TimeAdditionUtil.add8Hours(now));
        //this.save(newRecord);
        int result1=usageRecordMapper.insertBedUsageRecords(newRecord);
        //需要添加修改clients表里对应的bed_id
        Client client=clientMapper.selectById(clientId);
        client.setBedId(newBedId);
        int result2=clientMapper.updateById(client);
        //修改床位用户对应表
        ClientBedMapping clientBedMapping = clientBedMappingMapper.selectByClientId(clientId);
        clientBedMapping.setBedId(newBedId);
        clientBedMapping.setCreatedAt(TimeAdditionUtil.add8Hours(clientBedMapping.getCreatedAt()));
        clientBedMapping.setAssignedAt(TimeAdditionUtil.add8Hours(clientBedMapping.getAssignedAt()));
        clientBedMapping.setUpdatedAt(TimeAdditionUtil.add8Hours(now));
        clientBedMappingMapper.updateById(clientBedMapping);
        // 3. 更新新床位状态
        Bed newBed=bedMapper.selectById(newBedId);
        newBed.setStatus("有人");
        int result3=bedMapper.updateById(newBed);
        return result1!=0&&result3!=0&&result2!=0&&result!=0;
    }


}
