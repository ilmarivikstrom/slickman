package input;

import config.Config;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputHandler {

  public static UserKeyboardInput GetUserKeyboardInput(GameContainer gc) {
    Input input = gc.getInput();
    input.disableKeyRepeat();
    if (input.isKeyDown(Keyboard.KEY_W)) {
      return UserKeyboardInput.UP;
    }
    if (input.isKeyDown(Keyboard.KEY_A)) {
      return UserKeyboardInput.LEFT;
    }
    if (input.isKeyDown(Keyboard.KEY_S)) {
      return UserKeyboardInput.DOWN;
    }
    if (input.isKeyDown(Keyboard.KEY_D)) {
      return UserKeyboardInput.RIGHT;
    }
    if (input.isKeyPressed(Keyboard.KEY_P)) {
      return UserKeyboardInput.PAUSE;
    }
    if (input.isKeyPressed(Keyboard.KEY_Q)) {
      return UserKeyboardInput.QUIT;
    }
    if (input.isKeyPressed(Keyboard.KEY_I)) {
      return UserKeyboardInput.KEY_I;
    }
    if (input.isKeyPressed(Keyboard.KEY_C)) {
      return UserKeyboardInput.KEY_C;
    }
    if (input.isKeyPressed(Keyboard.KEY_T)) {
      return UserKeyboardInput.KEY_T;
    }
    if (input.isKeyPressed(Keyboard.KEY_L)) {
      return UserKeyboardInput.KEY_L;
    }
    if (input.isKeyPressed(Keyboard.KEY_F)) {
      return UserKeyboardInput.KEY_F;
    }

    return UserKeyboardInput.NONE;
  }

  public static UserMouseInput GetMouseLeftClickCoordinates(GameContainer gc) {
    Input input = gc.getInput();
    UserMouseInput userMouseInput = new UserMouseInput();
    if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || input
        .isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
      float x = (float) input.getMouseX();
      float y = (float) input.getMouseY();
      if (x > Config.WIDTH * Config.TILE_SIZE) {
        x = Config.WIDTH * Config.TILE_SIZE;
      }
      if (y > Config.HEIGHT * Config.TILE_SIZE - 1) {
        y = Config.HEIGHT * Config.TILE_SIZE - 1;
      }
      userMouseInput.setBlockX((int) (x / Config.TILE_SIZE));
      userMouseInput.setBlockY((int) (y / Config.TILE_SIZE));
    }
    return userMouseInput;
  }

  public static UserMouseInput GetMouseRightClickCoordinates(GameContainer gc) {
    Input input = gc.getInput();
    UserMouseInput userMouseInput = new UserMouseInput();
    if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) || input
        .isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
      float x = (float) input.getMouseX();
      float y = (float) input.getMouseY();
      userMouseInput.setBlockX((int) (x / Config.TILE_SIZE));
      userMouseInput.setBlockY((int) (y / Config.TILE_SIZE));
    }
    return userMouseInput;
  }
}
