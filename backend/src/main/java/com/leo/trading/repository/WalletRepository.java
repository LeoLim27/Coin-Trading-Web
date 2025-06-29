package com.leo.trading.repository;

import com.leo.trading.modal.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  Wallet findByUserId(Long userId);
}
