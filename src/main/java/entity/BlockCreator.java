package entity;

public class BlockCreator {

  public static Block CreateReplaceBlock(Block toBeReplaced, java.lang.Class proposedBlock) {
    if (proposedBlock.getName().equals("entity.Obstacle")) {
      return new Obstacle(toBeReplaced.getBlockX(), toBeReplaced.getBlockY());
    } else {
      return new EmptyBlock(toBeReplaced.getBlockX(), toBeReplaced.getBlockY());
    }
  }
}
