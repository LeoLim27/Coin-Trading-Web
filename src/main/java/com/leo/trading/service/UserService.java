package com.leo.trading.service;

import com.leo.trading.domain.VerificationType;
import com.leo.trading.modal.User;
import org.springframework.stereotype.Service;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserProfileByEmail(String email) throws Exception;
    public User findUserProfileById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(VerificationType verificationType,
                                              String sendTo,
                                              User user) throws Exception;

    User updatePassword(User user, String newPassword);
}
