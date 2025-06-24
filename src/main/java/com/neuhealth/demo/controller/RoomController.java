package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.domain.Room;
import com.neuhealth.demo.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rooms")
public class RoomController {
    @Autowired
    IRoomService roomService;

    @GetMapping("findAll")
    public Map<String, List<Room>> findAll(){
        return roomService.getRoomsGroupedByFloor();
    }


}
