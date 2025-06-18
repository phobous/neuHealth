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
public interface CaregiverMapper extends BaseMapper<Caregiver> {
    List<Caregiver> selectByNameLike(@Param("name") String name);
}
