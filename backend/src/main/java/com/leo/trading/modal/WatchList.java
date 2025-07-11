package com.leo.trading.modal;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WatchList {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  private User user;

  @ManyToMany
  private List<Coin> coins = new ArrayList<>();

  // Getter; Lombok doesn't work by some reason..

  public Long getId(){
    return id;
  }

  public void setId(Long id){
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Coin> getCoins() {
    return coins;
  }

  public void setCoins(List<Coin> coins) {
    this.coins = coins;
  }
}
