package com.copter.managers;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.GameBorder;
import com.copter.game.assets.Updatable;

public class BorderManager implements Updatable {  
  private static final String    BOTTOM_FIXTURE_DATA = "fixtures/bottomBorder";
  private static final String    BOTTOM_FIXTURE_NAME = "bottomBorder";
  private static final String    TOP_FIXTURE_DATA    = "fixtures/topBorder";
  private static final String    TOP_FIXTURE_NAME    = "topBorder";

  private static final float     BORDER_REAL_HEIGHT  = 71;
  private static final float     X_INITIAL           = 0;
  private static final float     NO_ROTATION         = 0;
  private static final float     Y_BOTTOM            = 0;
  private static final float     Y_TOP               = Copter2D.GAME_HEIGHT - (BORDER_REAL_HEIGHT / Copter2D.SCALE);

  private LinkedList<GameBorder> top, bottom;
  private GameBorder             topActual, bottomActual;
  private static BorderManager   instance;

  private BorderManager(World world) {
    initManager();
    topActual = fillList(world, TOP_FIXTURE_DATA, TOP_FIXTURE_NAME, top, Y_TOP);
    bottomActual = fillList(world, BOTTOM_FIXTURE_DATA, BOTTOM_FIXTURE_NAME, bottom, Y_BOTTOM);

  }

  private static GameBorder fillList(World world, String fixtureData, String fixtureName, Queue<GameBorder> queue,
      float yPosition) {

    GameBorder firstBorder = new GameBorder(fixtureData, fixtureName);
    firstBorder.init(world);
    firstBorder.getBody().setTransform(X_INITIAL, yPosition, NO_ROTATION);

    GameBorder secondBorder = new GameBorder(firstBorder);
    secondBorder.getBody().setTransform(X_INITIAL + Copter2D.GAME_WIDTH, yPosition, NO_ROTATION);
    queue.add(secondBorder);

    return firstBorder;
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
    topActual = updateBorder(topActual, top);
    bottomActual = updateBorder(bottomActual, bottom);

  }

  private static GameBorder updateBorder(GameBorder actual, Queue<GameBorder> queue) {
    float xPlane = Airplane.getInstance().getBody().getPosition().x;
    GameBorder updatedBorder = actual;
    
    if (((xPlane - Airplane.PLANE_LEFT_MARGIN) - actual.getBody().getPosition().x) >= Copter2D.GAME_WIDTH) {
      actual.getBody().setTransform(actual.getBody().getPosition().x + 2 * Copter2D.GAME_WIDTH,
          actual.getBody().getPosition().y, NO_ROTATION);
      queue.add(actual);
      updatedBorder = queue.poll();
    }
    return updatedBorder;
  }
}
