// --- 护理记录 Service ---

package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.CareRecord;

import java.util.List;

public interface ICareRecordService {
    List<CareRecord> getByClientId(int clientId);
    void deleteRecord(int recordId);
    void addCareRecord(CareRecord record);
    List<CareRecord> getByCaregiverId(int caregiverId);
}