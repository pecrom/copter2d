package com.copter.game.assets.collisions.colliders;

import com.copter.game.assets.WorldAsset;
import com.copter.game.state.StateManager;
import com.copter.game.state.States;

public class ObstacleCollider extends Collider {

  public ObstacleCollider(WorldAsset hero, WorldAsset asset) {
    super(hero, asset);
  }

  @Override
  public void collide() {    
    StateManager.getInstance().changeStateTo(States.GAMEOVER);
  }

}
