package com.copter.game.assets.meteorits;

import com.badlogic.gdx.graphics.Texture;
import com.copter.Copter2D;

public class MediumMeteorit extends Meteorit {

  private static final String MEDIUM_FIXTURE_DATA = "fixtures/meteoritMed";
  private static final String MEDIUM_FIXTURE_NAME = "meteoritMed";
  private static final String TEXTURE_NAME = "meteorBrownMed";
  
  private static final float SPEED_OF_ROTATION = 2.1f;  //2.1
  private static final float MEDIUM_SPEED = -2f; //-2
  
  public MediumMeteorit() {
    super(MEDIUM_FIXTURE_DATA, MEDIUM_FIXTURE_NAME, TEXTURE_NAME, MEDIUM_SPEED);
    width = 29f / Copter2D.SCALE; //width of the image
    height = 26f / Copter2D.SCALE; //height of the image
    rotationSpeed = SPEED_OF_ROTATION;
  }


}
