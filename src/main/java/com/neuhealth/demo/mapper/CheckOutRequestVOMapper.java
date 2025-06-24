package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CheckOutRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckOutRequestVOMapper extends BaseMapper<CheckOutRequestVO> {
    //查询列表，带分页
    List<CheckOutRequestVO> selectCheckOutRequestVOList(
            @Param("status") String status,
            @Param("name") String name,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    //查询总条数
    int countCheckOutRequestVO(
            @Param("status") String status,
            @Param("name") String name
    );
}
