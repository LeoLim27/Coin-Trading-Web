package com.leo.trading.request;


import com.leo.trading.domain.VerificationType;

public class ForgetPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
    public void setOtp(VerificationType verificationType) {
        this.verificationType = verificationType;
    }
    public String getSendTo() {
        return this.sendTo;
    }
    public VerificationType getVerificationType() {
        return this.verificationType;
    }
}
