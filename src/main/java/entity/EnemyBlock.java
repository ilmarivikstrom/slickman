package entity;

import config.Config;
import enums.BlockType;

public class EnemyBlock extends Block {

  public EnemyBlock(float x, float y) {
    super(
        x,
        y,
        BlockType.ENEMY,
        Config.ENEMY_COLOR,
        true
    );

  }
}
