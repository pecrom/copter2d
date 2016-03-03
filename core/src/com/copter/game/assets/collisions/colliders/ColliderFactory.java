package com.copter.game.assets.collisions.colliders;

import com.badlogic.gdx.utils.Logger;
import com.copter.game.assets.WorldAsset;

public class ColliderFactory {
  private static final String LOGGER_TAG = "ColliderFactory";

  private static final Logger LOGGER     = new Logger(LOGGER_TAG, Logger.INFO);

  private ColliderFactory() {

  };

  /**
   * Returns appropriate collider based on the asset.
   * 
   * @param hero game hero.
   * @param asset asset which collides with hero.
   * @return appropriate collider.
   */
  public static Collider getCollider(WorldAsset hero, WorldAsset asset) {
    Collider selectedCollider = null;

    switch (asset.getWorldType()) {
    case BONUS:
      selectedCollider = new BonusCollider(hero, asset);
      break;
    case OBSTACLE:
      selectedCollider = new ObstacleCollider(hero, asset);
      break;
    case HERO:
      LOGGER.error("Asset can not be hero, hero can not collide with itself!!!!");
      break;
    default:
      LOGGER.error("Unknown asset type!!!!");
    }

    return selectedCollider;
  }
}
