package com.neuhealth.demo.mapper;

import com.neuhealth.demo.domain.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
@Mapper
public interface HelpGiverMapper {
    List<ClientInfoDTO> getClientInfoByCaregiverIdAndName(@Param("caregiverId") Long caregiverId,
                                                      @Param("keyword") String keyword);
    @Select("""
        SELECT 
            id,
            client_id,
            item_id,
            caregiver_id,
            care_time,
            care_quantity
        FROM care_records
        WHERE client_id = #{clientId} AND is_deleted = false
        ORDER BY care_time DESC
    """)
    List<CareRecordVO> getCareRecordsByClientId(@Param("clientId") Long clientId);
    @Select("""
            SELECT 
                ROW_NUMBER() OVER() AS seq,
                c.name AS clientName,
                ci.id AS itemId, 
                ci.code AS itemCode,
                ci.name AS itemName,
                ci.price,
                ccc.quantity,
                ccc.end_date AS endDate
            FROM client_care_config ccc
            LEFT JOIN clients c ON ccc.client_id = c.id
            LEFT JOIN care_items ci ON ccc.item_id = ci.id
            WHERE ccc.client_id = #{clientId}
        """)
        List<ClientCareItemVO> getCareItemsByClientId(@Param("clientId") Long clientId);

    @Insert("""
        INSERT INTO care_records(client_id, item_id, caregiver_id, care_time, care_quantity, is_deleted,care_content)
        VALUES(#{clientId}, #{itemId}, #{caregiverId}, #{careTime}, #{careQuantity}, #{deleted},#{careContent})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCareRecord(CareRecord record);


    // 更新客户护理项目数量（减去护理数量）
    @Update("""
        UPDATE client_care_config
        SET quantity = quantity - #{careQuantity}
        WHERE client_id = #{clientId} AND item_id = #{itemId}
    """)
    int reduceCareItemQuantity(@Param("clientId") Integer clientId,
                               @Param("itemId") Integer itemId,
                               @Param("careQuantity") Integer careQuantity);
   // 新增: 多表联查抽取护理记录详情
    @Select("""
        SELECT 
            cr.id AS id,
            ci.code AS itemCode,        -- 护理项目编号
            ci.name AS itemName,        -- 护理项目名称
            cr.care_quantity AS careQuantity,  -- 护理数量
            cr.care_content AS careContent,    -- 护理内容
            u.real_name AS caregiverName,      -- 护理人员
            u.phone AS caregiverPhone,         -- 护理人员手机号
            cr.care_time AS careTime           -- 护理时间
        FROM care_records cr
        LEFT JOIN care_items ci ON cr.item_id = ci.id
        LEFT JOIN caregivers cg ON cr.caregiver_id = cg.id
        LEFT JOIN users u ON cg.user_id = u.id
        WHERE cr.client_id = #{clientId} AND cr.is_deleted = FALSE
        ORDER BY cr.care_time DESC
    """)
    List<CareRecordVO> getCareRecordDetailsByClientId(@Param("clientId") Long clientId);
    //逻辑删除
    @Update("""
        UPDATE care_records
        SET is_deleted = true
        WHERE id = #{id}
    """)
    int logicalDeleteCareRecord(@Param("id") Integer id);

    @Select("""
        SELECT 
            orq.id AS id,                     -- 序号，使用申请表 id
            c.name AS clientName,             -- 客户姓名
            orq.reason AS reason,          -- 外出事由
            orq.out_time AS outTime,          -- 外出时间
            orq.expected_return_time AS expectedReturnTime, -- 预计回院时间
            orq.actual_return_time AS actualReturnTime,     -- 实际回院时间
            cc.contact_name AS contactName,           -- 联系人姓名（从 client_contacts 表）
            cc.phone AS contactPhone,         -- 联系人电话（从 client_contacts 表）
            orq.detail AS detail,
            orq.status AS status      -- 审批状态
        FROM out_requests orq
        LEFT JOIN clients c ON orq.client_id = c.id
        LEFT JOIN client_contacts cc ON cc.client_id = c.id
        WHERE orq.client_id = #{clientId}
            AND orq.is_deleted = false  -- 这里必须加这个条件
        ORDER BY orq.out_time DESC
    """)
    List<OutRequestVO> getOutRequestsByClientId(@Param("clientId") Long clientId);

   //回院时间登记
    @Update("""
        UPDATE out_requests
        SET actual_return_time = #{actualReturnTime}
        WHERE id = #{id}
    """)
    int registerReturnTime(@Param("id") Integer id, @Param("actualReturnTime") Date actualReturnTime);


    //撤销外出申请
    @Update("""
            UPDATE out_requests
            SET is_deleted = true
            WHERE id = #{id}
        """)
        int logicalDeleteOutRequest(@Param("id") Integer id);

    @Insert("""
    INSERT INTO out_requests(client_id,reason,out_time,expected_return_time,actual_return_time,status,reviewer_id,review_time,detail,is_deleted) 
    VALUES (#{clientId},#{reason},#{outTime},#{expectedReturnTime},#{actualReturnTime},#{status},#{reviewerId},#{reviewTime},#{detail},#{isDeleted})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOutRequest(OutRequest outRequest);

    @Select("""
        SELECT 
            id, client_id, type, reason, check_in_date, check_out_date, 
            status, review_time, requested_at
        FROM check_out_requests
        WHERE client_id = #{clientId}
          AND is_deleted = false  -- 这里必须加这个条件
        ORDER BY requested_at DESC
    """)
    List<CheckOutRequestVO> getCheckOutRequestsByClientId(@Param("clientId") Integer clientId);


    @Insert("""
        INSERT INTO check_out_requests (
            client_id, 
            type, 
            reason, 
            requested_at, 
            status, 
            check_in_date, 
            check_out_date, 
            reviewer_id, 
            review_time, 
            detail
        ) 
        VALUES (
            #{clientId}, 
            #{type}, 
            #{reason}, 
            #{requestedAt}, 
            '已提交',   -- 默认值为 '已提交'
            #{checkInDate}, 
            #{checkOutDate}, 
            #{reviewerId}, 
            #{reviewTime}, 
            #{detail}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCheckOutRequest(CheckOutRequest checkOutRequest);
    //撤销退住申请
    @Update("""
            UPDATE check_out_requests
            SET is_deleted = true
            WHERE id = #{id}
        """)
    int logicalDeleteCheckOutRequest(@Param("id") Integer id);

}

