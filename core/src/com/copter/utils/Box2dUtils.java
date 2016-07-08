package com.copter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2dUtils {

  /**
   * Returns body created according to given parameters.
   * @param world game world
   * @param type type of the body - static, dynamic, kinematic
   * @param position position at which the body is created
   * @return
   */
  public static Body createBody(World world, BodyType type, Vector2 position) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = type;
    bodyDef.position.set(position);
    
    return world.createBody(bodyDef);    
  }

  /**
   * Returns body created according to given parameters.
   * @param world game world
   * @param type type of the body - static, dynamic, kinematic
   * @param xPosition x coordinate of the body
   * @param yPosition y coordinate of the body
   * @return
   */
  public static Body createBody(World world, BodyType type, float xPosition, float yPosition) {    
    return createBody(world, type, new Vector2(xPosition, yPosition));    
  }

  /**
   * Creates fixture definition according to provided parameters.
   * @param density density of the fixture
   * @param friction friction of the fixture
   * @param shape shape of the fixture
   * @return
   */
  public static FixtureDef createFixtureDef(float density, float friction, Shape shape) {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = density;
    fixtureDef.friction = friction;
    fixtureDef.shape = shape;
    
    return fixtureDef;    
  }
  
  /**
   * Creates fixture definition according to provided parameters.
   * @param density density of the fixture
   * @param friction friction of the fixture
   * @param isSensor sets if the fixture is sensor
   * @return
   */
  public static FixtureDef createFixtureDef(float density, float friction, boolean isSensor) {
    FixtureDef fixtureDef = createFixtureDef(density, friction, null);
    fixtureDef.isSensor = isSensor;
    return  fixtureDef;
  }
  
  /**
   * Attaches fixture to body.
   * @param fixtureData data which represents fixture itself
   * @param body body of the fixture
   * @param fixtureName name of the fixture, must be in fixtureData
   * @param fixtureDef definition of the fixture
   * @param width width of the fixture
   * @param userData user data
   */
  public static void attachFixture(String fixtureData, Body body, String fixtureName, FixtureDef fixtureDef, float width, Object userData){
    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(fixtureData));
    loader.attachFixture(body, fixtureName, fixtureDef, width, userData);
  }

  
}
