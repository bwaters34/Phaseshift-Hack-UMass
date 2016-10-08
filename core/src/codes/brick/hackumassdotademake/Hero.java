package codes.brick.hackumassdotademake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Hero extends Sprite {
  private int maxHealth;
  private int health;
  private int spellTimer1;
  private int spellTimer2;

  public static final int SIZE = 64;

  private Direction direction;

  public Direction getDirection() {
    return direction;
  }
  
  public Hero(int maxHealth, String textureName) {
    super(new Texture(textureName));
    this.maxHealth = maxHealth;
    restoreHealth();
    this.setSize(SIZE, SIZE);
  }

  public void restoreHealth() {
    health = maxHealth;
  }

  public void damage(int damage) {
    health -= damage;
    if (health < 0) {
      die();
    }
  }

  public void upSignal() {
    direction = Direction.UP;
  }

  public void rightSignal() {
    direction = Direction.RIGHT;
  }

  public void leftSignal() {
    direction = Direction.LEFT;
  }

  private void die() {
    // stub
  }

  public void move() {
    if (direction == null) {
      return;
    }
    switch (direction) {
    case LEFT:
      this.setPosition(getX() - 10, getY());
      this.setFlip(true, false);
      break;
    case RIGHT:
      this.setPosition(getX() + 10, getY());
      this.setFlip(false, false);
      break;
    case UP:
      this.setPosition(getX(), getY() + 10);
      stopMotion();
      break;
    default:
      return;
    }
  }

  public abstract void useFirstSpell();

  public void update() {
    System.out.println(direction);
    move();
  }
  
  public void stopMotion() {
    direction = null;
  }
}
