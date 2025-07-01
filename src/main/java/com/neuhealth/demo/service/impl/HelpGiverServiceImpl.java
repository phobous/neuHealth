package com.neuhealth.demo.service.impl;

import com.neuhealth.demo.domain.*;
import com.neuhealth.demo.mapper.ClientMapper;
import com.neuhealth.demo.mapper.HelpGiverMapper;
import com.neuhealth.demo.service.IHelpGiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class HelpGiverServiceImpl implements IHelpGiverService {

    @Autowired
    private HelpGiverMapper helpGiverMapper;

    @Override
    public List<ClientInfoDTO> getClientInfoByCaregiverIdAndName(Long caregiverId, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = ""; // 空字符串代表匹配全部
        }
        return helpGiverMapper.getClientInfoByCaregiverIdAndName(caregiverId, keyword);
    }

    @Override
    public List<CareRecordVO> getRecordsByClientId(Long clientId) {
        return helpGiverMapper.getCareRecordsByClientId(clientId);
    }

     @Override
        public List<ClientCareItemVO> getCareItemsByClientId(Long clientId) {
            List<ClientCareItemVO> items = helpGiverMapper.getCareItemsByClientId(clientId);

            LocalDate today = LocalDate.now();

            for (ClientCareItemVO item : items) {
                if (item.getEndDate() != null) {
                    // 把 Date 转 LocalDate
                    LocalDate endDate = item.getEndDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                    if (endDate.isBefore(today)) {
                        item.setStatus("过期");
                        continue;
                    }
                }

                if (item.getQuantity() != null && (item.getQuantity() < 0 || item.getQuantity() == 0)) {
                    item.setStatus("欠费");
                }else if(item.getQuantity() > 0 && item.getQuantity() < 5){
                    item.setStatus("即将用完");
                } else {
                    item.setStatus("正常");
                }
            }

            return items;
        }

        @Override
        @Transactional
        public void addCareRecordAndUpdateQuantity(CareRecord record) {
            // 插入护理记录
            helpGiverMapper.insertCareRecord(record);

            // 更新剩余护理数量
            helpGiverMapper.reduceCareItemQuantity(record.getClientId(), record.getItemId(), record.getCareQuantity());
        }

        /**
         * 查询护理记录详情
         */
        @Override
        public List<CareRecordVO> getCareRecordDetailsByClientId(Long clientId) {
            return helpGiverMapper.getCareRecordDetailsByClientId(clientId);
        }
        @Override
        public boolean deleteCareRecordById(Integer id) {
            return helpGiverMapper.logicalDeleteCareRecord(id) > 0;
        }
        @Override
        public List<OutRequestVO> getOutRequestsByClientId(Long clientId) {
            return helpGiverMapper.getOutRequestsByClientId(clientId);
        }

        @Override
        public boolean registerReturnTime(Integer id, Date actualReturnTime) {
            int rows = helpGiverMapper.registerReturnTime(id, actualReturnTime);
            return rows > 0;
        }

       @Override
        public boolean logicalDeleteOutRequest(Integer id) {
            int rows = helpGiverMapper.logicalDeleteOutRequest(id);
            return rows > 0;
        }
        @Override
        public int addOutRequest(OutRequest outRequest) {
            // 设置默认值（可根据实际业务调整）
            outRequest.setStatus("已提交");
            outRequest.setDeleted(false);
            outRequest.setReviewTime(null);       // 初次不设置审批时间
            outRequest.setReviewerId(null);       // 初次不设置审核人

            return helpGiverMapper.insertOutRequest(outRequest);
        }
        @Override
        public List<CheckOutRequestVO> getCheckOutRequestsByClientId(Integer clientId) {
            return helpGiverMapper.getCheckOutRequestsByClientId(clientId);
        }

        // 实现插入退住申请的方法
        @Override
        public int insertCheckOutRequest(CheckOutRequest checkOutRequest) {
            return helpGiverMapper.insertCheckOutRequest(checkOutRequest);
        }

         @Override
        public boolean logicalDeleteCheckOutRequest(Integer id) {
            int rows = helpGiverMapper.logicalDeleteCheckOutRequest(id);
            return rows > 0;
        }
}
