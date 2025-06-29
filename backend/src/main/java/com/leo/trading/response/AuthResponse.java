package com.leo.trading.response;

/** AuthResponse class contains the response of authentication
 * This class contains user's sign-in/sign-up result
 * Includes: token, status, message, error, twoFactorEnableInfo, and session
 * These info will be returned after user sign-in/sign-up as authResponse.
 */
public class AuthResponse {
    private String jwt;
    private boolean status;
    private String message;
    private String error;
    private boolean isTwoFactorAuthEnabled;
    private String session;

    public void setJwt(String jwt) {this.jwt = jwt;}
    public void setStatus(boolean status) {this.status = status;}
    public void setMessage(String message) {this.message = message;}
    public void setError(String error) {this.error = error;}
    public void setIsTwoFactorAuthEnabled(boolean twoFactorAuthEnabled) {this.isTwoFactorAuthEnabled = twoFactorAuthEnabled;}
    public void setSession(String session) {this.session = session;}
    public String getJwt() {return this.jwt;}
    public boolean isStatus() {return this.status;}
    public String getMessage() {return this.message;}
    public String getError() {return this.error;}
    public boolean isTwoFactorAuthEnabled() {return isTwoFactorAuthEnabled;}
    public String getSession() {return this.session;}

}
