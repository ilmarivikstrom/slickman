package saving;

import config.Config;
import entity.Block;
import entity.EmptyBlock;
import entity.Obstacle;
import entity.Wall;
import enums.BlockType;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SaveGame {

  public static void Save(Block[][] field) throws FileNotFoundException {
    String contents = "";
    for (int j = 0; j < field.length; j++) {
      for (int i = 0; i < field[j].length; i++) {
        if (field[j][i].getBlockType() == BlockType.WALL) {
          contents += "1";
        } else if (field[j][i].getBlockType() == BlockType.OBSTACLE) {
          contents += "2";
        } else {
          contents += "0";
        }
      }
      contents += "\n";
    }
    PrintWriter out = new PrintWriter("savegame.txt");
    out.println(contents);
    out.close();
  }

  public static Block[][] Load() throws FileNotFoundException, IOException {
    Block[][] field = new Block[Math.round(Config.HEIGHT)][Math.round(Config.WIDTH)];
    InputStream is = new FileInputStream("savegame.txt");
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();
    while(line != null) {
      sb.append(line).append("\n");
      line = buf.readLine();
    }
    String fileAsString = sb.toString();
    String[] lines = fileAsString.split("\n");
    for (int j = 0; j < field.length; j++) {
      char[] charArray = lines[j].toCharArray();
      for (int i = 0; i < field[j].length; i++) {
        if (charArray[i] == '1') {
          field[j][i] = new Wall(i, j);
        } else if (charArray[i] == '2') {
          field[j][i] = new Obstacle(i, j);
        } else {
          field[j][i] = new EmptyBlock(i, j);
        }
      }
    }
    return field;
  }
}
