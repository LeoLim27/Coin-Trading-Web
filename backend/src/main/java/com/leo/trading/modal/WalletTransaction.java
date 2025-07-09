package com.leo.trading.modal;

import com.leo.trading.domain.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class WalletTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Wallet wallet;

  private WalletTransactionType type;

  private LocalDate date;

  // which wallet to transfer
  // if WalletTransactionType is buy or sell, then this will be null
  // if and only if wallet_transfer, there will be id of the wallet to transfer
  private String transferId;

  private String purpose;

  private Long amount;

  public Long getAmount() {
    return amount;
  }
}
