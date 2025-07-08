package com.leo.trading.controller;

import com.leo.trading.modal.Asset;
import com.leo.trading.modal.User;
import com.leo.trading.service.AssetService;
import com.leo.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

  private AssetService assetService;
  @Autowired
  private UserService userService;

  @GetMapping("/{assetId}")
  public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
    Asset asset = assetService.getAssetById(assetId);
    return ResponseEntity.ok(asset);
  }

  @GetMapping("/coin/{coinId}/user")
  public ResponseEntity<Asset> geAssetByUserIdAndCoinId(
      @PathVariable String coinId,
      @RequestHeader("Authorization") String jwt
  ) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
    return ResponseEntity.ok().body(asset);
  }

  @GetMapping()
  public ResponseEntity<List<Asset>> getAssetsForUser(
      @RequestHeader("Authorization") String jwt
  ) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    List<Asset> assets = assetService.getUserAssets(user.getId());
    return ResponseEntity.ok().body(assets);
  }
}
