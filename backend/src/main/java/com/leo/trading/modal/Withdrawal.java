package com.leo.trading.modal;

import com.leo.trading.domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Withdrawal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private WithdrawalStatus status;

  private Long amount;

  @ManyToOne
  private User user;

  private LocalDateTime date = LocalDateTime.now();

  public Long getAmount(){
    return amount;
  }

  public void setAmount(Long amount){
    this.amount = amount;
  }

  public void setStatus(WithdrawalStatus status){
    this.status = status;
  }

  public void setUser(User user){
    this.user = user;
  }

  public void setDate(LocalDateTime date){
    this.date = date;
  }
}
