package com.leo.trading.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leo.trading.domain.USER_ROLE;
import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // automatic id generation
    private long id;
    private String fullName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // only write from client side
    private String password;
    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    public void setId(long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {this.fullName = fullName;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setTwoFactorAuth(TwoFactorAuth twoFactorAuth) {this.twoFactorAuth = twoFactorAuth;}
    public void setRole(USER_ROLE role) {this.role = role;}
    public Long getId(){return id;}
    public String getFullName() {return fullName;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public TwoFactorAuth getTwoFactorAuth() {return twoFactorAuth;}
    public USER_ROLE getRole() {return role;}
}
