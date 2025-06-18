package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {
    List<Room> getRoomsByFloor(@Param("floor") int floor);
}
