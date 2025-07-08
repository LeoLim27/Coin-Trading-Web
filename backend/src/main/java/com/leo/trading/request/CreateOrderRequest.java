package com.leo.trading.request;

import com.leo.trading.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
  private String coinId;
  private double quantity;
  private OrderType orderType;
}
