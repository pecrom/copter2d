package com.copter.managers;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.Obstacle;
import com.copter.game.assets.Obstacle.Orientation;
import com.copter.game.assets.Updatable;
import com.copter.utils.Utils;

public class ObstacleManager implements Updatable {
  private static final String LOGGER_TAG = "ObstacleManager";
  
  private static final Logger LOGGER = new Logger(LOGGER_TAG, Logger.INFO);
 
  private static ObstacleManager instance = null;
  
  private static final float INCREASE_OBSTACLE_COUNT_EVERY = 20f; //meters 
  
  private static final float MAXIMAL_DISTANCE_BETWEEN_OBSTACLES = Copter2D.WIDTH / Copter2D.SCALE;
  
  private static final float MINIMAL_DISTANCE_BETWEEN_OBSTACLES = Obstacle.OBSTACLE_WIDTH;
  
  private static final int MAXIMAL_OBSTACLES_COUNT = 5;
  
  
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
    
    if (numberOfObstacles > (availableObstacles.size() + usedObstacles.size()) && numberOfObstacles < MAXIMAL_OBSTACLES_COUNT) {
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
      prepareObstacle(Utils.getRandomTrue());
    }
        
  }
  
  private boolean insertObstacle() {
    boolean insert = false;
   
    if (!availableObstacles.isEmpty() && isEnoughDistance()) { //we still have some spare obstacles which can be inserted
      LOGGER.info("lastDistanceObstacle: " + lastDistanceObstacle + ", airplane position: " + airplane.getBody().getPosition().x);
      if ((lastDistanceObstacle - airplane.getDistance()) > 0) { //there is possibility that the obstacle wont be inserted
        insert = Utils.getRandomTrue();
        LOGGER.info("insert: " + insert);
      } else {    // the obstacle is inserted for sure
        insert = true;
      }
    }
      
    return insert;
  }
  
  private boolean isEnoughDistance() {
    boolean isEnoughDistance = false;
    int numberOfObstacles = availableObstacles.size() + usedObstacles.size();
    float averageDistance = (Copter2D.WIDTH / Copter2D.SCALE) / numberOfObstacles;
    float nextInsertedDistance = (airplane.getDistance() + MAXIMAL_DISTANCE_BETWEEN_OBSTACLES) - lastDistanceObstacle; //distance of the next inserted from the previously inserted
    if (nextInsertedDistance > averageDistance) {
      isEnoughDistance = true;
    }
    
    return isEnoughDistance;
  }
  
  private void prepareObstacle(boolean topDown) {
    LOGGER.info("Obstacle at: " + airplane.getDistance());
    
    Obstacle popedObstacle = availableObstacles.poll();
    Orientation obstacleOrientation = topDown ? Orientation.DOWN : Orientation.UP;
    LOGGER.info("Obstacle orientation: " + obstacleOrientation);
    popedObstacle.setObstacleOrientation(obstacleOrientation);
    
    float horizontalPosition = airplane.getDistance() + MAXIMAL_DISTANCE_BETWEEN_OBSTACLES;
    popedObstacle.setHorizontalPosition(horizontalPosition);
    lastDistanceObstacle = horizontalPosition;
    
    
    try {
      usedObstacles.add(popedObstacle);
    } catch (IllegalStateException stateEx) {
      LOGGER.info("Obstacle can not be added to the used queue", stateEx);
    }
    
    
    LOGGER.info("new obstacle will be shown");
  }
  
  private void recycleUsedObstacles() {
    float nonVisibleXposition = airplane.getBody().getPosition().x - airplane.planeLeftMargin() - Obstacle.OBSTACLE_WIDTH;
    while (usedObstacles.peek() != null && usedObstacles.peek().getBody().getPosition().x < nonVisibleXposition) {
      availableObstacles.add(usedObstacles.poll());
      LOGGER.info("Obstacle was recycled");
    }
  }
  
}
