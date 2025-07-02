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
}
