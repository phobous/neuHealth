package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.*;

import java.time.LocalDate;
import java.util.List;

public interface IClientCareConfigService {
    List<ClientCareConfig> getByClientId(int clientId);

    void assignCareLevel(int clientId, int careLevelId, List<Integer> itemIds);

    void removeCareLevel(int clientId, int careLevelId);

    // 列出所有客户信息
    List<ClientInfoDTO> getAllClientInfo();

    // 模糊查询
    List<ClientInfoDTO> getClientsByName(String name);

    void removeService(int configId);

    void addService(int clientId, int itemId, int quantity, LocalDate endDate);

    /**
     * 根据护理级别ID查询关联的护理项目
     */
    List<CareItem> queryItemsByCareLevel(int careLevelId);

    // 根据状态和名称模糊查询护理项目（利用 CareItemMapper.queryByConditions）
    List<CareItem> queryCareItemsByStatusAndName(String status, String name);

    // 查询指定客户未拥有的启用且未删除护理项目（调用你提供的selectItemsNotOwnedByClient）
    List<CareItem> getAvailableItemsForClient(int clientId, String name);
}
