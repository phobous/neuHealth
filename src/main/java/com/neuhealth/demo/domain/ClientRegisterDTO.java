package com.neuhealth.demo.domain;

import lombok.Data;

@Data
public class ClientRegisterDTO {
    private Client client;
    private ClientContact contact;
}
