package util;

import entity.Block;

public class DistanceCalculator {

  public static String ManhattanAsString(Block startBlock, Block endBlock) {
    return Float.toString(Manhattan(startBlock, endBlock));
  }

  public static String ManhattanAsString(float xStart, float yStart, float xEnd, float yEnd) {
    return Float.toString(Manhattan(xStart, yStart, xEnd, yEnd));
  }

  private static float Manhattan(Block startBlock, Block endBlock) {
    return Manhattan(startBlock.getX(), startBlock.getY(), endBlock.getX(), endBlock.getY());
  }

  private static float Manhattan(float xStart, float yStart, float xEnd, float yEnd) {
    return Math.abs(xEnd - xStart) + Math.abs(yEnd - yStart);
  }

  public static String EuclideanAsString(Block startBlock, Block endBlock) {
    return Float.toString(Euclidean(startBlock, endBlock));
  }

  public static String EuclideanAsString(float xStart, float yStart, float xEnd, float yEnd) {
    return Float.toString(Euclidean(xStart, yStart, xEnd, yEnd));
  }

  public static float Euclidean(Block startBlock, Block endBlock) {
    return Euclidean(startBlock.getX(), startBlock.getY(), endBlock.getX(), endBlock.getY());
  }

  private static float Euclidean(float xStart, float yStart, float xEnd, float yEnd) {
    double deltaX = xEnd - xStart;
    double deltaY = yEnd - yStart;
    return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }
}
