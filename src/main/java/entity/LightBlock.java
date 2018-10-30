package entity;

public class LightBlock extends Block {

  public LightBlock(Block block, float distance) {
    // Fix this to use some kind of a shade / light
    super(
        block.getBlockX(),
        block.getBlockY(),
        block.getBlockType(),
        block.getFillColor(),
        false
    );
  }
}
