package com.copter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.copter.game.state.StateManager;
import com.copter.screen.GameScreen;

public class Copter2D extends Game {
  public static final float WIDTH       = 800;
  public static final float HEIGHT      = 480;
  public static final float SCALE       = 100;
  public static final float GAME_WIDTH  = WIDTH / SCALE;
  public static final float GAME_HEIGHT = HEIGHT / SCALE;

  private Camera            cam;
  private Viewport          viewport;
  private StateManager      stateManager;

  @Override
  public void create() {
    cam = new OrthographicCamera(WIDTH, HEIGHT);
    viewport = new FitViewport(WIDTH, HEIGHT, cam);
    stateManager = StateManager.getInstance();
    stateManager.setGame(this);
    stateManager.init();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

}
