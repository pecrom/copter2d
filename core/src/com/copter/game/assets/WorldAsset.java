package com.copter.game.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public interface WorldAsset {

  /**
   * Returns the type of the game world asset - Hero, obstacle or bonus.
   * 
   * @return type of the game object.
   */
  GameWorldType getWorldType();

  /**
   * Returns graphical representation of the world asset.
   * 
   * @return graphical representation.
   */
  Texture getTexture(); 
  
  /**
   * Create the asset based on the worlds data.
   * @param world used to create body.
   */
  void init(World world);
  
  /**
   * Gets world body.
   * @return Body element
   */
  Body getBody();
  
  /**
   * Gets assets width.
   * @return float width
   */
  float getWidth();
}