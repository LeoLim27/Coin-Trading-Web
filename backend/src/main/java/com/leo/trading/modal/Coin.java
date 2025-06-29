package com.leo.trading.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Coin {

  @Id
  @JsonProperty("id")
  private String id;
  private String symbol;
  private String name;
  private String image;

  @JsonProperty("current_price")
  private double currentPrice;

  @JsonProperty("market_cap")
  private long marketCap;

  @JsonProperty("market_cap_rank")
  private int marketCapRank;

  @JsonProperty("fully_diluted_valuation")
  private long fullyDilutedValuation;

  @JsonProperty("total_volume")
  private long totalVolume;

  @JsonProperty("high_24h")
  private double high24h;

  @JsonProperty("low_24h")
  private double low24h;

  @JsonProperty("price_change_24h")
  private double priceChange24h;

  @JsonProperty("price_change_percentage_24h")
  private double priceChangePercentage24h;

  @JsonProperty("market_cap_change_24h")
  private long marketCapChange24h;

  @JsonProperty("market_cap_change_percentage_24h")
  private double marketCapChangePercentage24h;

  @JsonProperty("circulating_supply")
  private long circulatingSupply;

  @JsonProperty("total_supply")
  private long totalSupply;

  @JsonProperty("max_supply")
  private long maxSupply;

  private double ath;

  @JsonProperty("ath_change_percentage")
  private double athChangePercentage;

  @JsonProperty("ath_date")
  private Date athDate;

  private double atl;

  @JsonProperty("atl_change_percentage")
  private double atlChangePercentage;

  @JsonProperty("atl_date")
  private Date atlDate;

  @JsonProperty("roi")
  @JsonIgnore
  private Object roi; // Placeholder; define proper type if ROI has structure

  @JsonProperty("last_updated")
  private Date lastUpdated;

  // --- Getters and setters omitted for brevity ---

  // Optionally: constructors, toString, equals, hashCode
}
