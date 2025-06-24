package com.neuhealth.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("client_contacts")
public class ClientContact {
    private int id;
    private int clientId;
    private String contactName;
    private String phone;
    private String address;
}
