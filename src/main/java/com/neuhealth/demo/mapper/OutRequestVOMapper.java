package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.OutRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutRequestVOMapper extends BaseMapper<OutRequestVO> {
    //查询列表，带分页
    List<OutRequestVO> selectOutRequestVOList(
            @Param("status") String status,
            @Param("name") String name,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    //查询总条数
    int countOutRequestVO(
            @Param("status") String status,
            @Param("name") String name
    );
}
