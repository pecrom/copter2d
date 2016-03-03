package com.copter.game.assets.collisions;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Logger;
import com.copter.game.assets.GameWorldType;
import com.copter.game.assets.WorldAsset;

public class CollisionsChecker implements ContactFilter{
  private static final String LOGGER_TAG        = "CollisionsChecker";

  private static final Logger LOGGER            = new Logger(LOGGER_TAG, Logger.INFO);

  @Override
  public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
    WorldAsset first = (WorldAsset) fixtureA.getUserData();
    WorldAsset second = (WorldAsset) fixtureA.getUserData();
    
    CollisionsObject collided = splitCollided(first, second);    
    LOGGER.error("colliding: " + collided.isHeroColliding());
    return collided.isHeroColliding();
  }
  
  private CollisionsObject splitCollided(WorldAsset first, WorldAsset second) {
    CollisionsObject collided = new CollisionsObject();
    
    if (first.getWorldType() == GameWorldType.HERO) {
      collided.setHero(first);
      collided.setAsset(second);
    } else if (second.getWorldType() == GameWorldType.HERO) {      
      collided.setHero(second);
      collided.setAsset(first);
    }
    
    return collided;
  }

  /**
   * Helper class which holds hero and another world asset.
   * 
   * @author Roman Pecek
   *
   */
  private class CollisionsObject {
    private WorldAsset hero;
    private WorldAsset asset;
    
    public WorldAsset getHero() {
      return hero;
    }
    
    public void setHero(WorldAsset hero) {
      this.hero = hero;
    }
    
    public WorldAsset getAsset() {
      return asset;
    }
    
    public void setAsset(WorldAsset asset) {
      this.asset = asset;
    }
    
    public boolean isHeroColliding() {
      return hero != null;
    }
    
  }
  
}
