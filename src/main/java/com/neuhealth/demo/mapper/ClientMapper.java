package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClientMapper extends BaseMapper<Client> {
    List<Client> searchClients(@Param("name") String name, @Param("type") String type);

    void updateBedStatus(@Param("bedId") int bedId, @Param("status") String status);

    void hideCurrentBedUsage(@Param("clientId") int clientId);

    void updateCurrentBedLeaveDate(@Param("clientId") int clientId, @Param("leaveDate") java.util.Date leaveDate);
}
