package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CareLevelMapper extends BaseMapper<CareLevel> {

    @Select("SELECT * FROM care_levels")
    List<CareLevel> selectAll();

    @Select("SELECT * FROM care_levels WHERE status = #{status}")
    List<CareLevel> getByStatus(@Param("status") String status);
}
