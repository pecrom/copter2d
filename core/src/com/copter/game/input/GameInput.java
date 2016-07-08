package com.copter.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.copter.Copter2D;
import com.copter.game.assets.Airplane;

public class GameInput implements InputProcessor {

  private static final float   MAX_FORCE        = 1.5f;
  
  private World world;
  private Airplane plane;
  private Camera camera;
  
  public GameInput(World world, Airplane plane, Camera camera) {
    this.world = world;
    this.plane = plane;
    this.camera = camera;
  }

  @Override
  public boolean keyDown(int keycode) {
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (plane.getFuel() > 0) { //plane still has some fuel
      controlPlane(screenX, screenY);
    }
    
    return true;
  }

  private void controlPlane(int screenX, int screenY) {
    Vector2 boxCenterPosition = new Vector2(plane.getBody().getPosition().x, plane.getBody().getPosition().y);
    Vector3 touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
    Vector2 distanceVec = new Vector2(boxCenterPosition);
    Vector2 forceSize = distanceVec.sub(touchPosition.x, touchPosition.y); // fix
                                                                           // this

    float maxDistanceForce = Copter2D.HEIGHT / Copter2D.SCALE;
    float forceToApply = Math.abs(forceSize.y / maxDistanceForce * MAX_FORCE);

    if (boxCenterPosition.y < touchPosition.y) {
      plane.getBody().applyLinearImpulse(new Vector2(0, -forceToApply), plane.getBody().getPosition(), true);
    } else {
      plane.getBody().applyLinearImpulse(new Vector2(0, forceToApply), plane.getBody().getPosition(), true);
    }

  }
  
  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }

}
