package com.copter.game.assets.bonus;

import com.copter.game.assets.Airplane;

public class ShieldBonus extends Bonus{
  private static final String FIXTURE_DATA = "fixtures/bonusShield"; 
  private static final String FIXTURE_NAME = "shieldBonus";
  private static final String TEXTURE_REGION_NAME = "bonusShield";
  
  public ShieldBonus() {
    super(FIXTURE_DATA, FIXTURE_NAME, TEXTURE_REGION_NAME);    
  }

  @Override
  public void influencePlane() {
    super.influencePlane();
    Airplane.getInstance().setShield(true);
  }

  

}
