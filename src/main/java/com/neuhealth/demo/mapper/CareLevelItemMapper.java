package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareLevelItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CareLevelItemMapper extends BaseMapper<CareLevelItem> {
    List<Integer> getItemIdsByLevel(@Param("levelId") int levelId);
    void deleteItemsByLevel(@Param("levelId") int levelId);
    void deleteItemFromAllLevels(@Param("itemId") int itemId);
    void deleteByLevelAndItem(@Param("levelId") int levelId, @Param("itemId") int itemId);
}
