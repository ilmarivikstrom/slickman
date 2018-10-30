package input;

public class UserMouseInput {

  private int blockX;
  private int blockY;

  public UserMouseInput(int blockX, int blockY) {
    this.blockX = blockX;
    this.blockY = blockY;
  }

  public UserMouseInput() {
    this.blockX = -1;
    this.blockY = -1;
  }

  public int getBlockX() {
    return blockX;
  }

  public void setBlockX(int blockX) {
    this.blockX = blockX;
  }

  public int getBlockY() {
    return blockY;
  }

  public void setBlockY(int blockY) {
    this.blockY = blockY;
  }
}