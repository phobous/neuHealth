package com.neuhealth.demo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CareRecordDTO {
    private Integer id;               // 序号（主键 ID）
    private String itemCode;         // 护理项目编号
    private String itemName;         // 护理项目名称
    private Integer careQuantity;    // 护理数量
    private String careDescription;  // 护理内容（项目描述）
    private String caregiverName;    // 护理人员姓名
    private String caregiverPhone;   // 护理人员电话
    private Date careTime;           // 护理时间
}
