package com.copter.game.assets.collisions.colliders;

import com.copter.game.assets.WorldAsset;

public abstract class Collider {
  protected WorldAsset hero;
  protected WorldAsset asset;
  
  public Collider(WorldAsset hero, WorldAsset asset) {
    this.hero = hero;
    this.asset = asset;
  }
  
  /**
   * Abstract method for handling collision.
   */
  public abstract void collide();

}
