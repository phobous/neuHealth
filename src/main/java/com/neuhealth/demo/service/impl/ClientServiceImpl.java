package com.neuhealth.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuhealth.demo.domain.*;
import com.neuhealth.demo.mapper.BedMapper;
import com.neuhealth.demo.mapper.ClientBedMappingMapper;
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
    @Autowired
    private BedMapper bedMapper;
    @Autowired
    private ClientBedMappingMapper clientBed;

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
    public Page<Client> findClientsByPage(int pageNum, int pageSize, String name, String type) {
        System.out.println("分页第几页？: " + pageNum );
        Page<Client> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Client::getisDeleted, 0);

        if (name != null && !name.trim().isEmpty()) {
            wrapper.like(Client::getName, name);
        }

        if (type != null && !type.trim().isEmpty()) {
            wrapper.eq(Client::getType, type);
        }

        return clientMapper.selectPage(page, wrapper);
    }


    @Override
    public List<Client> searchClients(String name, String type) {
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        return clientMapper.searchClients(name, type);
    }

    @Override
    public List<Client> searchClientsNoCg( boolean isAllocated) {
        return clientMapper.searchClientsNoCg(isAllocated);
    }

    @Override
    public boolean registerClient(ClientRegisterDTO dto) {
        Client client = dto.getClient();
        ClientContact contact = dto.getContact();
        if (client.getContractEndDate().before(client.getCheckInDate())) {
            throw new IllegalArgumentException("合同到期时间不能早于入住时间");
        }
        if(if_available(client.getBedId())){
            throw new IllegalStateException("该床位已有入住人员，无法注册");
        }
        client.setCreatedAt(new Date());
        client.setDeleted(false);
        client.setBirthDate(parseBirthDate(client.getIdCard()));
        client.setAge(calculateAgeFromIdCard(client.getIdCard()));
        boolean saved = this.save(client);
        if (saved) {
            clientMapper.updateBedStatus(client.getBedId(), "有人");
            ClientBedMapping mapping = new ClientBedMapping();
            mapping.setClientId(client.getId());
            mapping.setBedId(client.getBedId());
            mapping.setAssignedAt(client.getCreatedAt());
            mapping.setIsDeleted(false);
            mapping.setCreatedAt(new Date());
            mapping.setUpdatedAt(new Date());

            clientBed.insert(mapping);

            if (contact != null) {
                contact.setClientId(client.getId());
                contact.setContactName(contact.getContactName());
                contact.setPhone(contact.getPhone());
                clientContact.insert(contact);
            }
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
            clientBed.markDeletedByClientId(clientId);
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

    public static Date parseBirthDate(String idCard) {
        String birthStr = extractBirthDateStr(idCard);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(birthStr, formatter);
        localDate = localDate.plusDays(1);

        // 2. 转换为 java.util.Date
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    public static int calculateAgeFromIdCard(String idCard) {
        String birthStr = extractBirthDateStr(idCard);
        System.out.print(birthStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.print(formatter);
        LocalDate birthDate = LocalDate.parse(birthStr, formatter);
        LocalDate now = LocalDate.now();
        System.out.print(birthDate);
        return Period.between(birthDate, now).getYears();
    }

    public boolean if_available(int id){
        Bed bed = bedMapper.selectById(id);
        if("空闲".equals(bed.getStatus())){ return false;}
        else return true;
    }

}
