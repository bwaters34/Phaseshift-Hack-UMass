package codes.brick.hackumassdotademake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
  private SpriteBatch batch;
  private Sprite background;
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

  private BitmapFont font;

  @Override
  public void create () {
    batch = new SpriteBatch();
    viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    background = new Sprite(new Texture("bgfinal.png"));
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
    // FONT
    FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("PXSansRegular.ttf"));
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = 32;
    font = generator.generateFont(parameter);
    generator.dispose();
    font.setColor(Color.WHITE);
  }

  public void assignControllers() {
    if (Controllers.getControllers().size == 0) {
      return;
    }
    player1.controller = Controllers.getControllers().get(0);
  }

  @Override
  public void render () {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    // draw bg
    background.draw(batch);
    font.setColor(Color.BLUE);
    font.draw(batch, "HEALTH: " + player1.getHealth(), 0, VIEWPORT_HEIGHT - 10);
    font.draw(batch, "LIVES: " + player1.getLives(), 0, VIEWPORT_HEIGHT - 10 - font.getLineHeight());
    font.setColor(Color.PINK);
    font.draw(batch, "HEALTH: " + player2.getHealth(), 1024 - 125, VIEWPORT_HEIGHT - 10);
    font.draw(batch, "LIVES: " + player2.getLives(), 1024 - 125, VIEWPORT_HEIGHT - 10 - font.getLineHeight());
    player1.draw(batch);
    player2.draw(batch);
    player1.drawOrb(batch);
    player2.drawOrb(batch);
    batch.end();
    player1.update();
    player2.update();
    collisions.detectCollisions();
    //TODO RICHARD DO THIS
    if (player1.isDead() || player2.isDead()) {
      if (player1.isDead()) {
        player1.increaseCurrentLives(-1);
      }
      if (player2.isDead()) {
        player2.increaseCurrentLives(-1);
      }
      player1.restoreHealth();
      player2.restoreHealth();
      player1.setPosition(player1XPosition, player1YPosition);
      player2.setPosition(player2XPosition, player2YPosition);
      player1.reset();
      player2.reset();
      Hero.deathSound.play();
      if (!player1.hasLivesLeft() || !player2.hasLivesLeft()) {
        System.out.println("Player 1: had " + player1.getLives());
        System.out.println("Player 2: had " + player2.getLives());
        String winner;
        if (player1.getLives() > player2.getLives()) {
          winner = "Player 1";
        } else if (player1.getLives() < player2.getLives()) {
          winner = "Player 2";
        } else {
          winner = "Neither??";
        }
        System.out.println("The winner was: " + winner);
        System.exit(0);
      }
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
