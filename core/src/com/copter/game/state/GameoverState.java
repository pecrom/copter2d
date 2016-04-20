package com.copter.game.state;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.copter.screen.GameoverScreen;

public class GameoverState extends GameState {
  
  public GameoverState() {
    this(States.GAMEOVER, new GameoverScreen());
  }

  private GameoverState(States state, ScreenAdapter screen) {
    super(state, screen);
    // TODO Auto-generated constructor stub
  }

  
  
  @Override
  public void stateEnter(Game game, States previous) {
    game.setScreen(screen);
  }

  @Override
  public boolean allowChangeTo(States state) {
    // TODO Auto-generated method stub
    return false;
  }

}
