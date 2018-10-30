package entity;

import config.Config;
import enums.BlockType;

public class Goal extends Block {

  public Goal(float x, float y) {
    super(
        x,
        y,
        BlockType.GOAL,
        Config.GOAL_COLOR,
        true
    );
  }
}
