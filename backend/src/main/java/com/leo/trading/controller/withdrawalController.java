package com.leo.trading.controller;

import com.leo.trading.modal.User;
import com.leo.trading.modal.Wallet;
import com.leo.trading.modal.Withdrawal;
import com.leo.trading.service.UserService;
import com.leo.trading.service.WalletService;
import com.leo.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class withdrawalController {

  @Autowired
  private WithdrawalService withdrawalService;

  @Autowired
  private UserService userService;

  private WalletService walletService;

  // put new withdrawal request to server
  @PostMapping("/api/withdrawal/{amount}")
  public ResponseEntity<?> withdrawalRequest(
      @PathVariable Long amount,
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Wallet userWallet = walletService.getUserWallet(user);

    Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
    walletService.addBalance(userWallet, -withdrawal.getAmount());

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  // update info in server
  @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
  public ResponseEntity<?> proceedWithdrawalRequest(
      @PathVariable Long id,
      @PathVariable boolean accept,
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Withdrawal withdrawal = withdrawalService.proceedWithdrawal(id, accept);
    Wallet userWallet = walletService.getUserWallet(user);
    if (!accept) {
      // give the balance back if user cannot withdraw the money
      walletService.addBalance(userWallet, withdrawal.getAmount());
    }
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @GetMapping("/api/withdrawal")
  public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
      @RequestHeader("Authorization") String jwt
  ) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    List<Withdrawal> withdrawal = withdrawalService.getUsersWithdrawalHistory(user);
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @GetMapping("/api/admin/withdrawal")
  public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
      @RequestHeader("Authorization") String jwt
  ) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequest();

    return new ResponseEntity<>(withdrawals, HttpStatus.OK);
  }
}
