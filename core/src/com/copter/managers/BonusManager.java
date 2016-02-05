package com.copter.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;
import com.copter.game.assets.Updatable;
import com.copter.game.assets.bonus.Bonus;
import com.copter.game.assets.bonus.SpeedBonus;

public class BonusManager implements Updatable {
  private static final String LOGGER_TAG        = "BonusManager";

  private static final Logger LOGGER            = new Logger(LOGGER_TAG, Logger.INFO);
  
  private static BonusManager instance          = null;
  private static int          HOW_OFTEN_TO_SHOW = 5;
  private static float        NOT_SCHEDULED     = 0;

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

    for (Bonus initialisingBonus : bonuses) {
      initialisingBonus.init(world);
    }
  }

  public static BonusManager getInstance(World world, Airplane plane) {
    if (instance == null) {
      instance = new BonusManager(world, plane);
    }

    return instance;
  }

  private float schedule() {
    return (float) (Math.random() * HOW_OFTEN_TO_SHOW);
  }

  @Override
  public void update(float delta) {
    if (shownBonus == null) {
      LOGGER.info("NOT SHOWN");
      if (showIn <= NOT_SCHEDULED) {
        
        shownBonus = chooseBonus();
        setupBonusToShow(shownBonus);
        showIn = schedule();
        LOGGER.info("WILL BE SHOWN IN: " + showIn);
      } else {
        showIn -= delta;
      }
    }
  }

  private void setupBonusToShow(Bonus chosenBonus) {
    float xPosition = plane.getDistance() + Copter2D.GAME_WIDTH;
    float yPosition = (float) (Math.random() * Copter2D.GAME_HEIGHT);

    chosenBonus.setPosition(xPosition, yPosition);
  }

  private Bonus chooseBonus() {
    int typeIndex = (int) Math.random() * bonuses.size();
    return bonuses.get(typeIndex);
  }

}
