package com.leo.trading.service;


import com.leo.trading.modal.Order;
import com.leo.trading.modal.User;
import com.leo.trading.modal.Wallet;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {

  Wallet getUserWallet(User user);

  Wallet addBalance(Wallet wallet, Long amount);

  Wallet findWalletById(Long id) throws Exception;

  Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;

  Wallet payOrderPayment(Order orer, User user) throws Exception;


}
