package com.neuhealth.demo.service;

import com.neuhealth.demo.domain.*;

import java.util.Date;
import java.util.List;

public interface IHelpGiverService {

    List<ClientInfoDTO> getClientInfoByCaregiverIdAndName(Long caregiverId, String keyword);
    List<CareRecordVO> getRecordsByClientId(Long clientId);

    List<ClientCareItemVO> getCareItemsByClientId(Long clientId);


   void addCareRecordAndUpdateQuantity(CareRecord record);
   /**
     * 根据客户ID查询护理记录详情（多表联查）
     */
    List<CareRecordVO> getCareRecordDetailsByClientId(Long clientId);
    //逻辑删除
    boolean deleteCareRecordById(Integer id); // 新增逻辑删除方法
    //查询外出申请
    List<OutRequestVO> getOutRequestsByClientId(Long clientId);
    //注册实际返回时间
    boolean registerReturnTime(Integer id, Date actualReturnTime);
     /**
     * 逻辑删除外出申请记录
     * @param id 记录ID
     * @return true 删除成功，false 失败
     */
    boolean logicalDeleteOutRequest(Integer id);
    /**
     * 添加外出登记申请
     * @param outRequest 外出申请对象
     * @return 成功插入的记录数
     */
    int addOutRequest(OutRequest outRequest);
    /**
     * 根据客户ID查询退住申请记录
     * @param clientId 客户ID
     * @return 退住申请信息列表
     */
    List<CheckOutRequestVO> getCheckOutRequestsByClientId(Integer clientId);
    // 插入退住申请
    int insertCheckOutRequest(CheckOutRequest checkOutRequest);
    /**
     * 逻辑删除退住申请记录
     * @param id 记录ID
     * @return true 删除成功，false 失败
     */
    boolean logicalDeleteCheckOutRequest(Integer id);
}
