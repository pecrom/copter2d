package com.copter.game.state;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

public abstract class GameState {
  private States state;
  protected ScreenAdapter screen;
  
  public GameState(States state, ScreenAdapter screen) {
    this.state = state;
    this.screen = screen;
  }
  
  public void stateEnter(Game game, States previous) {
    
  }
  
  public void stateExit() {
    
  }
  
  public abstract boolean allowChangeTo(States state);

  public States getState() {
    return state;
  }

}
