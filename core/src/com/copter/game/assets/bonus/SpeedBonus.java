package com.copter.game.assets.bonus;

import com.copter.game.assets.Airplane;

public class SpeedBonus extends Bonus{
  
  /**
   * Decrease of the speed
   */
  private static final float SPEED_DECREASE = 0.05f;
  
  private static final String FIXTURE_DATA = "fixtures/bonusSpeed"; 
  private static final String FIXTURE_NAME = "speedBonus";
  
  
  
  public SpeedBonus() {
    super(FIXTURE_DATA, FIXTURE_NAME);    
  }

  @Override
  public void influencePlane() {
    super.influencePlane();
    Airplane plane = Airplane.getInstance();
    plane.setHorizontalVelocity(plane.getHorizontalVelocity() - SPEED_DECREASE);
    
  }

  

}
