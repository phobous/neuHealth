// --- 客户护理设置 Service ---

package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.ClientCareConfig;

import java.util.Date;
import java.util.List;

public interface IClientCareConfigService {
    //得到客户的项目列表
    List<ClientCareConfig> getByClientId(int clientId);

    //得到客户未购买的项目列表
    //List<CareItem> getEnabledItemsExcludingClient(int clientId);


    //找到客户未购买的项目列表(带名字查询)
    List<CareItem> getEnabledItemsExcludingClientByName(int clientId, String name);

    //续费
    void renewService(int configId, int addQuantity, Date newEndDate);

    //删除护理项目
    int removeService(int configId);

    void assignCareLevel(int clientId, int careLevelId, List<Integer> itemIds);
    void removeCareLevel(int clientId, int careLevelId);

    //添加护理项目
    void addService(int clientId, int itemId, int quantity, Date endDate);

    // 根据状态和名称模糊查询护理项目（利用 CareItemMapper.queryByConditions）
    List<CareItem> queryCareItemsByStatusAndName(String status, String name);

    // 查询指定客户未拥有的启用且未删除护理项目（调用你提供的selectItemsNotOwnedByClient）
    List<CareItem> getAvailableItemsForClient(int clientId, String name);
}
