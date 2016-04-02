package com.copter.game.state;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.copter.screen.GameScreen;

public class PlayingState extends GameState {

  public PlayingState() {
    this(States.PLAYING, new GameScreen());
  }

  private PlayingState(States state, ScreenAdapter screen) {
    super(state, screen);
  }

  @Override
  public void stateEnter(Game game, States previous) {
    if (previous != States.PAUSE) {
      game.setScreen(screen);
    }
  }


  @Override
  public boolean allowChangeTo(States state) {

    // only allow to switch to PAUSE or GAMEOVER state
    switch (state) {
    case PAUSE:
    case GAMEOVER:
      return true;
    default:
      return false;
    }
  }

}
