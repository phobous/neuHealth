// --- 客户护理设置 ---

package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.ClientCareConfig;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
@Mapper
public interface ClientCareConfigMapper extends BaseMapper<ClientCareConfig> {
    //得到当前客户的护理列表
    List<ClientCareConfig> findByClientId(@Param("clientId") int clientId);

    // 得到当前客户未购买护理项目列表（带名字模糊查询）
    List<CareItem> selectEnabledItemsExcludingClientByName(@Param("clientId") int clientId, @Param("name") String name);


    @Delete("DELETE FROM client_care_config WHERE client_id = #{clientId} AND care_level_id = #{levelId}")
    void deleteByClientAndLevel(@Param("clientId") int clientId, @Param("levelId") int levelId);


    //添加项目


    //续费
    @Update("UPDATE client_care_config SET quantity = quantity + #{addQuantity}, end_date = #{newEndDate} WHERE id = #{configId}")
    int renewService(@Param("configId") int configId, @Param("addQuantity") int addQuantity, @Param("newEndDate") Date newEndDate);


    //移除项目
    @Delete("DELETE FROM client_care_config WHERE id = #{configId}")
    int removeService(@Param("configId") int configId);
}