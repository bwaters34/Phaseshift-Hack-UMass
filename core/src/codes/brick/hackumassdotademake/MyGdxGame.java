package codes.brick.hackumassdotademake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
    collisions = new CollisionDetector(player1, player2);
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
    if(player1.isDead()){
      player1.increaseCurrentLives(-1);
      if(player2.isDead()){
        player2.increaseCurrentLives(-1);
      }
      player1.restoreHealth();
      player2.restoreHealth();
      player1.setPosition(player1XPosition, player1YPosition);
      player2.setPosition(player2XPosition, player2YPosition);
    } else if(player2.isDead()){
      player2.increaseCurrentLives(-1);
      player1.restoreHealth();
      player2.restoreHealth();
      player1.setPosition(player1XPosition, player1YPosition);
      player2.setPosition(player2XPosition, player2YPosition);
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
