package com.neuhealth.demo.domain;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer requestId;
    private Integer reviewerId;
    private String status;
    private String detail;
}
