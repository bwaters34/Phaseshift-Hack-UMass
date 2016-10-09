package codes.brick.hackumassdotademake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
  private BitmapFont font2;
  private Music backgroundMusic;
  private Music victoryMusic;

  private static final Color PUCK_BLUE = new Color(0,(120f/255f),(248f/255f), 1);
  private static final Color PUCK_RED = new Color(1,0,(122f/255f), 1);

  private static final Color PUCK_BLUE_T = new Color(0,(120f/255f),(248f/255f), 0.5f);
  private static final Color PUCK_RED_T = new Color(1,0,(122f/255f), 0.5f);

  private Color overlayColor;

  private ShapeRenderer sr;

  private int overlayTimer = 0;

  public static boolean isGameFinished = false;
  public static boolean enterPressed = false;

  private boolean victoryMusicCurrentlyPlaying = false;

  @Override
  public void create () {
    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Bach_in_G.ogg"));
    victoryMusic = Gdx.audio.newMusic(Gdx.files.internal("victoryfanfare.wav"));
    backgroundMusic.setLooping(true);
    backgroundMusic.setVolume(0.5f);
    backgroundMusic.play();
    batch = new SpriteBatch();
    viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    background = new Sprite(new Texture("bgfinal.png"));

    player1 = new Puck("puck");
    player1.setPosition(player1XPosition, player1YPosition);
    player2 = new Puck("redpuck");
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
    parameter.borderColor = Color.BLACK;
    parameter.borderWidth = 1;
    parameter.size = 32;
    font = generator.generateFont(parameter);
    font.setColor(Color.WHITE);
    sr = new ShapeRenderer();

    FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
    parameter2.borderColor = Color.BLACK;
    parameter2.borderWidth = 1;
    parameter2.size = 24;
    font2 = generator.generateFont(parameter2);
    font2.setColor(Color.YELLOW);
    generator.dispose();

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
    if(isGameFinished && enterPressed){
      player1.setPosition(player1XPosition, player1YPosition);
      player2.setPosition(player2XPosition, player2YPosition);
      player1.reset();
      player2.reset();
      player1.resetLives();
      player2.resetLives();
      victoryMusic.pause();
      backgroundMusic.play();
      batch.end();
      isGameFinished = false;
      enterPressed = false;
      overlayTimer = 0;
      victoryMusicCurrentlyPlaying = false;

    }
    else{

      if (!player1.hasLivesLeft() || !player2.hasLivesLeft()) {
        System.out.println("Player 1: had " + player1.getLives());
        System.out.println("Player 2: had " + player2.getLives());
        String winner;
        //      batch.flush();
        //      sr.begin(ShapeRenderer.ShapeType.Filled);
        //      sr.setColor(Color.BLACK.r,Color.BLACK.g,Color.BLACK.b,0.5F);
        //      sr.rect(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        //      sr.end();
        backgroundMusic.pause();
        victoryMusic.setLooping(false);
        victoryMusic.setVolume(1);
        if(!victoryMusicCurrentlyPlaying){
            victoryMusic.play();
            victoryMusicCurrentlyPlaying = true;
        }
        if (player1.getLives() > player2.getLives()) {
          winner = "PLAYER 1";
        } else if (player1.getLives() < player2.getLives()) {
          winner = "PLAYER 2";
        } else {
          winner = "YOU TIED!";
        }
        font.setColor(Color.YELLOW);
        font.draw(batch, "THE WINNER IS: " + winner, VIEWPORT_WIDTH/2 - 150, VIEWPORT_HEIGHT/2);
        font.draw(batch, "PRESS ENTER TO RESTART", VIEWPORT_WIDTH/2 - 155, VIEWPORT_HEIGHT/2 - font.getLineHeight());
        font2.draw(batch, "CREATED BY THE BRICK.CODES TEAM:", 0, 0+ 50);
        font2.draw(batch, "MATT DIBELLO, RICHARD MCCORMACK, DAVID OSGOOD, AND BRENNAN WATERS", 0, 25);
        System.out.println("The winner was: " + winner);
        isGameFinished = true;
        //      Gdx.app.exit();
        batch.end();
      }
      else{
        background.draw(batch);
        font.setColor(PUCK_BLUE);
        font.draw(batch, "HEALTH: " + player1.getHealth(), 60, VIEWPORT_HEIGHT - 10);
        font.draw(batch, "LIVES: " + player1.getLives(), 60, VIEWPORT_HEIGHT - 10 - font.getLineHeight());
        font.setColor(PUCK_RED);
        font.draw(batch, "HEALTH: " + player2.getHealth(), VIEWPORT_WIDTH - 60 - 130, VIEWPORT_HEIGHT - 10);
        font.draw(batch, "LIVES: " + player2.getLives(), VIEWPORT_WIDTH - 60 - 130, VIEWPORT_HEIGHT - 10 - font.getLineHeight());
        player1.draw(batch);
        player2.draw(batch);
        player1.drawOrb(batch);
        player2.drawOrb(batch);
        if(overlayTimer > 0) {
          batch.flush();
          sr.begin(ShapeRenderer.ShapeType.Filled);
          sr.setColor(overlayColor);
          sr.rect(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
          sr.end();
          overlayTimer--;
        }
        batch.end();
        player1.update();
        player2.update();
        collisions.detectCollisions();
        if (player1.isDead() || player2.isDead()) {
          if (player1.isDead() && player2.isDead()) {
            player1.increaseCurrentLives(-1);
            player2.increaseCurrentLives(-1);
            overlayColor = new Color(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, 0.5f);
          } else if (player1.isDead()) {
            player1.increaseCurrentLives(-1);
            overlayColor = PUCK_RED_T;
          } else if (player2.isDead()) {
            player2.increaseCurrentLives(-1);
            overlayColor = PUCK_BLUE_T;
          }
          overlayTimer = 120;
          player1.restoreHealth();
          player2.restoreHealth();
          player1.setPosition(player1XPosition, player1YPosition);
          player2.setPosition(player2XPosition, player2YPosition);
          player1.reset();
          player2.reset();
          Hero.deathSound.play();
        }
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

  boolean isGameFinished () {
    return isGameFinished;
  }

}
