package field;

import config.Config;
import entity.Enemy;
import entity.Goal;
import entity.Block;
import entity.Character;
import entity.EmptyBlock;
import entity.CharacterBlock;
import entity.Wall;
import enums.Direction;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;


public class Field {

  public Block[][] fieldBase;
  public Character character;
  private Line crosshair;
  private List<Line> lines;
  public final List<Polygon> polygons;
  public Goal goal;
  public final List<Enemy> enemies;

  public Field() {
    this.fieldBase = this.GenerateEmptyField();
    this.character = new Character();
    this.crosshair = new Line(0,0,0,0);
    this.lines = new ArrayList<>();
    this.polygons = new ArrayList<>();
    this.enemies = new ArrayList<>();
    SpawnRandomGoal();
    SpawnRandomEnemies();
  }

  public Block[][] GenerateEmptyField() {
    Block[][] field = new Block[Math.round(Config.HEIGHT)][Math.round(Config.WIDTH)];
    for (int i = 0; i < Config.WIDTH; i++) {
      for (int j = 0; j < Config.HEIGHT; j++) {
        field[j][i] = new EmptyBlock(i, j);
      }
    }
    for (int i = 0; i < Config.WIDTH; i++) {
      for (int j = 0; j < Config.HEIGHT; j++) {
        if (i == 0 || i == Config.WIDTH - 1) {
          field[j][i] = new Wall(i, j);
        }
        if (j == 0 || j == Config.HEIGHT - 1) {
          field[j][i] = new Wall(i, j);
        }
      }
    }
    return field;
  }

  private void SpawnRandomEnemies() {
    Enemy enemy = new Enemy(5.f, 5.f);
    this.enemies.add(enemy);
    this.fieldBase[5][5] = enemy.getEnemyHead();
  }

  private void SpawnRandomGoal() {
    this.goal = new Goal(8.f, 8.f);
    this.fieldBase[8][8] = this.goal;
  }

  public boolean CheckCollision(Direction newDirection) {
    CharacterBlock head = character.getCharacterHead();
    if (newDirection == Direction.N) {
      return fieldBase[(int) head.getBlockY() - 1][(int) head.getBlockX()].isSolid();
    }
    if (newDirection == Direction.S) {
      return fieldBase[(int) head.getBlockY() + 1][(int) head.getBlockX()].isSolid();
    }
    if (newDirection == Direction.E) {
      return fieldBase[(int) head.getBlockY()][(int) head.getBlockX() + 1].isSolid();
    }
    if (newDirection == Direction.W) {
      return fieldBase[(int) head.getBlockY()][(int) head.getBlockX() - 1].isSolid();
    }
    return false;
  }

  public Character getCharacter() {
    return character;
  }

  public void setLines(List<Line> lines) {
    this.lines = lines;
  }
}
