package com.leo.trading.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

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
