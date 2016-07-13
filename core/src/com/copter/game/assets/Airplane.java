package com.copter.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;
import com.copter.utils.Box2dUtils;



public final class Airplane implements WorldAsset, Updatable {   
  private static final String LOGGER_TAG = Airplane.class.getName();
  
  private static final Logger LOGGER = new Logger(LOGGER_TAG, Logger.INFO);
  
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
  
  private static final float PLANE_DENSITY = 0.5f;
  
  private static final float PLANE_FRICTION = 0;
  
  public static final float PLANE_LEFT_MARGIN = Copter2D.WIDTH / Copter2D.SCALE / DIVISOR_FOR_X_START;
  
  /**
   * How often to update the linear velocity.
   */
  private static final float UPDATE_VELOCITY_EVERY = 10f; //meters
  
  private static final float HORIZONTAL_VELOCITY_GROWTH = 0.0f;//0.1f
  
  private static final float INIT_HORIZONTAL_VELOCITY = 0.0f; //0.2f
  
  /**
   * Max % of fuel
   */
  private static final float MAX_FUEL = 10000f;
  
  /**
   * Fuel consumption per meter
   */
  private static final float FUEL_CONSUMPTION_PER_METER = 15f;
  
  /**
   * For how long the shield should be active
   */
  private static final float MAX_DURATION_OF_SHIELD = 5f;
  
  private float horizontalVelocity = INIT_HORIZONTAL_VELOCITY;
  
  private float verticalVelocity = 0f;
  
  /**
   * % of fuel
   */
  private float fuel = 100f;
  
  private boolean shielded = false;
  private float shieldDuration = 0f;
  
  
  private static Airplane instance = null;
  private GameWorldType type = null;
  private Body planeBody = null;
  private Vector2 velocity = new Vector2(horizontalVelocity, verticalVelocity);
  private float nextVelocityUpdate = UPDATE_VELOCITY_EVERY;
  private float distance = PLANE_LEFT_MARGIN;
      
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
  public TextureRegion getTextureRegion() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void update(float delta) {
    updateHorizontalVelocity(delta);
    updateShield(delta);    
  }

  private void updateShield(float delta) {
    if (hasShield()) {
      shieldDuration -= delta;
      if (shieldDuration < 0) { //shield is not active anymore 
        setShield(false);
        LOGGER.info("Shield was turned off");
      }
    }
    
  }
  
  private void updateHorizontalVelocity(float delta) {
    float deltaDistance = velocity.x * delta;
    nextVelocityUpdate -= deltaDistance;
    distance += deltaDistance;    
    fuel -= deltaDistance * FUEL_CONSUMPTION_PER_METER; //@TODO create method for updating fuel state
    
    if (nextVelocityUpdate < REMAINING_DISTANCE_TO_UPDATE) {
      //update the speed
      nextVelocityUpdate = UPDATE_VELOCITY_EVERY;
      velocity.x += HORIZONTAL_VELOCITY_GROWTH;
      planeBody.setLinearVelocity(velocity);
    }
  }

  @Override
  public void init(World world) {
    planeBody = Box2dUtils.createBody(world, BodyType.DynamicBody, getStartingPosition());
        
    PolygonShape planeShape = new PolygonShape();
    Vector2 planeSize = Airplane.getHxHy(); //half of the plane size
    planeShape.setAsBox(planeSize.x, planeSize.y);
    
    FixtureDef planeFixDef = Box2dUtils.createFixtureDef(PLANE_DENSITY, PLANE_FRICTION, planeShape);
    
    
    Fixture planeFix = planeBody.createFixture(planeFixDef);
    planeFix.setUserData(this);
    
    planeBody.setLinearVelocity(velocity);
    
  }
  
  private static Vector2 getHxHy() {
    float hx = Copter2D.WIDTH / Copter2D.SCALE * PLANE_WIDTH_RATE / DIVISOR_FOR_FINDING_CENTER;
    float hy = Copter2D.HEIGHT / Copter2D.SCALE * PLANE_HEIGHT_RATE / DIVISOR_FOR_FINDING_CENTER;
    
    return new Vector2(hx, hy);
  }
    
  private static Vector2 getStartingPosition() {
    float x = PLANE_LEFT_MARGIN;
    float y = Copter2D.HEIGHT / Copter2D.SCALE / DIVISOR_FOR_FINDING_CENTER;
    return new Vector2(x, y);
  }

  
  
  @Override
  public Body getBody() {    
    return planeBody;
  }

  public float getDistance() {
    return distance;
  }

  @Override
  public float getWidth() {
    return Copter2D.WIDTH / Copter2D.SCALE * PLANE_WIDTH_RATE;
  }

  /**
   * Returns % of fuel
   * @return float % of fuel
   */
  public float getFuel() {
    return fuel;
  }

  /**
   * Sets fuel, if the resulted value would be more than MAX_FUEL, then MAX_FUEL is set instead
   * @param fuel
   */
  public void setFuel(float newFuel) {
    fuel = newFuel;
    if ( fuel > MAX_FUEL ) {
      fuel = MAX_FUEL;
    }
  }
  
  
  /**
   * Sets new horizontal velocity, if the new velocity is lower than the initial one, then the initial one is used.
   * @param newHorizontalVelocity
   */
  public void setHorizontalVelocity(float newHorizontalVelocity) {
    if (newHorizontalVelocity < INIT_HORIZONTAL_VELOCITY) {
      horizontalVelocity = newHorizontalVelocity;
    }
    
  }
  
  /**
   * Returns horizontal velocity.
   * @return float current horizontal velocity
   */
  public float getHorizontalVelocity() {
    return horizontalVelocity;
  }
  
  /**
   * Returns if the shield is active.
   * @return boolean status of the shield
   */
  public boolean hasShield() {
    return shielded;
  }
  
  /**
   * Activate or deactivate shield.
   * @param shield boolean status of the shield
   */
  public void setShield(boolean shield) {
    if (shield) {
      shieldDuration = MAX_DURATION_OF_SHIELD;
    }
    shielded = shield;
  }
}
