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
    } else if (keycode == Keys.R){
      player1.useFirstSpell();
    } else if (keycode == Keys.F){
      player1.useSecondSpell();
    }
    //BEGIN PLAYER 2
     else if (keycode == Keys.UP) {
      player2.upSignal();
    } else if (keycode == Keys.LEFT) {
      player2.leftSignal();
    } else if (keycode == Keys.RIGHT) {
      player2.rightSignal();
    } else if (keycode == Keys.SPACE) {
      player2.useFirstSpell();
    } else if (keycode == Keys.M) {
      player2.useSecondSpell();
    }
    return true;
  }
  
  @Override
  public boolean keyUp(int keycode) {
    // TODO: configurable controls
    if (keycode == Keys.A && player1.getDirection() == Direction.LEFT) {
      player1.stopMotion();
    } else if (keycode == Keys.D && player1.getDirection() == Direction.RIGHT) {
      player1.stopMotion();
    }
    //BEGIN PLAYER 2
    else if (keycode == Keys.LEFT && player2.getDirection() == Direction.LEFT) {
      player2.stopMotion();
    } else if (keycode == Keys.RIGHT && player2.getDirection() == Direction.RIGHT) {
      player2.stopMotion();
    }
    return true;
  }
}