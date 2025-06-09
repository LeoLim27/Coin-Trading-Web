package com.leo.trading.modal;

import com.leo.trading.domain.VerificationType;

public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;

    public void setIsEnabled(boolean isEnabled) {this.isEnabled = isEnabled;}
    public void setSendTo(VerificationType sendTo) {this.sendTo = sendTo;}
    public boolean isEnabled() {return isEnabled;}
    public VerificationType getSendTo() {return sendTo;}
}
