package com.copter.game.assets.collisions.colliders;

import com.copter.game.assets.WorldAsset;
import com.copter.game.assets.bonus.Bonus;

public class BonusCollider extends Collider {
  private static final float HIDING_X = -10;
  private static final float HIDING_Y = -10;
  private static final float HIDING_ANGLE = 0;
  
  
  public BonusCollider(WorldAsset hero, WorldAsset asset) {
    super(hero, asset);
  }

  @Override
  public void collide() {   
    Bonus bonusAsset = (Bonus) asset;
    bonusAsset.influencePlane();
    bonusAsset.getBody().setTransform(HIDING_X, HIDING_Y, HIDING_ANGLE);
    bonusAsset.getBody().setActive(false);
  }

}
