// --- 客户护理设置 ---

package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.CareLevel;
import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.domain.ClientCareConfig;
import com.neuhealth.demo.domain.ClientInfoDTO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
@Mapper
public interface ClientCareConfigMapper extends BaseMapper<ClientCareConfig> {
    @Select("SELECT * FROM client_care_config WHERE client_id = #{clientId}")
    List<ClientCareConfig> findByClientId(@Param("clientId") int clientId);

    @Delete("DELETE FROM client_care_config WHERE client_id = #{clientId} AND care_level_id = #{levelId}")
    void deleteByClientAndLevel(@Param("clientId") int clientId, @Param("levelId") int levelId);

   List<ClientInfoDTO> getAllClientInfo();
    //模糊查询
   List<ClientInfoDTO> findClientsByNameLike(@Param("name") String name);

    @Update("UPDATE client_care_config SET quantity = quantity + #{addQuantity}, end_date = #{newEndDate} WHERE id = #{configId}")
    int renewService(@Param("configId") int configId, @Param("addQuantity") int addQuantity, @Param("newEndDate") Date newEndDate);

    @Update("UPDATE client_care_config SET quantity = 0 WHERE id = #{configId}")
    int removeService(@Param("configId") int configId);
}