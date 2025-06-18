package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neuhealth.demo.domain.CareItem;
import com.neuhealth.demo.domain.ClientCareConfig;
import com.neuhealth.demo.mapper.CareItemMapper;
import com.neuhealth.demo.mapper.ClientCareConfigMapper;
import com.neuhealth.demo.mapper.CareLevelItemMapper;
import com.neuhealth.demo.service.IClientCareConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientCareConfigServiceImpl implements IClientCareConfigService {

    private ClientCareConfigMapper clientCareConfigMapper;
    private CareLevelItemMapper careLevelItemMapper;
    private CareItemMapper careItemMapper;

    @Autowired
    public ClientCareConfigServiceImpl(ClientCareConfigMapper clientCareConfigMapper, CareLevelItemMapper careLevelItemMapper) {
        this.clientCareConfigMapper = clientCareConfigMapper;
        this.careLevelItemMapper = careLevelItemMapper;
    }

    @Override
    public List<ClientCareConfig> getByClientId(int clientId) {
        return clientCareConfigMapper.findByClientId(clientId);
    }

    @Override
    public void assignCareLevel(int clientId, int careLevelId, List<Integer> itemIds) {
        Date now = new Date();
        Date threeMonthsLater = new Date(now.getTime() + 90L * 24 * 60 * 60 * 1000);
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
            clientCareConfigMapper.insert(config);
        }
    }

    @Override
    public void removeCareLevel(int clientId, int careLevelId) {
        clientCareConfigMapper.deleteByClientAndLevel(clientId, careLevelId);
    }

    public ClientCareConfigServiceImpl(ClientCareConfigMapper clientCareConfigMapper, CareItemMapper careItemMapper) {
        this.clientCareConfigMapper = clientCareConfigMapper;
        this.careItemMapper = careItemMapper;
    }

    @Override
    public void renewService(int configId, int addQuantity, Date newEndDate) {
        clientCareConfigMapper.renewService(configId, addQuantity, newEndDate);
    }

    @Override
    public void removeService(int configId) {
        clientCareConfigMapper.removeService(configId);
    }

    @Override
    public void addService(int clientId, int itemId, int quantity, Date endDate) {
        ClientCareConfig config = new ClientCareConfig();
        config.setClientId(clientId);
        config.setItemId(itemId);
        config.setQuantity(quantity);
        config.setStartDate(new Date());
        config.setEndDate(endDate);
        clientCareConfigMapper.insert(config);
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