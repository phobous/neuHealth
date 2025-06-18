package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface CareRecordMapper extends BaseMapper<CareRecord> {
    List<CareRecord> findByClientId(int clientId);
    @Select("SELECT cr.* FROM care_record cr JOIN caregiver_client cc ON cr.client_id = cc.client_id WHERE cc.caregiver_id = #{caregiverId} AND cr.is_deleted = false")
    List<CareRecord> findByCaregiverId(@Param("caregiverId") int caregiverId);
}