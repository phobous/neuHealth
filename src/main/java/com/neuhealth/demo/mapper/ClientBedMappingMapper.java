package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.ClientBedMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClientBedMappingMapper extends BaseMapper<ClientBedMapping> {
    void markDeletedByClientId(@Param("clientId") int clientId);

}
