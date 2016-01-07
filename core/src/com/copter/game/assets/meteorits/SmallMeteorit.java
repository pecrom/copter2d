package com.copter.game.assets.meteorits;

import com.copter.Copter2D;

public class SmallMeteorit extends Meteorit {

  private static final String SMALL_FIXTURE_DATA = "fixtures/meteoritSmall";
  private static final String SMALL_FIXTURE_NAME = "meteoritSmall";
  
  private static final float SPEED_OF_ROTATION = 2.4f;
  private static final float SMALL_SPEED = -2.7f;
  
  public SmallMeteorit() {
    super(SMALL_FIXTURE_DATA, SMALL_FIXTURE_NAME, SMALL_SPEED);
    width = 18f / Copter2D.SCALE; //width of the image
    height = 18f / Copter2D.SCALE; //height of the image
    rotationSpeed = SPEED_OF_ROTATION;
  }

}
