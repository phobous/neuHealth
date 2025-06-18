// --- 客户护理设置 Service ---

package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.ClientCareConfig;

import java.util.Date;
import java.util.List;

public interface IClientCareConfigService {
    List<ClientCareConfig> getByClientId(int clientId);
    void assignCareLevel(int clientId, int careLevelId, List<Integer> itemIds);
    void removeCareLevel(int clientId, int careLevelId);
    void renewService(int configId, int addQuantity, Date newEndDate);

    void removeService(int configId);

    void addService(int clientId, int itemId, int quantity, Date endDate);

    // 根据状态和名称模糊查询护理项目（利用 CareItemMapper.queryByConditions）
    List<CareItem> queryCareItemsByStatusAndName(String status, String name);

    // 查询指定客户未拥有的启用且未删除护理项目（调用你提供的selectItemsNotOwnedByClient）
    List<CareItem> getAvailableItemsForClient(int clientId, String name);
}
