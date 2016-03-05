package com.copter.game.assets.collisions.colliders;

import com.copter.game.assets.WorldAsset;
import com.copter.game.assets.bonus.Bonus;

public class BonusCollider extends Collider { 
  
  public BonusCollider(WorldAsset hero, WorldAsset asset) {
    super(hero, asset);
  }

  @Override
  public void collide() {   
    Bonus bonusAsset = (Bonus) asset;
    bonusAsset.influencePlane();
  }

}
