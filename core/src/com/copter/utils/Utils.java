package com.copter.utils;

public final class Utils {

  private static final int POSITIVE_RESULT_FROM_RAND = 1; //if the resulted number from Math.random() is this, then it means true  
  
  private Utils() {
    
  }
  
  public static boolean getRandomTrue() {
    return Math.round(Math.random()) == POSITIVE_RESULT_FROM_RAND ? true : false;
  }
  
}
