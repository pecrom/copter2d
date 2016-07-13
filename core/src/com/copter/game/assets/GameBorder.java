package com.copter.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;
import com.copter.utils.Box2dUtils;

public class GameBorder implements Updatable, WorldAsset {
  private static final float DENSITY = 100f;
  private static final float FRICTION = 1f;
  //private static final String TEXTURE_REGION_NAME = 
  private Body body;
  private String fixtureData, fixtureName;
  private World world;
  

  public GameBorder(String fixtureData, String fixtureName) {
    this.fixtureData = fixtureData;
    this.fixtureName = fixtureName;
  }

  public GameBorder(GameBorder border) {
    this.body = border.body;
    this.fixtureData = border.fixtureData;
    this.fixtureName = border.fixtureName;
    this.world = border.world;
    init(world);
        
  }
  
  @Override
  public void update(float delta) {
    throw new UnsupportedOperationException("Unsupported method update in GameBorder");
  }

  @Override
  public GameWorldType getWorldType() {
    return GameWorldType.OBSTACLE;
  }

  @Override
  public TextureRegion getTextureRegion() {
    // TODO return texture
    return null;
  }

  @Override
  public void init(World world) {
    this.world = world;
    
    body = Box2dUtils.createBody(world, BodyType.StaticBody, new Vector2(0, 0));
    
    FixtureDef fixtureDef = Box2dUtils.createFixtureDef(DENSITY, FRICTION, null);  
    
    Box2dUtils.attachFixture(fixtureData, body, fixtureName, fixtureDef, Copter2D.GAME_WIDTH, this);    
    
    //body.createFixture(fixtureDef); //TODO set texture
  } 
  
  
  @Override
  public Body getBody() {
    return body;
  }

  @Override
  public float getWidth() {
    return Copter2D.GAME_WIDTH;
  }
 
 
}
