package input;

import enums.Direction;

public class UserInputResult {

  private Direction direction;
  private boolean moveCommandGiven;
  private boolean pause;
  private boolean shouldGenerateNewMap;

  public UserInputResult() {
    this.direction = Direction.NONE;
    this.moveCommandGiven = false;
    this.shouldGenerateNewMap = false;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public boolean isMoveCommandGiven() {
    return moveCommandGiven;
  }

  public void setMoveCommandGiven(boolean moveCommandGiven) {
    this.moveCommandGiven = moveCommandGiven;
  }

  public boolean isPause() {
    return pause;
  }

  public void setPause(boolean pause) {
    this.pause = pause;
  }

  public boolean isShouldGenerateNewMap() {
    return shouldGenerateNewMap;
  }

  public void setShouldGenerateNewMap(boolean shouldGenerateNewMap) {
    this.shouldGenerateNewMap = shouldGenerateNewMap;
  }
}
