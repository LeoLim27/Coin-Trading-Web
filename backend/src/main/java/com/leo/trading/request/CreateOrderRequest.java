package com.leo.trading.request;

import com.leo.trading.domain.OrderType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class CreateOrderRequest {
  private String coinId;
  private double quantity;
  private OrderType orderType;

  public String getCoinId() {
    return coinId;
  }

  public double getQuantity() {
    return quantity;
  }

  public OrderType getOrderType() {
    return orderType;
  }
}
