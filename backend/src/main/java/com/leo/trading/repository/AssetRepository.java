package com.leo.trading.repository;

import com.leo.trading.modal.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.AbstractSet;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

  List<Asset> findByUserId(Long userId);

  Asset findByUserIdAndCoinId(Long userId, String coinId);
}
