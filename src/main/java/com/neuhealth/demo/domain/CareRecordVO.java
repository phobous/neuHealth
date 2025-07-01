package com.neuhealth.demo.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CareRecordVO {
    private Long id;
    private Long clientId;
    private Long itemId;
    private String itemCode;        // ✅ 新增：护理项目编号
    private String itemName;        // 护理项目名称（来自 care_items 表）
    private Long caregiverId;
    private String caregiverName;   // 护工姓名（来自 caregivers 表）
    private String caregiverPhone;
    private Integer careQuantity;
    private LocalDateTime careTime;
    private String careContent;     // ✅ 可选：护理内容字段（若你前面 insert 了此字段）
}
