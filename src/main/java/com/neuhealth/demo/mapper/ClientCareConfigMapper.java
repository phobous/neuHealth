// --- 客户护理设置 ---

package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.ClientCareConfig;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
@Mapper
public interface ClientCareConfigMapper extends BaseMapper<ClientCareConfig> {
    List<ClientCareConfig> findByClientId(@Param("clientId") int clientId);

    @Delete("DELETE FROM client_care_config WHERE client_id = #{clientId} AND care_level_id = #{levelId}")
    void deleteByClientAndLevel(@Param("clientId") int clientId, @Param("levelId") int levelId);


    @Update("UPDATE client_care_config SET quantity = quantity + #{addQuantity}, end_date = #{newEndDate} WHERE id = #{configId}")
    int renewService(@Param("configId") int configId, @Param("addQuantity") int addQuantity, @Param("newEndDate") Date newEndDate);

    @Update("UPDATE client_care_config SET quantity = 0 WHERE id = #{configId}")
    int removeService(@Param("configId") int configId);
}