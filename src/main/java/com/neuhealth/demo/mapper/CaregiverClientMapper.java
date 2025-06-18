package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.Caregiver;
import com.neuhealth.demo.domain.CaregiverClient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;

import java.util.List;
@Mapper
public interface CaregiverClientMapper extends BaseMapper<CaregiverClient> {
    
    @Select("SELECT c.* FROM caregivers c INNER JOIN caregiver_client cc ON c.id = cc.caregiver_id WHERE cc.client_id = #{clientId} LIMIT 1")
    Caregiver selectCaregiverByClientId(@Param("clientId") int clientId);

    @Select("SELECT cl.* FROM clients cl LEFT JOIN caregiver_client cc ON cl.id = cc.client_id WHERE cc.client_id IS NULL AND cl.name LIKE CONCAT('%', #{name}, '%')")
    List<Object> selectClientsWithoutCaregiver(@Param("name") String name); // 注意替换为实际Client实体
    
    @Select("SELECT cl.* FROM clients cl INNER JOIN caregiver_client cc ON cl.id = cc.client_id WHERE cc.caregiver_id = #{caregiverId}")
    List<Object> selectClientsByCaregiverId(@Param("caregiverId") int caregiverId); // 注意替换为实际Client实体

    @Delete("DELETE FROM caregiver_client WHERE caregiver_id = #{caregiverId} AND client_id = #{clientId}")
    int deleteByCaregiverAndClient(@Param("caregiverId") int caregiverId, @Param("clientId") int clientId);
}