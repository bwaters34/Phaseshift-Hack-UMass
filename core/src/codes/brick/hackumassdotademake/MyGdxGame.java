package codes.brick.hackumassdotademake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
  private SpriteBatch batch;
  private Puck player1;
  private Puck player2;
  private CollisionDetector collisions;

  public static final int VIEWPORT_WIDTH = 1024;
  public static final int VIEWPORT_HEIGHT = 256;

  private float player1XPosition = 0;
  private float player1YPosition = 0;

  private float player2XPosition;
  private float player2YPosition = 0;

  private InputWatcher iw;
  private ControllerWatcher cw;

  private Viewport viewport;


  @Override
  public void create () {
    batch = new SpriteBatch();
    viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    player1 = new Puck("puck.png");
    player1.setPosition(player1XPosition, player1YPosition);
    player2 = new Puck("redpuck.png");
    player2.flip(true, false);
    player2XPosition = VIEWPORT_WIDTH - player2.getWidth();
    player2.setPosition(player2XPosition, player2YPosition);
    iw = new InputWatcher(player1, player2);
    Gdx.input.setInputProcessor(iw);
    assignControllers();
    cw = new ControllerWatcher(player1, player2);
    Controllers.addListener(cw);
    collisions = new CollisionDetector(player1, player2);
  }

  public void assignControllers() {
    player1.controller = Controllers.getControllers().get(0);
  }

  @Override
  public void render () {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    // draw bg
    player1.draw(batch);
    player2.draw(batch);
    player1.drawOrb(batch);
    player2.drawOrb(batch);
    batch.end();
    player1.update();
    player2.update();
    collisions.detectCollisions();
    //TODO RICHARD DO THIS
    if(player1.isDead()){
      player1.increaseCurrentLives(-1);
      if(player2.isDead()){
        player2.increaseCurrentLives(-1);
      }
      player1.restoreHealth();
      player2.restoreHealth();
      player1.setPosition(player1XPosition, player1YPosition);
      player2.setPosition(player2XPosition, player2YPosition);
      player1.reset();
      player2.reset();
    } else if(player2.isDead()){
      player2.increaseCurrentLives(-1);
      player1.restoreHealth();
      player2.restoreHealth();
      player1.setPosition(player1XPosition, player1YPosition);
      player2.setPosition(player2XPosition, player2YPosition);
      player1.reset();
      player2.reset();
    }
    if(!player1.hasLivesLeft() || !player2.hasLivesLeft()){
      System.out.println("Player 1: had " + player1.getLives());
      System.out.println("Player 2: had " + player2.getLives());
      String winner = "";
      if(player1.getLives() > player2.getLives()){
        winner = "Player 1";
      } else if(player1.getLives() < player2.getLives()){
        winner = "Player 2";
      } else{
        winner = "Neither??";
      }
      System.out.println("The winner was: " + winner);
      System.exit(0);
    }
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

  @Override
  public void dispose () {
    batch.dispose();
  }
}
