package com.leo.trading.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class TwoFactorOTP {

    @Id
    private String id;
    private String otp;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private User user;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jwt;

    public void setId(String id) {
        this.id = id;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getId() {return this.id;}
    public String getOtp() {return this.otp;}
    public String getJwt() {return this.jwt;}
    public User getUser() {return this.user;}
}
