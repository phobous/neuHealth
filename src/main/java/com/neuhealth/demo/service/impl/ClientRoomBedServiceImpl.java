package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuhealth.demo.domain.ClientRoomBedVO;
import com.neuhealth.demo.mapper.ClientRoomBedMapper;
import com.neuhealth.demo.service.IClientRoomBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientRoomBedServiceImpl implements IClientRoomBedService {
    @Autowired
    private ClientRoomBedMapper clientRoomBedMapper;

    @Override
    public List<ClientRoomBedVO> getAllClientInfoWithRoom() {
        return clientRoomBedMapper.selectClientWithRoomAndBed();
    }

    @Override
    public Page<ClientRoomBedVO> findClientsWithRoomAndBedByPage(int pageNum, int pageSize, String name, String type) {
        Page<ClientRoomBedVO> page = new Page<>(pageNum, pageSize);
        List<ClientRoomBedVO> records = clientRoomBedMapper.selectClientsWithRoomAndBed(page, name, type);
        page.setRecords(records);
        return page;
    }
}
