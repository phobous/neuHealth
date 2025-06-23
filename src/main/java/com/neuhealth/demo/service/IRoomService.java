package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.Room;

import java.util.List;
import java.util.Map;

public interface IRoomService {
    Map<String, List<Room>> getRoomsGroupedByFloor();
}
