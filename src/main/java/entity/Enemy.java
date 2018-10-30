package entity;

import enums.Direction;

public class Enemy {

  private EnemyBlock enemyHead;
  private Direction direction;

  public Enemy(float x, float y) {
    this.enemyHead = new EnemyBlock(x, y);
    this.direction = Direction.NONE;
  }

  public EnemyBlock getEnemyHead() {
    return enemyHead;
  }

  public void setEnemyHead(EnemyBlock enemyHead) {
    this.enemyHead = enemyHead;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
