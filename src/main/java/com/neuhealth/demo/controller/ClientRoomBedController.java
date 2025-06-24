package com.neuhealth.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuhealth.demo.domain.Bed;
import com.neuhealth.demo.domain.ClientRoomBedVO;
import com.neuhealth.demo.service.IBedService;
import com.neuhealth.demo.service.IClientRoomBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("clients/with-room")
public class ClientRoomBedController {

    @Autowired
    private IClientRoomBedService clientRoomBedService;
    @Autowired
    private IBedService bed;

    @GetMapping("/page")
    public Map<String, Object> getClientsWithRoomAndBedPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ) {
        Map<String, Object> res = new HashMap<>();

        Page<ClientRoomBedVO> pageResult = clientRoomBedService.findClientsWithRoomAndBedByPage(pageNum, pageSize, name, type);

        res.put("isOk", true);
        res.put("msg", "分页查询成功");
        res.put("records", pageResult.getRecords());
        res.put("total", pageResult.getTotal());
        return res;
    }

    @GetMapping("/by-room")
    public List<Bed> getBedsByRoom(@RequestParam("roomId") int roomId) {
        return bed.getBedsByRoom(roomId);
    }

}
