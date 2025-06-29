package com.leo.trading.service;

import com.leo.trading.modal.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CoinService {

  List<Coin> getCoinList(int page) throws Exception;

  String getMarketChart(String coinId, int days) throws Exception;

  // use gecko api
  String getCoinDetails(String coinId) throws Exception;

  // use db
  Coin findById(String coinId) throws Exception;

  String searchCoin(String keyword) throws Exception;

  String getTop50CoinsByMarketCapRank() throws Exception;

  String getTradingCoins() throws Exception;
}
