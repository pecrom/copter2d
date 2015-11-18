package com.copter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.copter.Copter2D;

public class DesktopLauncher {
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = (int) Copter2D.WIDTH;
    config.height = (int) Copter2D.HEIGHT;
    new LwjglApplication(new Copter2D(), config);
  }
}
