package com.copter.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;
import com.copter.utils.BodyEditorLoader;

public class GameBorder implements Updatable, WorldAsset, Cloneable {
  private static final float DENSITY = 100f;
  private static final float FRICTION = 1f;
  private Body body;
  private String fixtureData, fixtureName;
  private World world;
  

  public GameBorder(String fixtureData, String fixtureName) {
    this.fixtureData = fixtureData;
    this.fixtureName = fixtureName;
  }

  @Override
  public void update(float delta) {
   
  }

  @Override
  public GameWorldType getWorldType() {
    return GameWorldType.OBSTACLE;
  }

  @Override
  public Texture getTexture() {
    // TODO return texture
    return null;
  }

  @Override
  public void init(World world) {
    this.world = world;
    
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.StaticBody;
    bodyDef.position.set(new Vector2(0, 0));
    
    body = world.createBody(bodyDef);
    
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = DENSITY;
    fixtureDef.friction = FRICTION;   
    
    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(fixtureData));
    loader.attachFixture(body, fixtureName, fixtureDef, Copter2D.GAME_WIDTH);
    
    //body.createFixture(fixtureDef); //TODO set texture
  } 
  
  
  @Override
  public Body getBody() {
    return body;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    GameBorder border = (GameBorder) super.clone();
    border.init(world);
    return border;
  }

  
  
 
}
