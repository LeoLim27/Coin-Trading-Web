package com.leo.trading.controller;

import com.leo.trading.modal.User;
import com.leo.trading.modal.Wallet;
import com.leo.trading.modal.WalletTransaction;
import com.leo.trading.service.UserService;
import com.leo.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/wallet")
public class WalletController {

  @Autowired
  private WalletService walletService;

  @Autowired
  private UserService userService;

  @GetMapping("/api/wallet")
  public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception{
    User user = userService.findUserProfileByJwt(jwt);
    Wallet wallet = walletService.getUserWallet(user);
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  // {walletId}; path variable
  @PutMapping("/api/wallet/{walletId}/transfer")
  public ResponseEntity<Wallet> walletToWalletTransfer(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long walletId,
      @RequestBody WalletTransaction req
  ) throws Exception {
    User senderUser = userService.findUserProfileByJwt(jwt);
    Wallet receiverWallet = walletService.findWalletById(walletId);
    // wallet contains sender's wallet
    Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, req.getAmount());
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }
}
