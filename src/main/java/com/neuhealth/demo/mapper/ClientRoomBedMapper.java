package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuhealth.demo.domain.ClientRoomBedVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClientRoomBedMapper {
    List<ClientRoomBedVO> selectClientWithRoomAndBed();

    List<ClientRoomBedVO> selectClientsWithRoomAndBed(@Param("page") Page<ClientRoomBedVO> page,
                                                      @Param("name") String name,
                                                      @Param("type") String type);

}
