package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.Room;
import com.neuhealth.demo.mapper.RoomMapper;
import com.neuhealth.demo.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Map<String, List<Room>> getRoomsGroupedByFloor() {
        List<Room> allRooms = roomMapper.selectAllRooms();
        Map<String, List<Room>> grouped = new TreeMap<>();

        for (Room room : allRooms) {
            String floorName = room.getFloor() + "楼";
            grouped.computeIfAbsent(floorName, k -> new ArrayList<>()).add(room);
        }
        return grouped;
    }

}
