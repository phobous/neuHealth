package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.ClientCareConfig;

import java.time.LocalDate;
import java.util.List;

public interface ICareConfigService {

    // 得到客户的项目列表
    List<ClientCareConfig> getByClientId(int clientId, String name);

    // 找到客户未购买的项目列表（支持名称模糊查询）
    List<CareItem> getEnabledItemsExcludingClientByName(int clientId, String name);

    // 续费护理服务
    void renewService(int configId, int addQuantity, LocalDate newEndDate);

    // 删除护理服务
    int removeService(int configId);

    // 分配护理等级（批量添加护理项目）
    void assignCareLevel(int clientId, int careLevelId, List<Integer> itemIds);

    // 移除指定客户的某个护理等级及其项目
    void removeCareLevel(int clientId, int careLevelId);

    // 添加单个护理服务项目（设置数量和结束日期）
    void saveClientCareConfig(Integer clientId, Integer itemId, LocalDate endDate, Integer quantity);

    void addService(int clientId, int itemId, int quantity, LocalDate endDate);

    // 按状态和名称查询护理项目（模糊查询）
    List<CareItem> queryCareItemsByStatusAndName(String status, String name);

    // 查询客户未拥有的、启用状态的护理项目
    List<CareItem> getAvailableItemsForClient(int clientId, String name);
}
