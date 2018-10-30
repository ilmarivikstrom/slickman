package entity;

import config.Config;
import enums.BlockType;

public class EmptyBlock extends Block {

  public EmptyBlock(float x, float y) {
    super(
        x,
        y,
        BlockType.EMPTY,
        Config.EMPTY_COLOR,
        false
    );
  }
}
