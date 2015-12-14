package com.copter.managers;

import java.util.LinkedList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.GameBorder;
import com.copter.game.assets.Updatable;

public class BorderManager implements Updatable{
  private static final String LOGGER_TAG = "BorderManager";
  private static final String BOTTOM_FIXTURE_DATA = "fixtures/bottomBorder";
  private static final String BOTTOM_FIXTURE_NAME = "bottomBorder";
  
  private static final Logger LOGGER = new Logger(LOGGER_TAG, Logger.INFO);
  
  private static final float X_INITIAL = 0;
  private static final float NO_ROTATION = 0;
  private static final float Y_BOTTOM = 0;
  
  
  private LinkedList<GameBorder> top, bottom;
  private GameBorder topActual, bottomActual;
  private static BorderManager instance;
  private World gameWorld;
  
  
  private BorderManager(World world) {
    initManager();
    fillBottomList(world);
    //top = new GameBorder();
    //topActual = top;
   
    
  }
  
  
  
  private void fillBottomList(World world) {
    GameBorder firstBottomBorder = new GameBorder(BOTTOM_FIXTURE_DATA, BOTTOM_FIXTURE_NAME);
    firstBottomBorder.init(world);
    firstBottomBorder.getBody().setTransform(X_INITIAL, Y_BOTTOM, NO_ROTATION);
    bottomActual = firstBottomBorder;
    
    try {
      GameBorder secondBottomBorder = (GameBorder) firstBottomBorder.clone();
      secondBottomBorder.getBody().setTransform(X_INITIAL + Copter2D.GAME_WIDTH, Y_BOTTOM, NO_ROTATION);
      bottom.add(secondBottomBorder);
      
    } catch (CloneNotSupportedException e) {
      LOGGER.error("Can not clone bottom border");
    }    
  }
  
  private void initManager() {
    top = new LinkedList<GameBorder>();
    bottom = new LinkedList<GameBorder>();
  }
  
  public static BorderManager getInstance(World world) {
    if (instance == null) {
      instance = new BorderManager(world);
    }
    
    return instance;
  }

  @Override
  public void update(float delta) {
    updateTop();
    updateBottom();
  }
  
  private void updateTop() {
    
  }

  private void updateBottom() {
    
    float xPlane = Airplane.getInstance().getBody().getPosition().x;
    
    if (((xPlane - Airplane.PLANE_LEFT_MARGIN) - bottomActual.getBody().getPosition().x) >= Copter2D.GAME_WIDTH ) {
      LOGGER.info("shifting bottom border");
      bottomActual.getBody().setTransform( bottomActual.getBody().getPosition().x + 2 * Copter2D.GAME_WIDTH, Y_BOTTOM, NO_ROTATION);
      bottom.add(bottomActual);
      bottomActual = bottom.poll();
    }
  }

}
