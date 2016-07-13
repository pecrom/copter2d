package com.copter.game.assets.bonus;

import com.copter.game.assets.Airplane;

public class FuelBonus extends Bonus{
  /**
   * Fuel increase 
   */
  private static final float FUEL_INCREASE = 30f;
  
  private static final String FIXTURE_DATA = "fixtures/bonusFuel"; 
  private static final String FIXTURE_NAME = "fuelBonus";
  private static final String TEXTURE_REGION_NAME = "bonusFuel";
  
  public FuelBonus() {
    super(FIXTURE_DATA, FIXTURE_NAME, TEXTURE_REGION_NAME);    
  }

  @Override
  public void influencePlane() {
    super.influencePlane();
    Airplane plane = Airplane.getInstance();
    plane.setFuel(plane.getFuel() + FUEL_INCREASE);
  }

  

}
