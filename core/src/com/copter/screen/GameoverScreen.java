package com.copter.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameoverScreen extends ScreenAdapter {
  private SpriteBatch batch;
  private BitmapFont font;
  
  public GameoverScreen() {
    batch = new SpriteBatch();
    font = new BitmapFont();
  }
  
  @Override
  public void render(float delta) {
    batch.begin();
    font.draw(batch, "Game over", 350, 200);
    batch.end();
  }

}
