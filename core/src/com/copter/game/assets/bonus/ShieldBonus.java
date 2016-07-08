package com.copter.game.assets.bonus;

import com.copter.game.assets.Airplane;

public class ShieldBonus extends Bonus{
  private static final String FIXTURE_DATA = "fixtures/bonusShield"; 
  private static final String FIXTURE_NAME = "shieldBonus";
  
  public ShieldBonus() {
    super(FIXTURE_DATA, FIXTURE_NAME);    
  }

  @Override
  public void influencePlane() {
    super.influencePlane();
    Airplane.getInstance().setShield(true);
  }

  

}
