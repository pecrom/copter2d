package com.copter.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Logger;

/**
 * This class handles providing of images from texture atlas.
 * 
 * @author Roman Pecek
 *
 */
public class TextureProvider {
  private static final String  LOGGER_TAG      = TextureProvider.class.getName();

  private static final Logger  LOGGER          = new Logger(LOGGER_TAG, Logger.INFO);

  private static TextureProvider instance;

  private static final String  PACK_FILE_NAME  = "copter2d";

  private static final String  IMAGES_DIR_NAME = "images/atlas/";

  private TextureAtlas         atlas;

  private Map<String, Texture> texturesCache;

  private TextureProvider() {
    atlas = new TextureAtlas(Gdx.files.internal(PACK_FILE_NAME), Gdx.files.internal(IMAGES_DIR_NAME));
    texturesCache = new HashMap<String, Texture>();
  }

  /**
   * Returns singleton of ImageProvider.
   * 
   * @return instance of ImageProvider
   */
  public static TextureProvider getInstance() {
    if (instance == null) {
      instance = new TextureProvider();
    }

    return instance;
  }

  
  /**
   * Returns texture from the texture pack according to texture name. 
   * @param name of the texture
   * @return found texture
   */
  public Texture getTexture(String name) {
    Texture found = texturesCache.get(name);

    if (found == null) {
      AtlasRegion region = atlas.findRegion(name);

      if (region != null) {
        found = region.getTexture();
        texturesCache.put(name, found);
      } else {
        LOGGER.error("Texture with name " + name + " was not found in texture pack " + PACK_FILE_NAME);
      }
    }

    return found;
  }
}
