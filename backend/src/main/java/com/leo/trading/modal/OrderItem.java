package com.leo.trading.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 * This class contains order item's detail; coin info, quantity, sell/buy price and Order instance to be mapped.
 */
@Entity
@Data
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private double quantity;

  // many orderItem can be mapped to one coin
  @ManyToOne
  private Coin coin;

  private double buyPrice;

  private double sellPrice;

  @JsonIgnore
  @OneToOne
  private Order order;

  public Coin getCoin(){
    return coin;
  }

  public double getBuyPrice() {
    return buyPrice;
  }

  public double getSellPrice() {
    return sellPrice;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public void setBuyPrice(double buyPrice) {
    this.buyPrice = buyPrice;
  }

  public void setSellPrice(double sellPrice) {
    this.sellPrice = sellPrice;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public void setCoin(Coin coin) {
    this.coin = coin;
  }
}
