package com.leo.trading.service;

import com.leo.trading.modal.Coin;
import com.leo.trading.modal.User;
import com.leo.trading.modal.WatchList;
import com.leo.trading.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService {

  @Autowired
  private WatchListRepository watchListRepository;

  @Override
  public WatchList findUserWatchList(Long userid) throws Exception {
    WatchList watchList = watchListRepository.findByUserId(userid);
    if (watchList == null) {
      throw new Exception("watchlist not found");
    }
    return watchList;
  }

  @Override
  public WatchList createWatchList(User user) {
    WatchList watchList = new WatchList();
    watchList.setUser(user);
    return watchListRepository.save(watchList);
  }

  @Override
  public WatchList findById(Long id) throws Exception {
    Optional<WatchList> watchlistOptional = watchListRepository.findById(id);
    if (watchlistOptional.isEmpty()) {
      throw new Exception("watchlist not found");
    }
    return watchlistOptional.get();
  }

  @Override
  public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
    WatchList watchlist = findUserWatchList(user.getId());
    if (watchlist.getCoins().contains(coin)) {
      watchlist.getCoins().remove(coin);
    } else {
      watchlist.getCoins().add(coin);
    }
    watchListRepository.save(watchlist);
    return coin;
  }
}
