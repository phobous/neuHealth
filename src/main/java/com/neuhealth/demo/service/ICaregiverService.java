package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.Caregiver;
import com.neuhealth.demo.domain.CaregiverClient;
import com.neuhealth.demo.domain.Client;

import java.util.List;

public interface ICaregiverService {
    List<Client> getUnassignedClientsByName(String name);

    List<Caregiver> searchCaregiversByName(String name);
    List<Client> getClientsByCaregiverIdAndName(int caregiverId, String name);
    Caregiver getCaregiverByClientId(int clientId);
    List<Object> getClientsWithoutCaregiver(String clientName);
    List<Object> getClientsByCaregiver(int caregiverId);
    void assignClientToCaregiver(int caregiverId, int clientId);
    void removeClientFromCaregiver(int caregiverId, int clientId);
}