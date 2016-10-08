package codes.brick.hackumassdotademake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Hero extends Sprite {
  private int maxHealth;
  private int health;

  private final int GROUND_Y_VALUE = 0;
  private final int JUMP_SPEED = 50;
  private final int JUMP_CHANGE = 1;
  private int currentJumpSpeed = JUMP_SPEED;

  private enum State {
    STANDING, JUMPING, FALLING
  }
  private State currentJumpState = State.STANDING;


  private Direction direction;

  public Hero(int maxHealth, String textureName) {
    super(new Texture(textureName));
    this.maxHealth = maxHealth;
    restoreHealth();
  }

  public void restoreHealth() {
    health = maxHealth;
  }

  private void damage(int damage) {
    health -= damage;
    if (health < 0) {
      die();
    }
  }

  public void upSignal(){
    if((int)this.getY() == GROUND_Y_VALUE){
      System.out.println("jumping");
      System.out.println(this.getY());
      currentJumpState = State.JUMPING;
    }
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
    default:
      return;
    }
  }

  public void jump() {
    if (currentJumpState == State.JUMPING)
    {
      //We're ascending. Decrease the Y coordinate of the sprite by the speed.
      //      DecreaseYCoordinateBy(Speed);
      this.setY(this.getY()+currentJumpSpeed);
      //      DecreaseByValue(Speed, Value); //The character needs to jump smoothly, so the speed should decrease as he ascends.
      currentJumpSpeed -= JUMP_CHANGE;
      if (currentJumpSpeed <= 0)
      {
        currentJumpState = State.FALLING; //If speed is <= 0, then the character should fall down.
        currentJumpSpeed = 0;
        System.out.println("BEGIN FALLING");
      }
    }
    else if (currentJumpState == State.FALLING)
    {
      //We're descending. Increase the Y coordinate by the speed (at first, it's 0).
      //      IncreaseYCoordinateBy(Speed);
      this.setY(this.getY()-currentJumpSpeed);
      //      IncreaseByValue(Speed, Value); //Increase the speed, so the character falls gradually faster.
      currentJumpSpeed += JUMP_CHANGE;
      if (this.getY() == GROUND_Y_VALUE)
      {
        //If we reached the original Y coordinate, we hit the ground. Mark the character as standing.
        //           ChangeState(Standing);
        currentJumpState = State.STANDING;
        currentJumpSpeed = JUMP_SPEED;
        //           CurrentYCoordinate = OriginalYCoordinate;

      }
    }

  }

  public abstract void useFirstSpell();

  public void update() {
    System.out.println(direction);
    move();
    jump();
    direction = null;

  }

}
