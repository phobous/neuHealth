package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.Caregiver;
import com.neuhealth.demo.domain.CaregiverClient;

import java.util.List;

public interface ICaregiverService {
    List<Caregiver> searchCaregiversByName(String name);
    Caregiver getCaregiverByClientId(int clientId);
    List<Object> getClientsWithoutCaregiver(String clientName);
    List<Object> getClientsByCaregiver(int caregiverId);
    void assignClientToCaregiver(int caregiverId, int clientId);
    void removeClientFromCaregiver(int caregiverId, int clientId);
}