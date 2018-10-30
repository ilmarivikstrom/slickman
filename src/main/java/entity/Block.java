package entity;

import config.Config;
import enums.BlockType;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public abstract class Block extends Rectangle {

  private final BlockType blockType;
  private Rectangle block;
  private Color fillColor;
  private Color lineColor;
  private boolean solid;

  Block(float x, float y, BlockType blockType, Color fillColor, boolean solid) {
    super(x * Config.TILE_SIZE, y * Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
    this.blockType = blockType;
    this.fillColor = fillColor;
    this.lineColor = Config.GLOBAL_LINE_COLOR;
    this.solid = solid;
  }

  public List<Point> getCornersAsPoints() {
    List<Point> corners = new ArrayList<>();
    float[] points = this.getPoints();
    for (int i = 0; i < points.length; i += 2) {
      corners.add(new Point(points[i], points[i + 1]));
    }
    return corners;
  }

  public float getBlockX() {
    return super.getX() / Config.TILE_SIZE;
  }

  public float getBlockY() {
    return super.getY() / Config.TILE_SIZE;
  }

  public Rectangle getBlock() {
    return block;
  }

  public void setBlock(Rectangle block) {
    this.block = block;
  }

  public BlockType getBlockType() {
    return blockType;
  }

  public Color getFillColor() {
    return fillColor;
  }

  public void setFillColor(Color fillColor) {
    this.fillColor = fillColor;
  }

  public Color getLineColor() {
    return lineColor;
  }

  public void setLineColor(Color lineColor) {
    this.lineColor = lineColor;
  }

  public boolean isSolid() {
    return solid;
  }

  public void setSolid(boolean solid) {
    this.solid = solid;
  }
}
