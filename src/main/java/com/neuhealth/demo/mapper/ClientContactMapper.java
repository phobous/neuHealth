package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.ClientContact;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientContactMapper extends BaseMapper<ClientContact> {
}
