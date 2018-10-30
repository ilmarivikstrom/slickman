package util;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;

public class LineUtils {

  private static double AngleBetweenLines(Line ln1, Line ln2) {
    double ln1Dx = ln1.getX2() - ln1.getX1();
    double ln1Dy = ln1.getY2() - ln1.getY1();
    double ln2Dx = ln2.getX2() - ln2.getX1();
    double ln2Dy = ln2.getY2() - ln2.getY1();
    double dotProduct = ln1Dx * ln2Dx + ln1Dy * ln2Dy;
    double ln1Length = Math.sqrt(ln1Dx*ln1Dx + ln1Dy*ln1Dy);
    double ln2Length = Math.sqrt(ln2Dx*ln2Dx + ln2Dy*ln2Dy);
    double cosTheta = dotProduct / (ln1Length * ln2Length);
    double desiredAngle = Math.acos(cosTheta);

    return desiredAngle;
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
}
