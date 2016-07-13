package com.copter.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

  private static final String  PACK_FILE_NAME  = "atlases/copter2d.atlas";

  private static final String  IMAGES_DIR_NAME = "images/atlas/";

  private TextureAtlas         atlas;

  private Map<String, TextureRegion> texturesCache;

  private TextureProvider() {
    atlas = new TextureAtlas(Gdx.files.internal(PACK_FILE_NAME), Gdx.files.internal(IMAGES_DIR_NAME));
    texturesCache = new HashMap<String, TextureRegion>();
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
   * Returns texture region from the texture pack according to texture region name. 
   * @param name of the texture region
   * @return found texture region
   */
  public TextureRegion getTextureRegion(String name) {
    TextureRegion found = texturesCache.get(name);

    if (found == null) {
      TextureRegion region = atlas.findRegion(name);

      if (region != null) {
        found = region;
        texturesCache.put(name, found);
      } else {
        LOGGER.error("Texture with name " + name + " was not found in texture pack " + PACK_FILE_NAME);
      }
    }

    return found;
  }
}
