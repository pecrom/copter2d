package com.copter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.GameBorder;
import com.copter.managers.BorderManager;
import com.copter.managers.ObstacleManager;

public class GameScreen extends ScreenAdapter implements InputProcessor, ContactFilter {
  private static final float   GRAVITY                    = 0f; //TODO change back to -0.3f
  private static final float   HORIZONTAL_FORCE           = 0.0F;
  private static final boolean DO_DEBUG                   = true;
  private static final float   MAX_FORCE                  = 1.5f;

  private World              gameWorld;
  private Camera             gameCamera;
  private Box2DDebugRenderer debugRenderer;
  private Body               boxBody;
  private Body               obstacleTop, obstacleBottom, bonus;
  private FPSLogger          fpsLogger;

  private Airplane plane;
  private ObstacleManager obstacles;
  private BorderManager borders;
  

  public GameScreen() {
    fpsLogger = new FPSLogger();
    initWorld();
    if (DO_DEBUG) {
      debugRenderer = new Box2DDebugRenderer();
    }
    Gdx.input.setInputProcessor(this);
  }

  private void initWorld() {
    gameWorld = new World(new Vector2(HORIZONTAL_FORCE, GRAVITY), true);
    gameCamera = new OrthographicCamera(Copter2D.WIDTH / Copter2D.SCALE, Copter2D.HEIGHT / Copter2D.SCALE);
    gameCamera.position.set(Copter2D.WIDTH / Copter2D.SCALE / 2, Copter2D.HEIGHT / Copter2D.SCALE / 2, 0);

    plane = Airplane.getInstance();
    plane.init(gameWorld);
    
    obstacles = ObstacleManager.getInstance(gameWorld, plane);
    borders = BorderManager.getInstance(gameWorld);
   
    initObstacle();
    initBonus();
  }

  private void initBonus() {
    BodyDef circle = new BodyDef();
    circle.type = BodyType.KinematicBody;
    circle.position.set(new Vector2(7f, Copter2D.HEIGHT / Copter2D.SCALE / 2f));

    bonus = gameWorld.createBody(circle);

    CircleShape circleShape = new CircleShape();
    circleShape.setRadius(1f);

    FixtureDef bonusFix = new FixtureDef();
    bonusFix.density = 1f;
    bonusFix.friction = 1f;
    bonusFix.shape = circleShape;

    bonusFix.isSensor = true;

    bonus.createFixture(bonusFix);

    bonus.setLinearVelocity(-1f, 0);

  }

  private void initObstacle() {
    // TOP
    BodyDef obstacleTopDef = new BodyDef();
    obstacleTopDef.type = BodyType.StaticBody;
    obstacleTopDef.position.set(new Vector2(-5, 3));

    obstacleTop = gameWorld.createBody(obstacleTopDef);

    PolygonShape obstacleTopShape = new PolygonShape();
    obstacleTopShape.set(new float[] { 0, 0, 1, 0, 0.5f, -2.39f });

    FixtureDef obstacleTopFix = new FixtureDef();
    obstacleTopFix.density = 1000;
    obstacleTopFix.friction = 1;
    obstacleTopFix.shape = obstacleTopShape;

    obstacleTop.createFixture(obstacleTopFix);

    // BOTTOM
    BodyDef obstacleBottomDef = new BodyDef();
    obstacleBottomDef.type = BodyType.StaticBody;
    obstacleBottomDef.position.set(new Vector2(-5, 0));

    obstacleBottom = gameWorld.createBody(obstacleBottomDef);

    PolygonShape obstacleBottomShape = new PolygonShape();
    obstacleBottomShape.set(new float[] { 0, 0, 1, 0, 0.5f, 2.39f });

    FixtureDef obstacleBottomFix = new FixtureDef();
    obstacleBottomFix.density = 1000;
    obstacleBottomFix.friction = 0;
    obstacleBottomFix.shape = obstacleBottomShape;

    obstacleBottom.createFixture(obstacleBottomFix);

  }

  @Override
  public void render(float delta) {
    updateWorld(delta);
    GameScreen.updateGraphics();
    fpsLogger.log();
 

    gameCamera.position.x = plane.getBody().getPosition().x + 3;

    
    gameCamera.update();
    if (DO_DEBUG) {
      debugRenderer.render(gameWorld, gameCamera.combined);

    }

  }

  private void updateWorld(float delta) {
    gameWorld.step(delta, 8, 3);
    plane.update(delta);
    obstacles.update(delta);
    borders.update(delta);
  }

  private static void updateGraphics() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
  }
  
  @Override
  public boolean keyDown(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    Vector2 boxCenterPosition = new Vector2(boxBody.getPosition().x, boxBody.getPosition().y);
    Vector3 touchPosition = gameCamera.unproject(new Vector3(screenX, screenY, 0));
    Vector2 distanceVec = new Vector2(boxCenterPosition);
    Vector2 forceSize = distanceVec.sub(touchPosition.x, touchPosition.y); // fix
                                                                           // this

    float maxDistanceForce = Copter2D.HEIGHT / Copter2D.SCALE;
    float forceToApply = Math.abs(forceSize.y / maxDistanceForce * MAX_FORCE);

    
    if (boxCenterPosition.y < touchPosition.y)
      boxBody.applyLinearImpulse(new Vector2(0, -forceToApply), boxBody.getPosition(), true);
    else
      boxBody.applyLinearImpulse(new Vector2(0, forceToApply), boxBody.getPosition(), true);
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

    return false;
  }

}
