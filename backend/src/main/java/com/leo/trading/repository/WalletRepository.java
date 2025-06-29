package com.leo.trading.repository;

import com.leo.trading.modal.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

  Wallet findByUserId(Long userId);
}
