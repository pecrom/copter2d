package com.copter.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.Updatable;
import com.copter.game.assets.bonus.Bonus;
import com.copter.game.assets.bonus.FuelBonus;
import com.copter.game.assets.bonus.ShieldBonus;
import com.copter.game.assets.bonus.SpeedBonus;
import com.copter.utils.Utils;

public class BonusManager implements Updatable {
  private static final String LOGGER_TAG        = "BonusManager";

  private static final Logger LOGGER            = new Logger(LOGGER_TAG, Logger.INFO);
  
  private static BonusManager instance          = null;
  private static final int          HOW_OFTEN_TO_SHOW = 5;
  private static final float        NOT_SCHEDULED     = 0;

  private World               world;
  private Airplane            plane;
  private List<Bonus>         bonuses;
  private Bonus               shownBonus        = null;
  private float               showIn            = NOT_SCHEDULED;

  private BonusManager(World world, Airplane plane) {
    this.world = world;
    this.plane = plane;

    fillBonuses();
    showIn = schedule();
    LOGGER.info("WILL BE SHOWN IN: " + showIn);
  }

  private void fillBonuses() {
    bonuses = new ArrayList<Bonus>();
    bonuses.add(new SpeedBonus());
    bonuses.add(new FuelBonus());
    bonuses.add(new ShieldBonus());

    for (Bonus initialisingBonus : bonuses) {
      initialisingBonus.init(world);
    }
  }

  /**
   * Returns instance of BonusManager.
   * @param world instance of World class
   * @param plane instance of Airplane class
   * @return instance of BonusManager
   */
  public static BonusManager getInstance(World world, Airplane plane) {
    if (instance == null) {
      instance = new BonusManager(world, plane);
    }

    return instance;
  }

  private static float schedule() {
    return (float) (Math.random() * HOW_OFTEN_TO_SHOW);
  }

  @Override
  public void update(float delta) {
    if (shownBonus == null) {
      
      if (showIn <= NOT_SCHEDULED) {        
        shownBonus = chooseBonus();
        LOGGER.info("Bonus type: " + shownBonus.getClass().getName());
        setupBonusToShow(shownBonus);
        showIn = schedule();
        LOGGER.info("WILL BE SHOWN IN: " + showIn);
      } else {        
        showIn -= delta;        
      }
    } else if (!Utils.isInViewableArea(plane, shownBonus)) { //if the bonus is out of viewable area then deactivate it
      LOGGER.info("Bonus");
      shownBonus.getBody().setActive(false);
      shownBonus = null;
    }
  }

  private void setupBonusToShow(Bonus chosenBonus) {
    float xPosition = plane.getDistance() + Copter2D.GAME_WIDTH;
    float yPosition = (float) (Math.random() * Copter2D.GAME_HEIGHT);
    chosenBonus.getBody().setActive(true);
    chosenBonus.setPosition(xPosition, yPosition);
  }

  private Bonus chooseBonus() {     
    int randomIndex = Utils.getRandomValue(bonuses.size());
    randomIndex = randomIndex == bonuses.size() ? bonuses.size() - 1 : randomIndex; //to prevent IndexOutOfBoundsException, if randomIndex is same like bonuses size, then substract one
    return bonuses.get(randomIndex);
  }

}
