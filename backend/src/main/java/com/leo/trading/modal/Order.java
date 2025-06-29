package com.leo.trading.modal;

import com.leo.trading.domain.OrderStatus;
import com.leo.trading.domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // One user can have many orders; many-to-one
  @ManyToOne
  private User user;

  // nullable false; this field is required
  @Column(nullable = false)
  private OrderType orderType;

  // required field
  @Column(nullable = false)
  private BigDecimal price;

  private LocalDateTime timestamp = LocalDateTime.now();

  // required field
  @Column(nullable = false)
  private OrderStatus status;

  // one order has only one order item (order > orderItem)
  @OneToOne (mappedBy = "order", cascade = CascadeType.ALL)
  private OrderItem orderItem;
}
