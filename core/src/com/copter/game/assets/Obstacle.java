package com.copter.game.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.copter.Copter2D;


public class Obstacle implements WorldAsset {
private static final String LOGGER_TAG = "Obstacle";
  
  private static final Logger LOGGER = new Logger(LOGGER_TAG, Logger.INFO);
  
  private static final float OBSTACLE_DENSITY = 1000f;
  private static final float OBSTACLE_FRICTION = 0.8f;
  
  /**
   * Height of the obstacle in comparison with the world height.
   */
  private static final float OBSTACLE_HEIGHT_RATE = 0.5f;
  
  /**
   * Width of the obstacle in comparison with the world height.
   */
  private static final float OBSTACLE_WIDTH_RATE = 0.125f;
  
  private static final float INITIAL_POSITION_X = -50f;
  private static final float INITIAL_POSITION_Y = 0f;
  
  private static final float ROTATE_ANGLE = 180;
  
  public static final float OBSTACLE_WIDTH = Copter2D.WIDTH / Copter2D.SCALE * OBSTACLE_WIDTH_RATE;
  public static final float OBSTACLE_HEIGHT = Copter2D.HEIGHT / Copter2D.SCALE * OBSTACLE_HEIGHT_RATE;
  
  
  private Body bodyObstacle;
  private Orientation obstacleOrientation;
  
  public Obstacle() {
    obstacleOrientation = Orientation.UP;
  }
  
  @Override
  public GameWorldType getWorldType() {    
    return GameWorldType.OBSTACLE;
  }

  @Override
  public Texture getTexture() {
    throw new UnsupportedOperationException();   
  }

  @Override
  public void init(World world) {
    BodyDef bodyDefObstacle = new BodyDef();
    bodyDefObstacle.type = BodyType.KinematicBody;
    bodyDefObstacle.position.set(INITIAL_POSITION_X, INITIAL_POSITION_Y);
    
    bodyObstacle = world.createBody(bodyDefObstacle);
    
    Shape shapeObstacle = Obstacle.createShape();    
   
    FixtureDef fixtureDefObstacle = new FixtureDef();
    fixtureDefObstacle.density = OBSTACLE_DENSITY;
    fixtureDefObstacle.friction = OBSTACLE_FRICTION;
    fixtureDefObstacle.shape = shapeObstacle;
    
    bodyObstacle.createFixture(fixtureDefObstacle);
    bodyObstacle.setUserData(this);

  }

  private static Shape createShape() {
    PolygonShape triangle = new PolygonShape();
    Vector2[] vertices = new Vector2[] {
        new Vector2(0, 0), //first vertice
        new Vector2(Obstacle.OBSTACLE_WIDTH, 0), //second vertice
        new Vector2(Obstacle.OBSTACLE_WIDTH / 2, OBSTACLE_HEIGHT) //third vertice
    };
    triangle.set(vertices);
    
    return triangle;
  }
  
  @Override
  public Body getBody() {
    return bodyObstacle;
  }
   
  
  public Orientation getObstacleOrientation() {
    return obstacleOrientation;
  }

  /**
   * Sets the orientation of the obstacle, whether it is from bottom up or from up to down. 
   * @param newObstacleOrientation DOWN or UP
   */
  public void setObstacleOrientation(Orientation newObstacleOrientation) { 
    
    if (obstacleOrientation != newObstacleOrientation) {
      //parameters for orientation == UP
      float yCoordinate = 0;
      float angle = 0;
      
      if (newObstacleOrientation == Orientation.DOWN) {
        yCoordinate = Copter2D.HEIGHT / Copter2D.SCALE;
        LOGGER.info("yCoordinate: " + yCoordinate);
        angle = ROTATE_ANGLE;
      }
      
      LOGGER.info("rotating: " + bodyObstacle.getAngle());
      
      bodyObstacle.setTransform(new Vector2(bodyObstacle.getPosition().x, yCoordinate), (float) Math.toRadians(angle));
      this.obstacleOrientation = newObstacleOrientation;
      LOGGER.info("rotating: " + bodyObstacle.getAngle());
    }    
  }

  /**
   * Sets the horizontal position of the obstacle.
   * @param newX float new x position.
   */
  public void setHorizontalPosition(float newX) { 
    Vector2 newPositon = bodyObstacle.getPosition();
    newPositon.x = obstacleOrientation == Orientation.DOWN ? newX + (Obstacle.OBSTACLE_WIDTH)  : newX;
    bodyObstacle.setTransform(newPositon, bodyObstacle.getAngle());
  }

  /**
   */
  public enum Orientation {
    UP,
    DOWN;
    
    @Override
    public String toString() {
      String orientationText;
      
      if (this == DOWN) {
        orientationText = "down";
      } else {
        orientationText = "up";
      }
    
      return orientationText;
      
    }
  }

  @Override
  public float getWidth() {
    return OBSTACLE_WIDTH;
  }
}
