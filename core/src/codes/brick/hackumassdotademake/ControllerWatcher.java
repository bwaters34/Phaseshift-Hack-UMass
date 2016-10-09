package codes.brick.hackumassdotademake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class ControllerWatcher extends ControllerAdapter {
  private Hero player1;
  private Hero player2;

  public ControllerWatcher(Hero player1, Hero player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  @Override
  public boolean buttonDown(Controller controller, int buttonCode) {
    if (buttonCode == 7 && MyGdxGame.isGameFinished) {
      MyGdxGame.enterPressed = true;
      return true;
    }
    // TODO: configurable controls
    if (controller == player1.controller) {
      if (buttonCode == 0) {
        player1.upSignal();
      } else if (buttonCode == 4 || buttonCode == 3) {
        player1.useFirstSpell();
      } else if (buttonCode == 2) {
        player1.useSecondSpell();
      }
      return true;
    }

    if (buttonCode == 0) {
      player2.upSignal();
    } else if (buttonCode == 4 || buttonCode == 3) {
      player2.useFirstSpell();
    } else if (buttonCode == 2) {
      player2.useSecondSpell();
    }
    return true;
  }

  @Override
  public boolean axisMoved(Controller controller, int axisCode, float value) {
    // 0 == horizontal Lstick axis on window for xbox360
    if (axisCode != 0) {
      return true;
    }

    if (controller == player1.controller) {
      if (value < 0) {
        player1.leftSignal();
      } else if (value > 0) {
        player1.rightSignal();
      } else {
        player1.stopMotion();
      }
      return true;
    }

    if (value < 0) {
      player2.leftSignal();
    } else if (value > 0) {
      player2.rightSignal();
    } else {
      player2.stopMotion();
    }

    return true;
  }
}
