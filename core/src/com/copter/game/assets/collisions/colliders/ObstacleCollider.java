package com.copter.game.assets.collisions.colliders;

import com.copter.game.assets.Airplane;
import com.copter.game.assets.GameBorder;
import com.copter.game.assets.WorldAsset;
import com.copter.game.state.StateManager;
import com.copter.game.state.States;

public class ObstacleCollider extends Collider {

  public ObstacleCollider(WorldAsset hero, WorldAsset asset) {
    super(hero, asset);
  }

  @Override
  public void collide() { 
    if (!Airplane.getInstance().hasShield() || asset instanceof GameBorder) {
      StateManager.getInstance().changeStateTo(States.GAMEOVER);
    } 
  }

}
