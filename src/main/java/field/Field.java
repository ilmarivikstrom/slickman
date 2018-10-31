package field;

import config.Config;
import entity.Block;
import entity.Character;
import entity.CharacterBlock;
import entity.EmptyBlock;
import entity.Enemy;
import entity.Goal;
import entity.Wall;
import enums.Direction;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import util.DistanceCalculator;


public class Field {

  public Block[][] fieldBase;
  private float[][] blockDistances;
  public Character character;
  private List<Line> lines;
  public final List<Polygon> polygons;
  public Goal goal;
  public final List<Enemy> enemies;

  public Field() {
    this.GenerateEmptyField();
    this.blockDistances = new float[Math.round(Config.HEIGHT)][Math.round(Config.WIDTH)];
    this.character = new Character();
    this.fieldBase[(int)this.character.getCharacterHead().getBlockX()][(int)this.character.getCharacterHead().getBlockY()] = this.character.getCharacterHead();
    this.lines = new ArrayList<>();
    this.polygons = new ArrayList<>();
    this.enemies = new ArrayList<>();
    SpawnGoal();
    SpawnEnemy();
  }

  public void GenerateEmptyField() {
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
    this.fieldBase = field;
  }

  public void UpdateEnemiesAndGoalAndCharacter() {
    if (this.enemies != null) {
      for (Enemy enemy : this.enemies) {
        this.fieldBase[(int)enemy.getEnemyHead().getBlockY()][(int)enemy.getEnemyHead().getBlockX()] = enemy.getEnemyHead();
      }
    }
    if (this.goal != null) {
      this.fieldBase[(int)this.goal.getBlockY()][(int)this.goal.getBlockX()] = this.goal;
    }
    if (this.character != null) {
      this.fieldBase[(int)this.character.getCharacterHead().getBlockY()][(int)this.character.getCharacterHead().getBlockX()] = this.character.getCharacterHead();
    }
  }

  public float[][] GetBlockDistances() {
    for (int j = 0; j < this.fieldBase.length; j++) {
      for (int i = 0; i < this.fieldBase[j].length; i++) {
        blockDistances[j][i] = DistanceCalculator
            .Euclidean(character.getCharacterHead(), this.fieldBase[j][i]);
      }
    }
    return blockDistances;
  }

  private void SpawnEnemy() {
    Enemy enemy = new Enemy(8.f, 12.f);
    this.enemies.add(enemy);
    this.fieldBase[12][8] = enemy.getEnemyHead();
  }

  private void SpawnGoal() {
    this.goal = new Goal(8.f, 8.f);
    this.fieldBase[8][8] = this.goal;
  }

  public void MoveCharacter(Direction newDirection) {
    this.fieldBase[(int)this.character.getCharacterHead().getBlockY()][(int)this.character.getCharacterHead().getBlockX()] = new EmptyBlock(this.character.getCharacterHead().getBlockX(), this.character.getCharacterHead().getBlockY());
    this.character.Move(newDirection);
    this.fieldBase[(int)this.character.getCharacterHead().getBlockY()][(int)this.character.getCharacterHead().getBlockX()] = this.character.getCharacterHead();
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
