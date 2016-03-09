package com.copter.game.state;

/**
 * Handles game states
 * @author Roman Pecek
 *
 */
public class StateManager {
  private static StateManager instance = null; 
  
  private StateManager() {
      
  }
  
  /**
   * Returns instance of StateManager
   * @return instance of StateManager
   */
  public static StateManager getInstance() {
    if (instance == null) {
      instance = new StateManager();
    }
    return instance;
  }
  
  
}
