package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.mapper.ClientMapper;
import com.neuhealth.demo.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<Client> searchClients(String name, String type) {
        return clientMapper.searchClients(name, type);
    }

    @Override
    public boolean registerClient(Client client) {
        if (client.getContractEndDate().before(client.getCheckInDate())) {
            throw new IllegalArgumentException("合同到期时间不能早于入住时间");
        }
        client.setCreatedAt(new Date());
        client.setDeleted(false);
        client.setType(determineClientType(client.getMealId())); // 示例处理
        boolean saved = this.save(client);
        if (saved) {
            clientMapper.updateBedStatus(client.getBedId(), "occupied");
        }
        return saved;
    }

    @Override
    public boolean deleteClient(int clientId) {
        Client client = this.getById(clientId);
        if (client == null) return false;

        client.setDeleted(true);
        boolean updated = this.updateById(client);
        if (updated) {
            clientMapper.updateBedStatus(client.getBedId(), "free");
            clientMapper.hideCurrentBedUsage(clientId);
        }
        return updated;
    }

    @Override
    public boolean updateClient(Client client) {
        Client existing = this.getById(client.getId());
        if (existing == null) return false;

        boolean updated = this.updateById(client);
        if (updated && client.getContractEndDate() != null &&
            !client.getContractEndDate().equals(existing.getContractEndDate())) {
            clientMapper.updateCurrentBedLeaveDate(client.getId(), client.getContractEndDate());
        }
        return updated;
    }

    private String determineClientType(int mealId) {
        return mealId == 0 ? "自理" : "护理";
    }
}
