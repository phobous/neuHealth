package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.BedUsageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface BedUsageRecordMapper extends BaseMapper<BedUsageRecord> {
    List<BedUsageRecord> queryUsageRecords(
        @Param("name") String name,
        @Param("status") String status,
        @Param("startDate") Date startDate
    );

    void endOldBedUsage(@Param("clientId") int clientId, @Param("endDate") Date endDate);

    void markAllInactive(@Param("clientId") int clientId);
}
