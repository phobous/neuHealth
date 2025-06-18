package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rooms")
public class Room {
    private int id;
    private String roomNumber;
    private int floor;
    private String building;
    private int bedCount;
    private String status;
    private boolean isDeleted;
}
