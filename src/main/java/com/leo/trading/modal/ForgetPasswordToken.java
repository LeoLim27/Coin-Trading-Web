package com.leo.trading.modal;

import com.leo.trading.domain.VerificationType;
import jakarta.persistence.*;

@Entity
public class ForgetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToOne // to check which user forgot the password
    private User user;
    private String otp;
    private VerificationType verificationType;
    private String token;
    // either mobile number or email address depending on verification type
    private String sendTo;

    public void setId(String id) {
        this.id = id;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public void setVerificationType(VerificationType verificationType) {
        this.verificationType = verificationType;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
    public String getId(){
        return this.id;
    }
    public User getUser() {
        return this.user;
    }
    public String getOtp() {
        return this.otp;
    }
    public VerificationType getVerificationType() {
        return this.verificationType;
    }
    public String getToken() {
        return this.token;
    }
    public String getSendTo() {
        return this.sendTo;
    }
}
