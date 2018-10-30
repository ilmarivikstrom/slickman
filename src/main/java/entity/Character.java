package entity;

import config.Config;
import enums.Direction;

public class Character {

  private CharacterBlock characterHead;
  private Direction direction;
  private Direction previousDirection;

  public Character() {
    this.characterHead = new CharacterBlock(Config.INITIAL_CHARACTER_POSX,
        Config.INITIAL_CHARACTER_POSY);
    this.direction = Direction.E;
    this.previousDirection = Direction.E;
  }

  public void Move() {
    Move(this.previousDirection);
  }

  public void Move(Direction direction) {
    if (direction == Direction.NONE) {
      direction = this.previousDirection;
    }
    switch (direction) {
      case N:
        this.characterHead.setY(this.characterHead.getY() - Config.TILE_SIZE);
        break;
      case S:
        this.characterHead.setY(this.characterHead.getY() + Config.TILE_SIZE);
        break;
      case E:
        this.characterHead.setX(this.characterHead.getX() + Config.TILE_SIZE);
        break;
      case W:
        this.characterHead.setX(this.characterHead.getX() - Config.TILE_SIZE);
        break;
    }
    if (direction != Direction.NONE) {
      this.previousDirection = direction;
    }
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction getPreviousDirection() {
    return previousDirection;
  }

  public void setPreviousDirection(Direction previousDirection) {
    this.previousDirection = previousDirection;
  }

  public CharacterBlock getCharacterHead() {
    return characterHead;
  }

  public void setCharacterHead(CharacterBlock characterHead) {
    this.characterHead = characterHead;
  }
}
