package com.leo.trading.service;

import com.leo.trading.domain.VerificationType;
import com.leo.trading.modal.ForgetPasswordToken;
import com.leo.trading.modal.User;
import com.leo.trading.repository.ForgetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    @Autowired
    private ForgetPasswordRepository forgetPasswordRepository;

    @Override
    public ForgetPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgetPasswordToken forgetPasswordToken = new ForgetPasswordToken();
        forgetPasswordToken.setUser(user);
        forgetPasswordToken.setSendTo(sendTo);
        forgetPasswordToken.setVerificationType(verificationType);
        forgetPasswordToken.setOtp(otp);
        forgetPasswordToken.setId(id);
        return forgetPasswordRepository.save(forgetPasswordToken);
    }

    @Override
    public ForgetPasswordToken findById(String id) {
        Optional<ForgetPasswordToken> forgetPasswordToken = forgetPasswordRepository.findById(id);
        return forgetPasswordToken.orElse(null);
    }

    @Override
    public ForgetPasswordToken findByUserId(Long userId) {
        return forgetPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgetPasswordToken token) {
        forgetPasswordRepository.delete(token);
    }
}
