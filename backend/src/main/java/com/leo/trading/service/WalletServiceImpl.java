package com.leo.trading.service;

import com.leo.trading.modal.Order;
import com.leo.trading.modal.User;
import com.leo.trading.modal.Wallet;
import com.leo.trading.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class WalletServiceImpl implements WalletService {

  @Autowired
  private WalletRepository walletRepository;

  @Override
  public Wallet getUserWallet(User user) {
    Wallet wallet = walletRepository.findByUserId(user.getId());
    if (wallet == null) {
      wallet = new Wallet();
      wallet.setUser(user);
    }
    return wallet;
  }

  @Override
  public Wallet addBalance(Wallet wallet, Long amount) {
    BigDecimal balance = wallet.getBalance();
    BigDecimal newBalance = balance.add(BigDecimal.valueOf(amount));

    wallet.setBalance(newBalance);
    return walletRepository.save(wallet);
  }

  @Override
  public Wallet findWalletById(Long id) throws Exception {
    Optional<Wallet> wallet = walletRepository.findById(id);
    if (wallet.isPresent()) {
      return wallet.get();
    }
    throw new Exception("wallet not found");
  }

  @Override
  public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
    Wallet senderWallet = getUserWallet(sender);

    // check if sender's balance is larger than the amount the sender want to send
    if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
      throw new Exception("insufficient balance");
    }

    // subtract the amount from the sender's balance and update the wallet repo
    BigDecimal senderBalance = senderWallet
        .getBalance()
        .subtract(BigDecimal.valueOf(amount));
    senderWallet.setBalance(senderBalance);
    walletRepository.save(senderWallet);

    // add the amount to the receiver wallet and update the wallet repo
    BigDecimal receiverBalance = receiverWallet
        .getBalance().add(BigDecimal.valueOf(amount));
    receiverWallet.setBalance(receiverBalance);
    walletRepository.save(receiverWallet);

    return senderWallet;
  }

  @Override
  public Wallet payOrderPayment(Order orer, User user) {
    Wallet wallet = getUserWallet(user);


    return null;
  }
}
