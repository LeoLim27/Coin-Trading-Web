package com.leo.trading.service;

import com.leo.trading.domain.OrderStatus;
import com.leo.trading.domain.OrderType;
import com.leo.trading.modal.Coin;
import com.leo.trading.modal.Order;
import com.leo.trading.modal.OrderItem;
import com.leo.trading.modal.User;
import com.leo.trading.repository.OrderItemRepository;
import com.leo.trading.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private WalletService walletService;
  @Autowired
  private OrderItemRepository orderItemRepository;

  @Override
  public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
    double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
    Order order = new Order();
    order.setUser(user);
    order.setOrderItem(orderItem);
    order.setOrderType(orderType);
    order.setPrice(BigDecimal.valueOf(price));
    order.setTimestamp(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);

    return orderRepository.save(order);
  }

  @Override
  public Order getOrderById(Long orderId) throws Exception {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new Exception("order not found"));
  }

  @Override
  public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
    return orderRepository.findByUserId(userId);
  }

  /**
   * Helper Method for processOrder method
   * @param coin which coin in order item
   * @param quantity
   * @param buyPrice
   * @param sellPrice
   * @return created order item
   */
  private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
    OrderItem orderItem = new OrderItem();
    orderItem.setCoin(coin);
    orderItem.setQuantity(quantity);
    orderItem.setBuyPrice(buyPrice);
    orderItem.setSellPrice(sellPrice);
    return orderItemRepository.save(orderItem);
  }

  /**
   * A method to buy coin; creat order item & order -> wallet service(payment -> order status success and save to repo)
   * -> asset service (add to user's asset)
   * @param coin
   * @param quantity
   * @param user
   * @return
   * @throws Exception
   */
  @Transactional
  public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("Quantity should be greater than 0");
    }
    double buyPrice = coin.getCurrentPrice();
    OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
    Order order = createOrder(user, orderItem, OrderType.BUY);
    orderItem.setOrder(order);
    // go to wallet service and pay for the order first -> status success and save it to the repo
    walletService.payOrderPayment(order, user);
    order.setStatus(OrderStatus.SUCCESS);
    Order savedOrder = orderRepository.save(order);
    // Asset service; add to user's asset

    return savedOrder;
  }

  @Transactional
  public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("Quantity should be greater than 0");
    }
    double sellPrice = coin.getCurrentPrice();

    double buyPrice =  assetToSell.getPrice();

    OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
    Order order = createOrder(user, orderItem, OrderType.SELL);
    orderItem.setOrder(order);

    if (assetToSell.getQuantity)
    // go to wallet service and put the sell amount to wallet -> status success and save it to the repo
    walletService.payOrderPayment(order, user);
    order.setStatus(OrderStatus.SUCCESS);
    Order savedOrder = orderRepository.save(order);
    // Asset service; add to user's asset

    return savedOrder;
  }

  @Override
  public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
    return null;
  }
}
