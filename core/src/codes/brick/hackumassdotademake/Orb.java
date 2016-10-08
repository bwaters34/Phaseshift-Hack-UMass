package codes.brick.hackumassdotademake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Orb extends Sprite{
  private Puck puck;
  private boolean facingRight = true;
  private int velocity = 20;

  public Orb(Puck puck, String textureName){
    super(new Texture(textureName));
    this.puck = puck;
    this.setPosition(puck.getX(), puck.getY());
    //TODO: get facing direction from Puck
    //currently bullets only move to the right
  }

  public void move(){  //TODO:  Move different directions depending how Puck is facing
    this.setPosition(getX() + velocity, getY()); //moves it to the right
  }

  public void update() {
    System.out.println("ORB IS UPDATING");
    move();
  }
}
