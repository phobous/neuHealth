package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.domain.ClientRegisterDTO;

import java.util.List;

public interface IClientService extends IService<Client> {
    List<Client> findAll();
    Page<Client> findClientsByPage(int pageNum, int pageSize, String name, String type);

    List<Client> findAllActive();
    List<Client> searchClients(String name, String type);

    List<Client> searchClientsNoCg(boolean isAllocated);

    boolean registerClient(ClientRegisterDTO dto);

    //xh：boolean deleteClient(int clientId);
    boolean deleteClient(int id);
    boolean deleteClientsByIds(List<Long> ids);
    boolean updateClient(Client client);
}
