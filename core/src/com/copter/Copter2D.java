package com.copter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.copter.screen.GameScreen;

public class Copter2D extends Game {
  public static final float WIDTH  = 800;
  public static final float HEIGHT = 480;
  public static final float SCALE  = 100;

  private Camera            cam;
  private Viewport          viewport;

  @Override
  public void create() {
    cam = new OrthographicCamera(WIDTH, HEIGHT);
    viewport = new FitViewport(WIDTH, HEIGHT, cam);
    setScreen(new GameScreen());
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

}
