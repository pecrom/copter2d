package com.copter.game.state;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;

/**
 * Handles game states.
 * @author Roman Pecek
 *
 */
public class StateManager {
  private static StateManager instance = null; 
  private GameState actualState = null;
  private Map<States, GameState> allStates;
  private Game game;
  
  private StateManager() {
    allStates = new HashMap<States, GameState>();       
    initStates();
  }
  
  private void initStates() {
    allStates.put(States.PLAYING, new PlayingState());
  }
  
  /**
   * Returns instance of StateManager.
   * @return instance of StateManager
   */
  public static StateManager getInstance() {
    if (instance == null) {
      instance = new StateManager();
    }
    return instance;
  }
  
  /**
   * Pass in Game class so that states will be able to change screen.
   * @param game 
   */
  public void setGame(Game game) {
    this.game = game;
  }
  
  /**
   * Setup first state.
   */
  public void init() {
    actualState = allStates.get(States.PLAYING); 
    actualState.stateEnter(game, null);
  }
    
  /**
   * Changes to a new state. Before the change is made, 
   * stateExit is called on an old state then stateEnter is called on the new state.
   * @param newState name of the new state
   */
  public void changeStateTo(States newState) {
    if (actualState.allowChangeTo(newState)) {  
      
      States oldState = actualState.getState();
      actualState.stateExit();
      actualState = allStates.get(newState);
      actualState.stateEnter(game, oldState);
    }
  }
  
}
