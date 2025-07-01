package com.neuhealth.demo.domain;

public class OutRequestVO extends OutRequest {
    private String clientName;
    private String contactName;   // 联系人姓名
    private String contactPhone;  // 联系人电话
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
