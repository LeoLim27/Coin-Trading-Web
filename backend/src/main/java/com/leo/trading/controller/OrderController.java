package com.leo.trading.controller;

import com.leo.trading.domain.OrderType;
import com.leo.trading.modal.Coin;
import com.leo.trading.modal.Order;
import com.leo.trading.modal.User;
import com.leo.trading.request.CreateOrderRequest;
import com.leo.trading.service.CoinService;
import com.leo.trading.service.OrderService;
import com.leo.trading.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private OrderService orderService;

  private UserService userService;

  private CoinService coinService;

  // private WalletTransactionService walletTransactionService;

  @PostMapping("/pay")
  public ResponseEntity<Order> payOrderPayment(
      @RequestHeader("Authorization") String jwt,
      @RequestBody CreateOrderRequest req
  ) throws Exception{
    User user = userService.findUserProfileByJwt(jwt);
    Coin coin = coinService.findById(req.getCoinId());

    Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);

    return ResponseEntity.ok(order);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Order> getOrderById(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long orderId
  ) throws Exception{
    User user = userService.findUserProfileByJwt(jwt);
    Order order = orderService.getOrderById(orderId);
    if (order.getUser().getId().equals(user.getId())) {
      return ResponseEntity.ok(order);
    } else {
      throw new Exception("you don't have an access");
    }
  }

  @GetMapping()
  public ResponseEntity<List<Order>> getAllOrdersForUser(
      @RequestHeader("Authorization") String jwt,
      @RequestParam(required = false) OrderType order_type,
      @RequestParam(required = false) String asset_symbol
  ) throws Exception {
    Long userId = userService.findUserProfileByJwt(jwt).getId();
    List<Order> userOrders = orderService.getAllOrdersOfUser(userId, order_type, asset_symbol);
    return ResponseEntity.ok(userOrders);
  }

}
