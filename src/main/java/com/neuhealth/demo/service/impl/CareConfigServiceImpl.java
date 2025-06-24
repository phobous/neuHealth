package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.ClientCareConfig;
import com.neuhealth.demo.mapper.CareItemMapper;
import com.neuhealth.demo.mapper.CareConfigMapper;
import com.neuhealth.demo.mapper.CareLevelItemMapper;
import com.neuhealth.demo.service.ICareConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CareConfigServiceImpl implements ICareConfigService {

    private CareConfigMapper careConfigMapper;
    private CareLevelItemMapper careLevelItemMapper;
    private CareItemMapper careItemMapper;

    @Autowired
    public CareConfigServiceImpl(CareConfigMapper careConfigMapper, CareLevelItemMapper careLevelItemMapper) {
        this.careConfigMapper = careConfigMapper;
        this.careLevelItemMapper = careLevelItemMapper;
    }


    //找到客户购买的项目列表
    @Override
    public List<ClientCareConfig> getByClientId(int clientId, String name) {
        if (name == null) {
            name = "";
        }

        List<ClientCareConfig> configs = careConfigMapper.findByClientIdAndName(clientId, name);

        LocalDate now = LocalDate.now();  // 改为 LocalDate
        for (ClientCareConfig config : configs) {
            if (config.getEndDate() != null && config.getEndDate().isBefore(now)) {
                config.setStatus("过期");
            } else if (config.getQuantity() < 0) {
                config.setStatus("欠费");
            } else {
                config.setStatus("正常");
            }
        }

        return configs;
    }
    @Override
    public void saveClientCareConfig(Integer clientId, Integer itemId, LocalDate endDate, Integer quantity) {
        Integer careLevelId = careConfigMapper.getCareLevelIdByItemId(itemId); // ← 修正大小写
        ClientCareConfig config = new ClientCareConfig();
        config.setClientId(clientId);
        config.setItemId(itemId);
        config.setCareLevelId(careLevelId);
        config.setStartDate(LocalDate.now());
        config.setEndDate(endDate);
        config.setQuantity(quantity);
        careConfigMapper.insertClientCareConfig(config); // ← 修正大小写
    }
    //找到客户未购买的项目列表
    @Override
    public List<CareItem> getEnabledItemsExcludingClientByName(int clientId, String name) {
        return careConfigMapper.selectEnabledItemsExcludingClientByName(clientId, name);
    }


    //续费
    @Override
    public void renewService(int configId, int addQuantity, LocalDate newEndDate) {
        careConfigMapper.renewService(configId, addQuantity, newEndDate);
    }

    //删除
    @Override
    public int removeService(int configId) {
        careConfigMapper.removeService(configId);
        return configId;
    }


    @Override
    public void assignCareLevel(int clientId, int careLevelId, List<Integer> itemIds) {
        LocalDate now = LocalDate.now();
        LocalDate threeMonthsLater = now.plusMonths(3);

        List<ClientCareConfig> configs = new ArrayList<>();
        for (int itemId : itemIds) {
            ClientCareConfig config = new ClientCareConfig();
            config.setClientId(clientId);
            config.setCareLevelId(careLevelId);
            config.setItemId(itemId);
            config.setStartDate(now);
            config.setQuantity(1);
            config.setEndDate(threeMonthsLater);
            configs.add(config);
        }

        for (ClientCareConfig config : configs) {
            careConfigMapper.insert(config);
        }
    }

    @Override
    public void removeCareLevel(int clientId, int careLevelId) {
        careConfigMapper.deleteByClientAndLevel(clientId, careLevelId);
    }

    public CareConfigServiceImpl(CareConfigMapper careConfigMapper, CareItemMapper careItemMapper) {
        this.careConfigMapper = careConfigMapper;
        this.careItemMapper = careItemMapper;
    }



    @Override
    public void addService(int clientId, int itemId, int quantity, LocalDate endDate) {
        ClientCareConfig config = new ClientCareConfig();
        config.setClientId(clientId);
        config.setItemId(itemId);
        config.setQuantity(quantity);
        config.setStartDate(LocalDate.now());
        config.setEndDate(endDate);
        careConfigMapper.insert(config);
    }

    @Override
    public List<CareItem> queryCareItemsByStatusAndName(String status, String name) {
        return careItemMapper.queryByConditions(status, name);
    }

    @Override
    public List<CareItem> getAvailableItemsForClient(int clientId, String name) {
        return careItemMapper.selectItemsNotOwnedByClient(clientId, name);
    }
}