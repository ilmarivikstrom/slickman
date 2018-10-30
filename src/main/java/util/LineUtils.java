package util;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;

public class LineUtils {

  private static double AngleBetweenLines(Line ln1, Line ln2) {
    double angle1 = Math
        .toDegrees(Math.atan2(ln1.getY2() - ln1.getY1(), ln1.getX1() - ln1.getX2()));
    double angle2 = Math
        .toDegrees(Math.atan2(ln2.getY2() - ln2.getY1(), ln2.getX1() - ln2.getX2()));

    // Fix this somehow!
    double desiredAngle = Math.abs(angle1 - angle2);
    if (desiredAngle < 0) {
      desiredAngle += 180;
    }
    if (desiredAngle > 180) {
      desiredAngle -= 180;
    }
    return desiredAngle;
  }

  private static double AngleBetweenPoints(Point main, Point a, Point b) {
    Line mainA = new Line(main.getX(), main.getY(), a.getX(), a.getY());
    Line mainB = new Line(main.getX(), main.getY(), b.getX(), b.getY());
    double angle1 = Math.atan2(mainA.getY1() - mainA.getY2(), mainA.getX1() - mainA.getX2());
    double angle2 = Math.atan2(mainB.getY1() - mainB.getY2(), mainB.getX1() - mainB.getX2());
    double desiredAngle = angle1 - angle2;
    return Math.abs(desiredAngle);
  }

  public static List<Line> GetLargestAngleLines(List<Line> lines) {
    List<Line> biggestAngles = new ArrayList<>();
    double ang01 = LineUtils.AngleBetweenLines(lines.get(0), lines.get(1));
    double ang12 = LineUtils.AngleBetweenLines(lines.get(1), lines.get(2));
    double ang23 = LineUtils.AngleBetweenLines(lines.get(2), lines.get(3));
    double ang02 = LineUtils.AngleBetweenLines(lines.get(0), lines.get(2));
    double ang03 = LineUtils.AngleBetweenLines(lines.get(0), lines.get(3));
    double ang13 = LineUtils.AngleBetweenLines(lines.get(1), lines.get(3));
    double maximum = Math
        .max(ang01, Math.max(ang12, Math.max(ang23, Math.max(ang02, Math.max(ang03, ang13)))));
    if (maximum == ang01) {
      biggestAngles.add(lines.get(0));
      biggestAngles.add(lines.get(1));
      return biggestAngles;
    } else if (maximum == ang12) {
      biggestAngles.add(lines.get(1));
      biggestAngles.add(lines.get(2));
      return biggestAngles;
    } else if (maximum == ang23) {
      biggestAngles.add(lines.get(2));
      biggestAngles.add(lines.get(3));
      return biggestAngles;
    } else if (maximum == ang02) {
      biggestAngles.add(lines.get(0));
      biggestAngles.add(lines.get(2));
      return biggestAngles;
    } else if (maximum == ang03) {
      biggestAngles.add(lines.get(0));
      biggestAngles.add(lines.get(3));
      return biggestAngles;
    } else {
      biggestAngles.add(lines.get(1));
      biggestAngles.add(lines.get(3));
      return biggestAngles;
    }
  }

  public static List<Point> GetLargestAnglePoints(Point main, List<Point> allPoints) {
    double biggestAngle = 0;
    List<Point> biggestAnglePoints = new ArrayList<>();
    for (int i = 0; i < allPoints.size(); i += 2) {
      if (LineUtils.AngleBetweenPoints(main, allPoints.get(i), allPoints.get(i + 1))
          > biggestAngle) {
        biggestAngle = LineUtils.AngleBetweenPoints(main, allPoints.get(i), allPoints.get(i + 1));
        biggestAnglePoints.clear();
        biggestAnglePoints.add(allPoints.get(i));
        biggestAnglePoints.add(allPoints.get(i + 1));
      }
    }
    return biggestAnglePoints;
  }
}
