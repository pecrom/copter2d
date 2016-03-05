package com.copter.game.assets.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Logger;
import com.copter.game.assets.GameWorldType;
import com.copter.game.assets.WorldAsset;
import com.copter.game.assets.collisions.colliders.ColliderFactory;

public class CollisionsChecker implements ContactFilter, ContactListener{
  private static final String LOGGER_TAG        = "CollisionsChecker";

  private static final Logger LOGGER            = new Logger(LOGGER_TAG, Logger.INFO);
  
  CollisionsObject collidedPair;

  @Override
  public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
    collidedPair = null;
    WorldAsset first = (WorldAsset) fixtureA.getUserData();
    WorldAsset second = (WorldAsset) fixtureB.getUserData();
    
    collidedPair = splitCollided(first, second);
    return collidedPair.isHeroColliding();
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


  @Override
  public void beginContact(Contact contact) {
   if(contact.isTouching()) {
     ColliderFactory.getCollider(collidedPair.getHero(), collidedPair.getAsset()).collide();
   }
  }

  @Override
  public void endContact(Contact contact) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {
    // TODO Auto-generated method stub
    
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
