package com.neuhealth.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuhealth.demo.domain.Client;

import java.util.List;

public interface IClientService extends IService<Client> {
    List<Client> searchClients(String name, String type);
    boolean registerClient(Client client);
    boolean deleteClient(int clientId);
    boolean updateClient(Client client);
}
