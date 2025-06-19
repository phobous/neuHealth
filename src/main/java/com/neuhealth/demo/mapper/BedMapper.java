package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.Bed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BedMapper extends BaseMapper<Bed> {

    List<Bed> getAllBeds();
    List<Map<String, Object>> countBedsSummary();

    List<Bed> getBedsByRoom(@Param("roomId") int roomId);

    List<Bed> getAvailableBeds(@Param("roomId") int roomId);
}
