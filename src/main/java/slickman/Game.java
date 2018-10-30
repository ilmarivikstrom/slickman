package slickman;

import config.Config;
import entity.Block;
import entity.BlockCreator;
import entity.EmptyBlock;
import entity.Enemy;
import entity.Obstacle;
import enums.BlockType;
import enums.Direction;
import field.Field;
import input.InputHandler;
import input.UserKeyboardInput;
import input.UserMouseInput;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import saving.SaveGame;
import util.DistanceCalculator;
import util.LineUtils;

class Game extends BasicGame {

  private long updateTime;
  private float time;
  private float interval;
  private final Field field;
  private boolean inEditMode;
  private TrueTypeFont pauseFont;
  private TrueTypeFont distanceFont;

  private Game() {
    super("SlickMan!");
    new Config();
    field = new Field();
    this.inEditMode = false;
  }

  public static void main(String[] args) {
    String OS = System.getProperty("os.name").toLowerCase();

    System.setProperty("org.lwjgl.librarypath",
        new File("native/" + OS).getAbsolutePath());
    try {
      AppGameContainer appgc;
      appgc = new AppGameContainer(new Game());
      appgc.setDisplayMode(Math.round(Config.WIDTH * Config.TILE_SIZE),
          Math.round(Config.HEIGHT * Config.TILE_SIZE), false);
      appgc.start();
    } catch (SlickException ex) {
      Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void init(GameContainer gc) {
    Font font16 = new Font("Verdana", Font.BOLD, 10);
    Font font12 = new Font("Verdana", Font.BOLD, 8);
    this.pauseFont = new TrueTypeFont(font16, true);
    this.distanceFont = new TrueTypeFont(font12, true);
    updateTime = System.nanoTime();
    time = 0;
    interval = 1.0f / 20.0f;
  }

  @Override
  public void update(GameContainer gc, int i) {
    boolean moveCommandGiven = false;
    UserKeyboardInput keyboardInput = InputHandler.GetUserKeyboardInput(gc);
    UserMouseInput leftMouseInput = InputHandler.GetMouseLeftClickCoordinates(gc);
    UserMouseInput rightMouseInput = InputHandler.GetMouseRightClickCoordinates(gc);
    Direction newDirection = Direction.NONE;
    if (keyboardInput != UserKeyboardInput.NONE) {
      if (keyboardInput == UserKeyboardInput.UP) {
        newDirection = Direction.N;
        moveCommandGiven = true;
      } else if (keyboardInput == UserKeyboardInput.DOWN) {
        newDirection = Direction.S;
        moveCommandGiven = true;
      } else if (keyboardInput == UserKeyboardInput.LEFT) {
        newDirection = Direction.W;
        moveCommandGiven = true;
      } else if (keyboardInput == UserKeyboardInput.RIGHT) {
        newDirection = Direction.E;
        moveCommandGiven = true;
      } else if (keyboardInput == UserKeyboardInput.PAUSE) {
        this.inEditMode = !this.inEditMode;
      } else if (keyboardInput == UserKeyboardInput.KEY_C) {
        this.field.fieldBase = this.field.GenerateEmptyField();
      } else if (keyboardInput == UserKeyboardInput.KEY_T) {
        try {
          SaveGame.Save(this.field.fieldBase);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      } else if (keyboardInput == UserKeyboardInput.KEY_L) {
        try {
          this.field.fieldBase = SaveGame.Load();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else if (keyboardInput == UserKeyboardInput.KEY_F) {
        this.field.character.setLightIsOn(!this.field.character.isLightIsOn());
      } else if (keyboardInput == UserKeyboardInput.QUIT) {
        System.exit(1);
      }
    }

    long currentTime = System.nanoTime();
    float dt = (currentTime - updateTime) * 0.000000001f;
    time += dt;
    while (time >= interval) {

      // If not in Edit Mode, move character.
      if (!this.inEditMode) {
        boolean collided = field.CheckCollision(newDirection);

        if (!collided && moveCommandGiven) {
          this.field.MoveCharacter(newDirection);
        }

        // Get corner points of all obstacle blocks.
        List<Point> points = new ArrayList<>();
        for (Block[] blockRow : this.field.fieldBase) {
          for (Block block : blockRow) {
            if (block.getBlockType() == BlockType.OBSTACLE) {
              points.addAll(block.getCornersAsPoints());
            }
          }
        }

        // Get lines from corners to the character.
        List<Line> lines = new ArrayList<>();
        for (Point point : points) {
          lines.add(new Line(this.field.character.getCharacterHead().getCenterX(),
              this.field.character.getCharacterHead().getCenterY(), point.getX(), point.getY()));
        }

        // Find the largest angles and store them for drawing.
        List<Line> largestAngleLines = new ArrayList<>();
        List<Line> temp = new ArrayList<>();
        for (int index = 0; index < lines.size(); index += 4) {
          temp.add(lines.get(index));
          temp.add(lines.get(index + 1));
          temp.add(lines.get(index + 2));
          temp.add(lines.get(index + 3));
          largestAngleLines.addAll(LineUtils.GetLargestAngleLines(temp));
          temp.clear();
        }

        List<Line> shadeLines = new ArrayList<>();
        for (Line line : largestAngleLines) {
          float dx = 80 * (line.getMaxX() - line.getMinX());
          float dy = 80 * (line.getMaxY() - line.getMinY());
          if (this.field.getCharacter().getCharacterHead().getCenterX() < line.getEnd().x
              && this.field.getCharacter().getCharacterHead().getCenterY() > line.getEnd().y) {
            dx = -dx;
            dy = -dy;
          }
          if (this.field.getCharacter().getCharacterHead().getCenterX() > line.getEnd().x
              && this.field.getCharacter().getCharacterHead().getCenterY() > line.getEnd().y) {
            dx = -dx;
            dy = -dy;
          }
          if (this.field.getCharacter().getCharacterHead().getCenterX() > line.getEnd().x
              && this.field.getCharacter().getCharacterHead().getCenterY() < line.getEnd().y) {
            dx = -dx;
          }
          if (this.field.getCharacter().getCharacterHead().getCenterX() < line.getEnd().x
              && this.field.getCharacter().getCharacterHead().getCenterY() > line.getEnd().y) {
            dx = -dx;
          }
          Line newLine = new Line(line.getEnd().x, line.getEnd().y, dx, dy, true);
          shadeLines.add(newLine);
        }

        this.field.polygons.clear();
        for (int i1 = 0; i1 < shadeLines.size(); i1 += 2) {
          Polygon polygon = new Polygon();
          polygon.addPoint(shadeLines.get(i1).getStart().x, shadeLines.get(i1).getStart().y);
          polygon.addPoint(shadeLines.get(i1).getEnd().x, shadeLines.get(i1).getEnd().y);
          polygon.addPoint(shadeLines.get(i1 + 1).getEnd().x, shadeLines.get(i1 + 1).getEnd().y);
          polygon
              .addPoint(shadeLines.get(i1 + 1).getStart().x, shadeLines.get(i1 + 1).getStart().y);
          this.field.polygons.add(polygon);
        }

        this.field.setLines(shadeLines);
      }

      // If in Edit Mode, do all the stuff!
      if (this.inEditMode) {
        interval = 1.0f / 100.0f;
        if (leftMouseInput.getBlockX() != -1) {
          AddMouseBlock(leftMouseInput, BlockType.OBSTACLE);
        }
        if (rightMouseInput.getBlockX() != -1) {
          AddMouseBlock(rightMouseInput, BlockType.EMPTY);
        }
        if (keyboardInput == UserKeyboardInput.KEY_I) {
          GenerateRandomMap();
        }
      } else {
        interval = 1.0f / 20.f;
      }
      time -= interval;
    }
    updateTime = currentTime;
  }

  @Override
  public void render(GameContainer gc, Graphics g) {
    // Begin by drawing white background.
    g.setBackground(new Color(255, 255, 255, 255));

    // Dark background.
    for (Block[] blockRow : field.fieldBase) {
      for (Block block : blockRow) {
        g.setColor(Config.EMPTY_COLOR);
        g.fill(block);
      }
    }

    Color origColor;
    Color newColor;

    // If the light is on, draw...
    if (this.field.character.isLightIsOn()) {
      // ... goal
      if (!this.inEditMode) {
        origColor = this.field.goal.getFillColor();
        newColor = new Color(origColor.r, origColor.g, origColor.b,
            origColor.a - (float) 1.75 * origColor.a * (
                DistanceCalculator.Euclidean(this.field.character.getCharacterHead(), this.field.goal)
                    / (Config.TILE_SIZE * Config.WIDTH)));

        g.setColor(newColor);
        g.fill(this.field.goal);

        // ... enemies
        for (Enemy enemy : this.field.enemies) {
          origColor = enemy.getEnemyHead().getFillColor();
          newColor = new Color(origColor.r, origColor.g, origColor.b,
              origColor.a - (float) 1.75 * origColor.a * (DistanceCalculator
                  .Euclidean(this.field.character.getCharacterHead(), enemy.getEnemyHead()) / (
                  Config.TILE_SIZE * Config.WIDTH)));
          g.setColor(newColor);
          g.fill(enemy.getEnemyHead());
        }
      }

      // ... light
      if (!this.inEditMode) {
        origColor = Config.EMPTY_BRIGHT_COLOR;
        float[][] blockDistances = this.field.GetBlockDistances();
        for (int j = 0; j < blockDistances.length; j++) {
          for (int i = 0; i < blockDistances[j].length; i++) {
            if (this.field.fieldBase[j][i].getBlockType() == BlockType.EMPTY) {
              newColor = new Color(origColor.r, origColor.g, origColor.b,
                  origColor.a - (float) 1.75 * origColor.a * (blockDistances[j][i] / (Config.TILE_SIZE
                      * Config.WIDTH)));
              g.setColor(newColor);
              g.fill(this.field.fieldBase[j][i]);
              Color origLineColor = this.field.fieldBase[j][i].getLineColor();
              Color newLineColor = new Color(origLineColor.r, origLineColor.g, origLineColor.b, origLineColor.a - (float) 1 * origLineColor.a * (blockDistances[j][i] / (Config.TILE_SIZE
                  * Config.WIDTH)));
              g.setColor(newLineColor);
              g.draw(this.field.fieldBase[j][i]);
            }
          }
        }
      }

      // ... and visibility polygons.
      if (!this.inEditMode) {
        g.setColor(Config.EMPTY_COLOR);
        for (Polygon polygon : this.field.polygons) {
          g.fill(polygon);
        }
      }
    }

    // Draw character in any case.
    g.setColor(this.field.character.getCharacterHead().getFillColor());
    g.fill(this.field.character.getCharacterHead());
    g.setColor(Config.GLOBAL_LINE_COLOR);
    g.draw(this.field.character.getCharacterHead());

    // Draw the field and walls so that
    for (Block[] blockRow : field.fieldBase) {
      for (Block block : blockRow) {
        if (block.getBlockType() == BlockType.OBSTACLE || block.getBlockType() == BlockType.WALL) {
          g.setColor(block.getFillColor());
          g.fill(block);
        }
      }
    }

    // Draw some distance info
    DrawDistanceInfo();

    // Draw some info if in Edit Mode
    if (this.inEditMode) {
      DrawEditModeOverlay(g);
      DrawEditModeInfo();
    }
  }

  private void DrawEditModeInfo() {
    pauseFont.drawString(Config.WIDTH * Config.TILE_SIZE / 2 - pauseFont.getWidth("EDIT MODE") / 2,
        Config.TILE_SIZE,
        "EDIT MODE", Color.white);
    pauseFont.drawString(Config.WIDTH * Config.TILE_SIZE / 2
            - pauseFont.getWidth("Try drawing obstacles with the mouse!") / 2, Config.TILE_SIZE * 2,
        "Try drawing obstacles with the mouse!", Color.white);
  }

  private void DrawEditModeOverlay(Graphics g) {
    g.setColor(Config.EDITMODE_OVERLAY_COLOR);
    g.fillRect(0.f, 0.f, Config.WIDTH * Config.TILE_SIZE, Config.HEIGHT * Config.TILE_SIZE);
  }

  private void DrawDistanceInfo() {
    String manhattan = "Manh: " + DistanceCalculator
        .ManhattanAsString(this.field.character.getCharacterHead().getBlockX(),
            this.field.character.getCharacterHead().getBlockY(), this.field.goal.getBlockX(),
            this.field.goal.getBlockY());
    String euclidean = "Eucl: " + DistanceCalculator
        .EuclideanAsString(this.field.character.getCharacterHead().getBlockX(),
            this.field.character.getCharacterHead().getBlockY(), this.field.goal.getBlockX(),
            this.field.goal.getBlockY());
    distanceFont
        .drawString(Config.WIDTH * Config.TILE_SIZE - 50, Config.HEIGHT * Config.TILE_SIZE - 60,
            manhattan);
    distanceFont
        .drawString(Config.WIDTH * Config.TILE_SIZE - 50, Config.HEIGHT * Config.TILE_SIZE - 40,
            euclidean);
  }

  private void AddMouseBlock(UserMouseInput mouseInput, BlockType blockType) {
    switch (blockType) {
      case OBSTACLE:
        Block clickedEmptyBlock = BlockCreator.CreateReplaceBlock(
            this.field.fieldBase[mouseInput.getBlockY()][mouseInput.getBlockX()], Obstacle.class);
        this.field.fieldBase[mouseInput.getBlockY()][mouseInput.getBlockX()] = clickedEmptyBlock;
        return;
      case EMPTY:
        Block clickedWallBlock = BlockCreator.CreateReplaceBlock(
            this.field.fieldBase[mouseInput.getBlockY()][mouseInput.getBlockX()],
            EmptyBlock.class);
        this.field.fieldBase[mouseInput.getBlockY()][mouseInput.getBlockX()] = clickedWallBlock;
    }
  }

  private void GenerateRandomMap() {
    Random random = new Random();
    Block[][] newField = this.field.GenerateEmptyField();
    for (int j = 0; j < this.field.fieldBase.length - 1; j++) {
      for (int i = 0; i < this.field.fieldBase[j].length - 1; i++) {
        if (j % (6 + random.nextInt(3)) == 0) {
          newField[j][i] = new Obstacle(i, j);
        } else if (i % (10 + random.nextInt(4)) == 0) {
          newField[j][i] = new Obstacle(i, j);
        } else {
          newField[j][i] = new EmptyBlock(i, j);
        }
      }
    }
    this.field.fieldBase = newField;
  }
}