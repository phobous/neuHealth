package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.mapper.ClientMapper;
import com.neuhealth.demo.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<Client> findAll() {
        return clientMapper.selectList(null);
    }
    @Override
    public List<Client> findAllActive() {
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        return clientMapper.selectList(queryWrapper);
    }

    @Override
    public List<Client> searchClients(String name, String type) {
        return clientMapper.searchClients(name, type);
    }

    @Override
    public boolean registerClient(Client client) {
        if (client.getContractEndDate().before(client.getCheckInDate())) {
            throw new IllegalArgumentException("合同到期时间不能早于入住时间");
        }
        String birthStr = extractBirthDateStr(client.getIdCard());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(birthStr, formatter);
        Date birthDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        client.setCreatedAt(new Date());
        client.setDeleted(false);
        client.setBirthDate(birthDate);
        client.setAge(calculateAgeFromIdCard(client.getIdCard()));
        boolean saved = this.save(client);
        if (saved) {
            clientMapper.updateBedStatus(client.getBedId(), "有人");
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
            clientMapper.updateBedStatus(client.getBedId(), "空闲");
            //clientMapper.hideCurrentBedUsage(clientId);
        }
        return updated;
    }

    @Override
    public boolean deleteClientsByIds(List<Long> ids) {
        return this.removeByIds(ids);
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


    public static String extractBirthDateStr(String idCard) {
        if (idCard == null || idCard.length() < 10) {
            throw new IllegalArgumentException("身份证号格式不正确");
        }
        return idCard.substring(6, 14);
    }


    public static int calculateAgeFromIdCard(String idCard) {
        String birthStr = extractBirthDateStr(idCard);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(birthStr, formatter);
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

}
