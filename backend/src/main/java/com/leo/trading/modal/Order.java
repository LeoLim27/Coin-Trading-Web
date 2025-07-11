package com.leo.trading.modal;

import com.leo.trading.domain.OrderStatus;
import com.leo.trading.domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class contains user info, order item, price, order type and time stamp.
 * Order details (coin info, quantity, buy price, sell price are stored in order item)
 * Order and Order Item are mapped to each other (one order contains one order item)
 */
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

  public User getUser() {
    return user;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }
}
