package com.copter.game.assets.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.game.assets.GameWorldType;
import com.copter.game.assets.WorldAsset;
import com.copter.utils.Box2dUtils;
import com.copter.utils.TextureProvider;

public abstract class Bonus implements WorldAsset{
  private static final float DENSITY = 100;
  private static final float FRICTION = 1;
  private static final float INIT_X  = -10;
  private static final float INIT_Y = -10;
  private static final float SCALE = 0.3f;
  private static final float NO_ROTATION = 0;
  private static final float HORIZONTAL_VELOCITY = -1f;
  private static final float VERTICAL_VELOCITY = 0f;
  
  protected World world;
  protected Body body;
  protected String fixtureData;
  protected String fixtureName;
  protected boolean consumed;
  
  private String textureRegionName;
  
  protected Bonus(String fixtureData, String fixtureName, String textureRegionName) {
    this.fixtureData = fixtureData;
    this.fixtureName = fixtureName;
    this.textureRegionName = textureRegionName;
    consumed = false;
  }
  
  
  @Override
  public GameWorldType getWorldType() {
    return GameWorldType.BONUS; 
  }

  @Override
  public TextureRegion getTextureRegion() {
    return TextureProvider.getInstance().getTextureRegion(textureRegionName);
  }

  @Override
  public void init(World world) {
    this.world = world;
    
    body = Box2dUtils.createBody(world, BodyType.KinematicBody, new Vector2(INIT_X, INIT_Y));
    body.setLinearVelocity(HORIZONTAL_VELOCITY, VERTICAL_VELOCITY);
    
    FixtureDef bonusFixtureDef = Box2dUtils.createFixtureDef(DENSITY, FRICTION, true);
        
    Box2dUtils.attachFixture(fixtureData, body, fixtureName, bonusFixtureDef, SCALE, this);    
  }

  @Override
  public Body getBody() {
    return body;
  }

  public void setPosition(float x, float y) {
    body.setTransform(new Vector2(x, y), NO_ROTATION);
  }
  
  public void influencePlane() {
    consumed = true;
  }

  public void setConsumed(boolean consumed) {
    this.consumed = consumed;
  }  
    
  public boolean isConsumed() {
    return consumed;
  }
  
  @Override
  public float getWidth() {
    return SCALE;
  }
  
  
}
