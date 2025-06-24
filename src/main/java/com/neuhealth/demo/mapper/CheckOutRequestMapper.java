package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CheckOutRequest;
import com.neuhealth.demo.domain.CheckOutRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckOutRequestMapper extends BaseMapper<CheckOutRequest> {
    List<CheckOutRequestVO> selectAll();

    List<CheckOutRequestVO> searchByClientName(@Param("name") String name);

    void updateBedStatus(@Param("clientId") int clientId, @Param("status") String status);
}
