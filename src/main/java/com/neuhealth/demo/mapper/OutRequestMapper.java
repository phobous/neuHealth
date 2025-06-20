package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CheckOutRequest;
import com.neuhealth.demo.domain.OutRequest;
import com.neuhealth.demo.domain.OutRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OutRequestMapper extends BaseMapper<OutRequest> {

    List<OutRequestVO> selectAll();
    List<OutRequestVO> searchByClientName(@Param("name") String name);

    void updateBedStatus(@Param("clientId") int clientId, @Param("status") String status);

    @Update("UPDATE out_requests SET actual_return_time = #{actualReturnTime}, status = '已回院' WHERE id = #{id}")
    void registerReturn(@Param("id") int id, @Param("actualReturnTime") java.util.Date actualReturnTime);
}
