package config;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import org.newdawn.slick.Color;

public class Config {

  public static float TILE_SIZE;
  public static float WIDTH;
  public static float HEIGHT;
  public static int INITIAL_CHARACTER_POSY;
  public static int INITIAL_CHARACTER_POSX;
  public static Color GLOBAL_LINE_COLOR;
  public static Color EDITMODE_OVERLAY_COLOR;
  public static Color GOAL_COLOR;
  public static Color CHARACTER_COLOR;
  public static Color EMPTY_COLOR;
  public static Color EMPTY_BRIGHT_COLOR;
  public static Color HAZE_COLOR;
  public static Color WALL_COLOR;
  public static Color OBSTACLE_COLOR;
  private static Color CROSSHAIR_COLOR;
  public static Color ENEMY_COLOR;
  public static Color DUMMY_COLOR;
  private final Properties prop = new Properties();

  public Config() {
    try {
      String configFile = System.getProperty("user.dir") + "/resources/config.properties";

      InputStream is = new FileInputStream(configFile);
      prop.load(is);

    } catch (IOException e) {
      e.printStackTrace();
    }
    initializeProperties();
  }

  private void initializeProperties() {
    TILE_SIZE = Float.parseFloat(this.getProperty("block.size"));
    WIDTH = Float.parseFloat(this.getProperty("world.width"));
    HEIGHT = Float.parseFloat(this.getProperty("world.height"));
    INITIAL_CHARACTER_POSX = Integer.parseInt(this.getProperty("character.initial.pos.blockX"));
    INITIAL_CHARACTER_POSY = Integer.parseInt(this.getProperty("character.initial.pos.blockY"));
    GLOBAL_LINE_COLOR = this.getColor("global.line");
    EDITMODE_OVERLAY_COLOR = this.getColor("editmode.overlay");
    GOAL_COLOR = this.getColor("goal");
    CHARACTER_COLOR = this.getColor("character");
    EMPTY_COLOR = this.getColor("empty");
    CROSSHAIR_COLOR = this.getColor("crosshair");
    WALL_COLOR = this.getColor("wall");
    OBSTACLE_COLOR = this.getColor("obstacle");
    ENEMY_COLOR = this.getColor("enemy");
    DUMMY_COLOR = this.getColor("dummy");
    EMPTY_BRIGHT_COLOR = this.getColor("empty.bright");
    HAZE_COLOR = this.getColor("haze");


  }

  private String getProperty(String key) {
    return prop.get(key).toString();
  }

  private Color getColor(String keyPart) {
    String key = "color." + keyPart;
    int[] globalLineColor = Arrays.stream(this.getProperty(key).split(","))
        .mapToInt(Integer::parseInt).toArray();

    return new Color(globalLineColor[0], globalLineColor[1], globalLineColor[2],
        globalLineColor[3]);
  }
}