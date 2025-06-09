package com.leo.trading.modal;

import com.leo.trading.domain.VerificationType;
import jakarta.persistence.*;

@Entity
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otp;
    @OneToOne
    private User user;
    private String email;
    private String mobile;
    private VerificationType verificationType;

    public void setId(Long id) {
        this.id = id;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setVerificationType(VerificationType verificationType) {
        this.verificationType = verificationType;
    }
    public Long getId() {return this.id;}
    public String getOtp() {return this.otp;}
    public String getEmail() {return this.email;}
    public String getMobile() {return this.mobile;}
    public VerificationType getVerificationType() {return this.verificationType;}
}
