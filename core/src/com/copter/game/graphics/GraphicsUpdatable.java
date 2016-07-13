package com.copter.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classes which are responsible for updating graphics should implement this interface.
 * @author Roman Pecek
 *
 */
public interface GraphicsUpdatable {
  
  /**
   * It is called every time the screen is being rendered.
   * @param delta time since the screen was previously rendered
   * @param batch used to draw the graphics
   */
  public void updateGraphics(float delta, SpriteBatch batch);

}
