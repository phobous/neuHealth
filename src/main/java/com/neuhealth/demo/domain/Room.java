package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rooms")
public class Room {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; // 注意是包装类型 Integer，而不是 int
    private String roomNumber;
    private int floor;
    private String building;
    private int bedCount;
    private String status;
    private boolean isDeleted;
}
