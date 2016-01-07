package com.copter.game.assets.meteorits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.game.assets.GameWorldType;
import com.copter.game.assets.Updatable;
import com.copter.game.assets.WorldAsset;
import com.copter.utils.BodyEditorLoader;

public abstract class Meteorit implements WorldAsset, Updatable{
  private static final float DENSITY = 100f;
  private static final float FRICTION = 1f;
  private static final float VERTICAL_VELOCITY = 0f;
  
  protected static final float X_START = -2f;
  protected static final float Y_START = -2f;
  
  
  protected String fixtureData;
  protected String fixtureName;
  protected float speed;
  protected float width;
  protected float height;
  protected float rotation = 0;
  protected float rotationSpeed = 1;
  
  protected World world;
  protected Body body;
  
  /**
   * Instatiate new meteorit based on provided fixture and its name.
   */
  public Meteorit(String fixtureData, String fixtureName, float speed) {
    this.fixtureData = fixtureData;
    this.fixtureName = fixtureName;
    this.speed = speed;    
  }
  
  @Override
  public GameWorldType getWorldType() {
    return GameWorldType.OBSTACLE;
  }

  @Override
  public Texture getTexture() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void init(World world) {
    this.world = world;
    
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.KinematicBody;
    bodyDef.position.set(new Vector2(0, 0));
    
    body = world.createBody(bodyDef);
    
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = DENSITY;
    fixtureDef.friction = FRICTION;   
    fixtureDef.isSensor = true;
    
    body.setLinearVelocity(new Vector2(speed, VERTICAL_VELOCITY));
    
    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(fixtureData));
    loader.attachFixture(body, fixtureName, fixtureDef, width);    
    
  //body.createFixture(fixtureDef); //TODO set texture
  }

  @Override
  public Body getBody() {    
    return body;
  }
  
  public void prepareForShow(Vector2 position) {
    rotation = 0;
    body.setTransform(position, rotation);
    
  }
  
  public float getWidth() {
    return width;
  }
  
  public float getHeight() {
    return height;
  }

  @Override
  public void update(float delta) {
    rotation += delta * rotationSpeed;
    body.setTransform(body.getPosition(), rotation);
  }
  
  
  
}