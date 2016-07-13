package com.copter.game.assets.meteorits;

import com.copter.Copter2D;

public class SmallMeteorit extends Meteorit {

  private static final String SMALL_FIXTURE_DATA = "fixtures/meteoritSmall";
  private static final String SMALL_FIXTURE_NAME = "meteoritSmall";
  private static final String TEXTURE_NAME = "meteorBrownSmall";
  
  private static final float SPEED_OF_ROTATION = 2.4f; //2.4
  private static final float SMALL_SPEED = -2.7f; //-2.7
  
  public SmallMeteorit() {
    super(SMALL_FIXTURE_DATA, SMALL_FIXTURE_NAME, TEXTURE_NAME, SMALL_SPEED);
    width = 18f / Copter2D.SCALE; //width of the image
    height = 18f / Copter2D.SCALE; //height of the image
    rotationSpeed = SPEED_OF_ROTATION;
  }


}
