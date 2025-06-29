package com.leo.trading.request;

public class ResetPasswordRequest {
    private String otp;
    private String password;

    public void setOtp(String otp) {
        this.otp = otp;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getOtp() {
        return this.otp;
    }
    public String getPassword() {
        return this.password;
    }
}
