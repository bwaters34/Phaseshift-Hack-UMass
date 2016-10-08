package codes.brick.hackumassdotademake;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;


public class InputWatcher extends InputAdapter {
  private Hero player1;
  private Hero player2;

  public InputWatcher(Hero player1, Hero player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  @Override
  public boolean keyDown(int keycode) {
    // TODO: configurable controls
    if (keycode == Keys.W) {
      player1.upSignal();
    } else if (keycode == Keys.A) {
      player1.leftSignal();
    } else if (keycode == Keys.D) {
      player1.rightSignal();
    } else if (keycode == Keys.UP) {
      player2.upSignal();
    } else if (keycode == Keys.LEFT) {
      player2.leftSignal();
    } else if (keycode == Keys.RIGHT) {
      player2.rightSignal();
    }
    return true;
  }
}