package com.copter.game.assets.meteorits;

import com.copter.Copter2D;

public class BigMeteorit extends Meteorit {

  private static final String BIG_FIXTURE_DATA = "fixtures/meteoritBig";
  private static final String BIG_FIXTURE_NAME = "meteoritBig";
  
  private static final float SPEED_OF_ROTATION = 1.8f;
  private static final float BIG_SPEED = -1.3f;
  
  
  /**
   * Constructs BigMeteorit.
   */
  public BigMeteorit() {
    super(BIG_FIXTURE_DATA, BIG_FIXTURE_NAME, BIG_SPEED);
    width = 43f / Copter2D.SCALE; //width of the image
    height = 43f / Copter2D.SCALE; //height of the image
    rotationSpeed = SPEED_OF_ROTATION;
  }

}
