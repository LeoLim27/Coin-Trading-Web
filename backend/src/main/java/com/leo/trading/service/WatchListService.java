package com.leo.trading.service;

import com.leo.trading.modal.Coin;
import com.leo.trading.modal.User;
import com.leo.trading.modal.WatchList;
import org.springframework.stereotype.Service;

@Service
public interface WatchListService {

  WatchList findUserWatchList(Long userid) throws Exception;

  WatchList createWatchList(User user);

  WatchList findById(Long id) throws Exception;

  Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
