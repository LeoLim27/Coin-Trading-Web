package com.leo.trading.service;

import com.leo.trading.modal.Asset;
import com.leo.trading.modal.Coin;
import com.leo.trading.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssetService {

  Asset createAsset(User user, Coin coin, double quantity);

  Asset getAssetById(Long assetId) throws Exception;

  Asset getAssetByUserId(Long userId, Long assetId);

  List<Asset> getUserAssets(Long userId);

  Asset updateAsset(Long assetId, double quantity) throws Exception;

  Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

  void deleteAsset(Long assetId);


}
