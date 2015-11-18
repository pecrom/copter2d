package com.copter.managers;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.Obstacle;
import com.copter.game.assets.Updatable;

public class ObstacleManager implements Updatable {
  private static final String LOGGER_TAG = "ObstacleManager";
  
  private static final Logger LOGGER = new Logger(LOGGER_TAG, Logger.INFO);
 
  private static ObstacleManager instance = null;
  
  private static final float INCREASE_OBSTACLE_COUNT_EVERY = 10f; //meters 
  
  private static final float MAXIMAL_DISTANCE_BETWEEN_OBSTACLES = Copter2D.WIDTH / Copter2D.SCALE;
  
  private static final int POSITIVE_RESULT_FROM_RAND = 1; //if the resulted number from Math.random() is this, then it means true  
  
  private Queue<Obstacle> availableObstacles;
  private Queue<Obstacle> usedObstacles;
  
  private World gameWorld;
  private Airplane airplane;
  private float lastDistanceObstacle = 0; //last flow plane distance when the obstacle was inserted 
  
  private ObstacleManager(World world, Airplane plane) {
    availableObstacles = new LinkedList<Obstacle>();
    usedObstacles = new LinkedList<Obstacle>();
    gameWorld = world;
    airplane = plane;    
    
  }
  
  /**
   * Returns the instance of the ObstacleManager.
   * @param world game world
   * @param plane hero plane
   * @return instance of ObstacleManager
   */
  public static ObstacleManager getInstance(World world, Airplane plane) {
    if (instance == null) {
      instance = new ObstacleManager(world, plane);
    }
    return instance;
  }
  
  
  private void createObstacle() {
    Obstacle obstacle = new Obstacle();
    obstacle.init(gameWorld);
    availableObstacles.add(obstacle);
  }
  
  
  private boolean increaseObstacleCount() {
    boolean increase = false;
    int numberOfObstacles = (int) ((int) airplane.getDistance() / INCREASE_OBSTACLE_COUNT_EVERY);
    
    if (numberOfObstacles > (availableObstacles.size() + usedObstacles.size())) {
      increase = true;
      LOGGER.info("obstacles count: "+numberOfObstacles);
    }
    
    return increase;
  }
  
  @Override
  public void update(float delta) {
    recycleUsedObstacles();
    
    if ( increaseObstacleCount() ) {
      LOGGER.info("new obstacle");
      createObstacle();
    }
    
    if ( insertObstacle() ) {
      prepareObstacle();
    }
        
  }
  
  private boolean insertObstacle() {
    boolean insert = false;
   
    if (!availableObstacles.isEmpty()) { //we still have some spare obstacles which can be inserted 
      if ((lastDistanceObstacle - airplane.getBody().getPosition().x) > 0) { //there is possibility that the obstacle wont be inserted
        insert = ObstacleManager.getRandomTrue();
        
      } else {    // the obstacle is inserted for sure
        insert = true;
      }
    }
      
    return insert;
  }
  
  private static boolean getRandomTrue() {
    return Math.round(Math.random()) == POSITIVE_RESULT_FROM_RAND ? true : false;
  }
  
  private void prepareObstacle() { //@TODO add parameter to set the orientation of the obstacle
    LOGGER.info("Obstacle at: " + airplane.getDistance());
    Obstacle popedObstacle = availableObstacles.poll();
    Vector2 obstaclePosition = new Vector2(airplane.getDistance() + MAXIMAL_DISTANCE_BETWEEN_OBSTACLES , 0);
    popedObstacle.getBody().setTransform(obstaclePosition, 0);
    lastDistanceObstacle = obstaclePosition.x;
    
    try {
      usedObstacles.add(popedObstacle);
    } catch (IllegalStateException stateEx) {
      LOGGER.info("Obstacle can not be added to the used queue", stateEx);
    }
    
    
    LOGGER.info("new obstacle will be shown");
  }
  
  private void recycleUsedObstacles() {
    float nonVisibleXposition = airplane.getBody().getPosition().x - airplane.planeLeftMargin();
    while (usedObstacles.peek() != null && usedObstacles.peek().getBody().getPosition().x < nonVisibleXposition) {
      availableObstacles.add(usedObstacles.poll());
      LOGGER.info("Obstacle was recycled");
    }
  }
  
}
