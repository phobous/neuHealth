package com.neuhealth.demo.domain;

import lombok.Data;

@Data
public class ClientInfoDTO {
    private Integer clientId;
    private String name;
    private Integer age;
    private String gender;
    private String roomNumber;
    private String bedNumber;
    private String building;
    private String phone;
    private String careLevel;
    private Long careLevelId;
}
