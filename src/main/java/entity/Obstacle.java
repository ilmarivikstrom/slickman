package entity;

import config.Config;
import enums.BlockType;

public class Obstacle extends Block {

  public Obstacle(float x, float y) {
    super(
        x,
        y,
        BlockType.OBSTACLE,
        Config.OBSTACLE_COLOR,
        true
    );
  }
}
