package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareRecord;
import com.neuhealth.demo.domain.CareRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface CareRecordMapper extends BaseMapper<CareRecord> {
   @Select("""
        SELECT 
            cr.id AS id,
            ci.code AS itemCode,
            ci.name AS itemName,
            cr.care_quantity AS careQuantity,
            ci.description AS careDescription,
            cg.name AS caregiverName,
            cg.phone AS caregiverPhone,
            cr.care_time AS careTime
        FROM care_records cr
        LEFT JOIN care_items ci ON cr.item_id = ci.id
        LEFT JOIN caregivers cg ON cr.caregiver_id = cg.id
        WHERE cr.client_id = #{clientId}
          AND cr.is_deleted = 0
          AND ci.is_deleted = 0
    """)
    List<CareRecordDTO> findByClientId(@Param("clientId") int clientId);

    @Select("SELECT cr.* FROM care_records cr JOIN caregiver_client cc ON cr.client_id = cc.client_id WHERE cc.caregiver_id = #{caregiverId} AND cr.is_deleted = false")
    List<CareRecord> findByCaregiverId(@Param("caregiverId") int caregiverId);
}