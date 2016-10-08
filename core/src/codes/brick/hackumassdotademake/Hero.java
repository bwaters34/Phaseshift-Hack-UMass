package codes.brick.hackumassdotademake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Hero extends Sprite {
  private int maxHealth;
  private int health;
  private int spellTimer1;
  private int spellTimer2;

  static final int VERTEX_SIZE = 2 + 1 + 2;
  static final int SPRITE_SIZE = 4 * VERTEX_SIZE;

  private Direction direction;

  public Hero(int maxHealth, String textureName) {
    super(new Texture(textureName));
    this.maxHealth = maxHealth;
    restoreHealth();
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
      break;
    case RIGHT:
      this.setPosition(getX() + 10, getY());
      break;
    case UP:
      this.setPosition(getX(), getY() + 10);;
      break;
    default:
      return;
    }
  }

  public abstract void useFirstSpell();

  public void update() {
    System.out.println(direction);
    move();
    direction = null;
  }

}
