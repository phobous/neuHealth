package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.CareLevelItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CareLevelItemMapper extends BaseMapper<CareLevelItem> {
    //护理级别和护理项目设置
    @Select("SELECT ci.* FROM care_items ci JOIN care_level_items cli ON ci.id = cli.item_id WHERE cli.level_id = #{levelId} AND ci.is_deleted = 0")
    List<CareItem> getBoundItems(Integer levelId);
     @Select("SELECT * FROM care_items WHERE status = '启用' AND is_deleted = 0 AND id NOT IN ( SELECT item_id FROM care_level_items WHERE level_id = #{levelId})")
    List<CareItem> getAvailableItems(Integer levelId);
    @Delete("DELETE FROM care_level_items WHERE level_id = #{levelId} AND item_id = #{itemId}")
    int deleteByLevelIdAndItemId(Integer levelId, Integer itemId);
    @Select("""
        SELECT ci.*
        FROM care_level_item cli
        JOIN care_item ci ON cli.item_id = ci.id
        WHERE cli.care_level_id = #{levelId}
    """)
    List<CareItem> selectItemsByLevelId(@Param("levelId") int levelId);
    @Delete("DELETE FROM care_level_items WHERE care_level_id = #{levelId}")
    void deleteItemsByLevel(@Param("levelId") int levelId);
    @Delete("DELETE FROM care_level_items WHERE item_id = #{itemId}")
    void deleteItemFromAllLevels(@Param("itemId") int itemId);
    void deleteByLevelAndItem(@Param("levelId") int levelId, @Param("itemId") int itemId);
}
