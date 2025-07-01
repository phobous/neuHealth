package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.mapper.CareItemMapper;
import com.neuhealth.demo.mapper.CareLevelItemMapper;
import com.neuhealth.demo.service.ICareItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

@Service
public class CareItemServiceImpl implements ICareItemService {
    @Autowired
    private CareItemMapper careItemMapper;
    @Autowired
    private CareLevelItemMapper careLevelItemMapper;

    @Override
    public List<CareItem> list(String status, String name) {
        QueryWrapper<CareItem> wrapper = new QueryWrapper<>();

        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name); // 模糊匹配
        }
        wrapper.eq("is_deleted", false); // 只查未逻辑删除的

        return careItemMapper.selectList(wrapper);
    }


    @Override
    public void addItem(CareItem item) {
        careItemMapper.insert(item);
    }

    @Override
    public void updateItem(CareItem item) {
        careItemMapper.updateById(item);
        if ("停用".equals(item.getStatus())) {
            careLevelItemMapper.deleteItemFromAllLevels(item.getId());
        }
    }

    @Override
    public void logicDelete(int id) {
        careItemMapper.logicDelete(id);
        careLevelItemMapper.deleteItemFromAllLevels(id);
    }
}
