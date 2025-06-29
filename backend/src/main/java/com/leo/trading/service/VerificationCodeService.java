package com.leo.trading.service;

import com.leo.trading.domain.VerificationType;
import com.leo.trading.modal.User;
import com.leo.trading.modal.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUserId(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
