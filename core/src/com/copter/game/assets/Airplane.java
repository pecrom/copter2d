package com.copter.game.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;



public final class Airplane implements WorldAsset, Updatable {  
  /**
   * The plane is placed 1/8 of the screen width from the left side.
   */
  private static final float DIVISOR_FOR_X_START = 8; 
  
  private static final float DIVISOR_FOR_FINDING_CENTER = 2;
  
  private static final float REMAINING_DISTANCE_TO_UPDATE = 0f;
  
  /**
   * Height of the plane in comparison with the world height.
   */
  private static final float PLANE_HEIGHT_RATE = 0.15f;
  
  /**
   * Width of the plane in comparison with the world width.
   */
  private static final float PLANE_WIDTH_RATE = 0.11f;
  
  private static final float PLANE_DENSITY = 500;
  
  private static final float PLANE_FRICTION = 0;
  
  /**
   * How often to update the linear velocity.
   */
  private static final float UPDATE_VELOCITY_EVERY = 10f; //meters
  
  private static final float HORIZONTAL_VELOCITY_GROWTH = 0.1f;
  
  private float horizontalVelocity = 0.2f;
  
  private float verticalVelocity = 0f;
  
  private static Airplane instance = null;
  private GameWorldType type = null;
  private Body planeBody = null;
  private Vector2 velocity = new Vector2(horizontalVelocity, verticalVelocity);
  private float nextVelocityUpdate = UPDATE_VELOCITY_EVERY;
  private float distance = 0f;
      
  private Airplane() {
    type = GameWorldType.HERO;  
  }  
  
  /**
   * If the instance of the plane does not exist, lets create it.
   * @return instance of Plane.
   */
  public static Airplane getInstance() {
    if (instance == null) {
      instance = new Airplane();
    }
    
    return instance;
  }
  
  @Override
  public GameWorldType getWorldType() {    
    return type;
  }

  @Override
  public Texture getTexture() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void update(float delta) {
    updateHorizontalVelocity(delta);
  }

  private void updateHorizontalVelocity(float delta) {
    float deltaDistance = velocity.x * delta;
    nextVelocityUpdate -= deltaDistance;
    distance += deltaDistance;    
    
    if (nextVelocityUpdate < REMAINING_DISTANCE_TO_UPDATE) {
      //update the speed
      nextVelocityUpdate = UPDATE_VELOCITY_EVERY;
      velocity.x += HORIZONTAL_VELOCITY_GROWTH;
      planeBody.setLinearVelocity(velocity);
    }
  }

  @Override
  public void init(World world) {
    BodyDef planeBodyDef = new BodyDef();
    planeBodyDef.type = BodyType.DynamicBody;
    Vector2 startingPosition = getStartingPosition();    
    planeBodyDef.position.set(startingPosition);
    
    planeBody = world.createBody(planeBodyDef);
        
    PolygonShape planeShape = new PolygonShape();
    Vector2 planeSize = Airplane.getHxHy(); //half of the plane size
    planeShape.setAsBox(planeSize.x, planeSize.y);
    
    FixtureDef planeFixDef = new FixtureDef();
    planeFixDef.shape = planeShape;
    planeFixDef.density = PLANE_DENSITY;
    planeFixDef.friction = PLANE_FRICTION;
    
    //@TODO set user data to fixture
    Fixture planeFix = planeBody.createFixture(planeFixDef);
    planeBody.setLinearVelocity(velocity);
    
    planeBody.setUserData(this);
  }
  
  private static Vector2 getHxHy() {
    float hx = Copter2D.WIDTH / Copter2D.SCALE * PLANE_WIDTH_RATE / DIVISOR_FOR_FINDING_CENTER;
    float hy = Copter2D.HEIGHT / Copter2D.SCALE * PLANE_HEIGHT_RATE / DIVISOR_FOR_FINDING_CENTER;
    
    return new Vector2(hx, hy);
  }
    
  private Vector2 getStartingPosition() {
    float x = planeLeftMargin();
    float y = Copter2D.HEIGHT / Copter2D.SCALE / DIVISOR_FOR_FINDING_CENTER;
    return new Vector2(x, y);
  }

  /**
   * How far from left border the plane is placed.
   * @return left margin of the plane.
   */
  public float planeLeftMargin() {
    return Copter2D.WIDTH / Copter2D.SCALE / DIVISOR_FOR_X_START;
  }
  
  @Override
  public Body getBody() {    
    return planeBody;
  }

  public float getDistance() {
    return distance;
  }
}
