// CareItemMapper.java
package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CareItemMapper extends BaseMapper<CareItem> {
    List<CareItem> queryByConditions(@Param("status") String status, @Param("name") String name);
    void logicDelete(@Param("id") int id);
    List<CareItem> getEnabledItems();
    @Select("SELECT * FROM care_items WHERE id NOT IN (SELECT item_id FROM client_care_config WHERE client_id = #{clientId}) AND status = '启用' AND is_deleted = false AND name LIKE CONCAT('%', #{name}, '%')")
    List<CareItem> selectItemsNotOwnedByClient(@Param("clientId") int clientId, @Param("name") String name);
}