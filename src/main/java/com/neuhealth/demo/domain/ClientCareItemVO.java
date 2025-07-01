package com.neuhealth.demo.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ClientCareItemVO {
    private Integer seq;           // 序号（ROW_NUMBER）
    private String clientName;     // 客户姓名
    private String itemCode;       // 护理项目编号
    private Integer itemId;        // 护理项目id
    private String itemName;       // 护理项目名称
    private BigDecimal price;      // 护理项目价格
    private Integer quantity;      // 购买数量
    private Date endDate;          // 到期日期
    private String status;         // 护理项目状态
}
