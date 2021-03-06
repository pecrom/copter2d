package com.copter.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.Updatable;
import com.copter.game.assets.meteorits.BigMeteorit;
import com.copter.game.assets.meteorits.MediumMeteorit;
import com.copter.game.assets.meteorits.Meteorit;
import com.copter.game.assets.meteorits.SmallMeteorit;
import com.copter.game.graphics.GraphicsUpdatable;
import com.copter.utils.Utils;

public class MeteoritManager implements Updatable, GraphicsUpdatable{
  private static final String    LOGGER_TAG          = MeteoritManager.class.getName();
  private static final Logger    LOGGER              = new Logger(LOGGER_TAG, Logger.INFO);
  private static final float HALF_OF_WIDTH = 2;
  
  private static MeteoritManager instance;
  private static final long MIN_DELAY_INTERVAL = 7L; //seconds
  private static final float OVER_VIEWABLE_AREA = 0f;
  
  private World world;
  private float timeSinceLastDisappear = 0;
  private List<Meteorit> meteorits;
  private Meteorit shownMeteorit;
  
  private MeteoritManager(World world) {
    meteorits = new ArrayList<Meteorit>();
    this.world = world;
    initMeteorits();
  }
  
  public static MeteoritManager getInstance(World world) {
    if (instance == null) {
      instance = new MeteoritManager(world);
    }
    return instance;
  }
  
  private void initMeteorits() {
    Meteorit big = new BigMeteorit();
    big.init(world);
    meteorits.add(big);
    Meteorit med = new MediumMeteorit();
    med.init(world);
    meteorits.add(med);
    Meteorit small = new SmallMeteorit();
    small.init(world);
    meteorits.add(small);
  }

  @Override
  public void update(float delta) {
    
    if (shownMeteorit == null) {
      timeSinceLastDisappear += delta;
      if (timeSinceLastDisappear > MIN_DELAY_INTERVAL && Utils.getRandomTrue()) {
        shownMeteorit = chooseMeteorit();
        Vector2 coordinates = getPositionCoordinates(shownMeteorit);
        shownMeteorit.prepareForShow(coordinates);
        
      }
    } else {
      shownMeteorit.update(delta);
      float leftBorder = Airplane.getInstance().getDistance() - Airplane.PLANE_LEFT_MARGIN;
      float shownRightBorder = shownMeteorit.getBody().getPosition().x + (shownMeteorit.getWidth() / HALF_OF_WIDTH);
      if ((leftBorder - shownRightBorder) > OVER_VIEWABLE_AREA) {
        shownMeteorit = null;
        timeSinceLastDisappear = 0;
      } 
    } 
  }
  
  
  
  private static Vector2 getPositionCoordinates(Meteorit meteoritToShow) {
    float yCoordinate = (float) (Math.random() * Copter2D.GAME_HEIGHT);
    yCoordinate = (yCoordinate + meteoritToShow.getHeight()) > Copter2D.GAME_HEIGHT ? (yCoordinate - meteoritToShow.getHeight()) : yCoordinate;
    float xCoordinate = Airplane.getInstance().getDistance() + Copter2D.GAME_WIDTH;
    return new Vector2(xCoordinate, yCoordinate);
    
  }
  
  private Meteorit chooseMeteorit() {    
    int index = (int) (Math.random() * meteorits.size());
    return meteorits.get(index); //index
  }

  @Override
  public void updateGraphics(float delta, SpriteBatch batch) {
   if (shownMeteorit != null) {
     //LOGGER.info("Meteorit position: " + shownMeteorit.getBody().getPosition().x + " " + shownMeteorit.getBody().getPosition().x * Copter2D.SCALE);
     //batch.draw(shownMeteorit.getTextureRegion(), (shownMeteorit.getBody().getPosition().x - (Airplane.getInstance().getDistance() - Airplane.PLANE_LEFT_MARGIN)) * Copter2D.SCALE, shownMeteorit.getBody().getPosition().y * Copter2D.SCALE);
     batch.draw(shownMeteorit.getTextureRegion(),
         (shownMeteorit.getBody().getPosition().x - (Airplane.getInstance().getDistance() - Airplane.PLANE_LEFT_MARGIN) - shownMeteorit.getWidth() / 2) * Copter2D.SCALE, (shownMeteorit.getBody().getPosition().y - shownMeteorit.getHeight() / 2) * Copter2D.SCALE, shownMeteorit.getTextureRegion().getRegionWidth() / 2, shownMeteorit.getTextureRegion().getRegionHeight() / 2, shownMeteorit.getTextureRegion().getRegionHeight(), shownMeteorit.getTextureRegion().getRegionHeight(),
         1.1f, 1.1f, (float)Math.toDegrees(shownMeteorit.getRotation()));
     LOGGER.info("Rotation: " + shownMeteorit.getRotation());
   }
  }
}
