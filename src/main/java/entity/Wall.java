package entity;

import config.Config;
import enums.BlockType;

public class Wall extends Block {

  public Wall(float x, float y) {
    super(
        x,
        y,
        BlockType.WALL,
        Config.WALL_COLOR,
        true
    );
  }
}