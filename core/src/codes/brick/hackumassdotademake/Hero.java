package codes.brick.hackumassdotademake;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Hero extends Sprite {
  public Controller controller;
  private int maxHealth;
  private int health;

  protected final int GROUND_Y_VALUE = 0;
  private final int JUMP_SPEED = 30;
  private final int JUMP_CHANGE = 2;
  protected int currentJumpSpeed = JUMP_SPEED;
  private boolean isDead = false;
  private final int MAX_LIVES = 3;
  private int currentLives = MAX_LIVES;

  public int getHealth() {
    return health;
  }

  protected enum State {
    STANDING, JUMPING, FALLING
  }
  protected State currentJumpState = State.STANDING;

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

  public boolean isDead(){
    return health <= 0;
  }

  public void increaseCurrentLives(int add){
    currentLives += add;
  }

  public boolean hasLivesLeft(){
    return currentLives > 0;
  }

  public int getLives(){
    return currentLives;
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
    default:
      return;
    }
  }

  public void jump() {
    if (currentJumpState == State.JUMPING)
    {
      this.setY(this.getY()+currentJumpSpeed);
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
      this.setY(this.getY()-currentJumpSpeed);
      currentJumpSpeed += JUMP_CHANGE;
      if (this.getY() <= GROUND_Y_VALUE)
      {
        this.setY(GROUND_Y_VALUE);
        //If we reached the original Y coordinate, we hit the ground. Mark the character as standing.
        //           ChangeState(Standing);
        currentJumpState = State.STANDING;
        currentJumpSpeed = JUMP_SPEED;

      }
    }

  }

  public abstract void useFirstSpell();
  public abstract void useSecondSpell();

  public void update() {
    move();
    jump();
  }

  public void stopMotion() {
    direction = null;
  }
}
