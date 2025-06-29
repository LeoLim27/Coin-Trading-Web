package com.leo.trading.repository;

import com.leo.trading.modal.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPasswordToken, String> {

    ForgetPasswordToken findByUserId(Long userId);
}
