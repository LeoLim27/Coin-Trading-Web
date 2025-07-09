package com.leo.trading.service;

import com.leo.trading.modal.User;
import com.leo.trading.modal.Withdrawal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WithdrawalService {

  Withdrawal requestWithdrawal(Long amount, User user);

  Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception;

  List<Withdrawal> getUsersWithdrawalHistory(User user);

  List<Withdrawal> getAllWithdrawalRequest();
}
