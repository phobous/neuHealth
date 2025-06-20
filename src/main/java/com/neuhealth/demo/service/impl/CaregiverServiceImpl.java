package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.Caregiver;
import com.neuhealth.demo.domain.CaregiverClient;
import com.neuhealth.demo.mapper.CaregiverClientMapper;
import com.neuhealth.demo.mapper.CaregiverMapper;
import com.neuhealth.demo.service.ICaregiverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaregiverServiceImpl implements ICaregiverService {

    private final CaregiverMapper caregiverMapper;
    private final CaregiverClientMapper caregiverClientMapper;



    public CaregiverServiceImpl(CaregiverMapper caregiverMapper, CaregiverClientMapper caregiverClientMapper) {
        this.caregiverMapper = caregiverMapper;
        this.caregiverClientMapper = caregiverClientMapper;
    }


    @Override
    public List<Caregiver> searchCaregiversByName(String name) {
        return caregiverMapper.selectByNameLike(name);
    }

    @Override
    public Caregiver getCaregiverByClientId(int clientId) {
        return caregiverClientMapper.selectCaregiverByClientId(clientId);
    }

    @Override
    public List<Object> getClientsWithoutCaregiver(String clientName) {
        return caregiverClientMapper.selectClientsWithoutCaregiver(clientName);
    }

    @Override
    public List<Object> getClientsByCaregiver(int caregiverId) {
        return caregiverClientMapper.selectClientsByCaregiverId(caregiverId);
    }

    @Override
    public void assignClientToCaregiver(int caregiverId, int clientId) {
        CaregiverClient mapping = new CaregiverClient();
        mapping.setCaregiverId(caregiverId);
        mapping.setClientId(clientId);
        int rows = caregiverClientMapper.insert(mapping);
        if (rows <= 0) {
            throw new RuntimeException("插入失败");
        }
    }


    @Override
    public void removeClientFromCaregiver(int caregiverId, int clientId) {
        caregiverClientMapper.deleteByCaregiverAndClient(caregiverId, clientId);
    }
}