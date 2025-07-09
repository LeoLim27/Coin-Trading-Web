package com.leo.trading.modal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Asset {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private double quantity;
  private double buyPrice;
  @ManyToOne
  private Coin coin;
  @ManyToOne
  private User user;

  public void setUser(User user) {
    this.user = user;
  }

  public void setCoin(Coin coin) {
    this.coin = coin;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public void setBuyPrice(double buyPrice) {
    this.buyPrice = buyPrice;
  }

  public Long getId() {
    return id;
  }

  public double getQuantity(){
    return quantity;
  }

  public double getBuyPrice(){
    return buyPrice;
  }
}
