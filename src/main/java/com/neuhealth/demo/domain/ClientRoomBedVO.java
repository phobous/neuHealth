package com.neuhealth.demo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ClientRoomBedVO {
    private Integer clientId;
    private String name;
    private String gender;
    private String nation;
    private Date birthDate;
    private String idCard;
    private String bloodType;
    private Date checkInDate;
    private Date contractEndDate;
    private String type;
    private Date createdAt;

    private Integer bedId;
    private String bedNumber;

    private Integer roomId;
    private String roomNumber;
    private Integer floor;
    private String building;

    private String contactName;
    private String phone;
}
