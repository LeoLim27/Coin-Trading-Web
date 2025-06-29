package com.leo.trading.service;

import com.leo.trading.domain.VerificationType;
import com.leo.trading.modal.ForgetPasswordToken;
import com.leo.trading.modal.User;

public interface ForgetPasswordService {
    ForgetPasswordToken createToken(User user, String id,
                                    String otp, VerificationType verificationType,
                                    String sendTo);
    ForgetPasswordToken findById(String id);

    ForgetPasswordToken findByUserId(Long userId);

    void deleteToken(ForgetPasswordToken token);
}
