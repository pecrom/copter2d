package com.copter.game.assets;

public interface Updatable {
  /**
   * Updates the state of the implementor of this interface.
   * @param delta time since the last update was called;
   */
  void update(float delta);

}
