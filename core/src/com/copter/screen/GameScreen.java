package com.copter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.collisions.CollisionsChecker;
import com.copter.game.input.GameInput;
import com.copter.managers.BonusManager;
import com.copter.managers.BorderManager;
import com.copter.managers.MeteoritManager;
import com.copter.managers.ObstacleManager;
import com.copter.utils.Box2dUtils;

public class GameScreen extends ScreenAdapter {
  private static final float   GRAVITY          = -0.3f;             
  private static final float   HORIZONTAL_FORCE = 0.0F;
  private static final boolean DO_DEBUG         = true;

  private World                gameWorld;
  private Camera               gameCamera;
  private Box2DDebugRenderer   debugRenderer;
  private Body                 boxBody;
  private Body                 obstacleTop, obstacleBottom, bonus;
  private FPSLogger            fpsLogger;

  private Airplane             plane;
  private ObstacleManager      obstacles;
  private BorderManager        borders;
  private MeteoritManager      meteorits;
  private BonusManager         bonuses;

  
  /**
   * Creates main game screen.
   */
  public GameScreen() {
    fpsLogger = new FPSLogger();
    initWorld();
    initCamera();
    initPlane();
    initObstacle();
    initManagers();
    Gdx.input.setInputProcessor(new GameInput(gameWorld, plane, gameCamera));    
    
    if (DO_DEBUG) {
      debugRenderer = new Box2DDebugRenderer();
    }    
  }

  private void initManagers() {
    obstacles = ObstacleManager.getInstance(gameWorld, plane);
    borders = BorderManager.getInstance(gameWorld);
    meteorits = MeteoritManager.getInstance(gameWorld);
    bonuses = BonusManager.getInstance(gameWorld, plane);
  }
  
  private void initCamera() {
    gameCamera = new OrthographicCamera(Copter2D.WIDTH / Copter2D.SCALE, Copter2D.HEIGHT / Copter2D.SCALE);
    gameCamera.position.set(Copter2D.WIDTH / Copter2D.SCALE / 2, Copter2D.HEIGHT / Copter2D.SCALE / 2, 0);
  }
  
  private void initPlane() {
    plane = Airplane.getInstance();
    plane.init(gameWorld);
  }
  
  /**
   * Initialise world with collision checker.
   */
  private void initWorld() {
    CollisionsChecker checker = new CollisionsChecker();   
    
    gameWorld = new World(new Vector2(HORIZONTAL_FORCE, GRAVITY), true);    
    gameWorld.setContactFilter(checker);
    gameWorld.setContactListener(checker);            
  }

  private void initObstacle() {
    // TOP
    obstacleTop = Box2dUtils.createBody(gameWorld, BodyType.StaticBody, new Vector2(-5, 3));    

    PolygonShape obstacleTopShape = new PolygonShape();
    obstacleTopShape.set(new float[] { 0, 0, 1, 0, 0.5f, -2.39f });

    FixtureDef obstacleTopFix = Box2dUtils.createFixtureDef(1000, 1, obstacleTopShape);

    obstacleTop.createFixture(obstacleTopFix);

    // BOTTOM    
    obstacleBottom = Box2dUtils.createBody(gameWorld, BodyType.StaticBody, new Vector2(-5, 0));     
    
    PolygonShape obstacleBottomShape = new PolygonShape();
    obstacleBottomShape.set(new float[] { 0, 0, 1, 0, 0.5f, 2.39f });

    FixtureDef obstacleBottomFix = Box2dUtils.createFixtureDef(1000, 1, obstacleBottomShape);
    obstacleBottom.createFixture(obstacleBottomFix);

  }

  @Override
  public void render(float delta) {
    updateWorld(delta);
    updateGraphics();
    fpsLogger.log();

    gameCamera.position.x = plane.getBody().getPosition().x + 3;

    gameCamera.update();
    if (DO_DEBUG) {
      debugRenderer.render(gameWorld, gameCamera.combined);

    }

  }

  private void updateWorld(float delta) {
    gameWorld.step(delta, 3, 8);
    plane.update(delta);
    obstacles.update(delta);
    borders.update(delta);
    meteorits.update(delta);
    bonuses.update(delta);

  }

  private void updateGraphics() {
    //clearing screen   
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
  }
  
}
