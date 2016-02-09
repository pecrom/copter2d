package com.copter.utils;

import com.copter.game.assets.Airplane;
import com.copter.game.assets.WorldAsset;

public final class Utils {
  
  private static final float VIEWABLE_AREA = 0; 

  private static final int POSITIVE_RESULT_FROM_RAND = 1; //if the resulted number from Math.random() is this, then it means true  
  
  private static final int MAKE_INTEGER = 10;
  
  private Utils() {
    
  }
  
  /**
   * Randomly returns true or false
   * @return true / false
   */
  public static boolean getRandomTrue() {
    return Math.round(Math.random()) == POSITIVE_RESULT_FROM_RAND ? true : false;
  }
  
  /**
   * Check if asset is still in viewable area
   * @param plane 
   * @param asset
   * @return true / false
   */
  public static boolean isInViewableArea(Airplane plane, WorldAsset asset) {
    float planeX = plane.getBody().getPosition().x - Airplane.PLANE_LEFT_MARGIN;
    float assetX = asset.getBody().getPosition().x + asset.getWidth();
    return (assetX - planeX) < VIEWABLE_AREA ? false : true; 
  }
  
  /**
   * Returns random number from <0-numberOfValues>
   * @param numberOfValues 
   * @return int
   */
  public static int getRandomValue(int numberOfValues) {
    int randomNum = (int) Math.round(Math.random() * MAKE_INTEGER);
     return (int)(randomNum * (numberOfValues / (float)MAKE_INTEGER));
  }
  
}
