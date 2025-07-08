package com.leo.trading.service;

import com.leo.trading.domain.OrderType;
import com.leo.trading.modal.Coin;
import com.leo.trading.modal.Order;
import com.leo.trading.modal.OrderItem;
import com.leo.trading.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

  Order createOrder(User user, OrderItem orderItem, OrderType orderType);

  Order getOrderById(Long orderId) throws Exception;

  List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

  Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;


}
