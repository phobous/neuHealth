package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuhealth.demo.domain.ClientRoomBedVO;

import java.util.List;

public interface IClientRoomBedService {
    List<ClientRoomBedVO> getAllClientInfoWithRoom();
    Page<ClientRoomBedVO> findClientsWithRoomAndBedByPage(int pageNum, int pageSize, String name, String type);

}
