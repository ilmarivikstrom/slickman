package entity;

import config.Config;
import enums.BlockType;

public class CharacterBlock extends Block {

  public CharacterBlock(float x, float y) {
    super(
        x,
        y,
        BlockType.CHARACTER,
        Config.CHARACTER_COLOR,
        true
        );
  }
}
