package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("beds")
public class Bed {
    private int id;
    private int roomId;
    private String bedNumber;
    private String status;
    private boolean isDeleted;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
