package codes.brick.hackumassdotademake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Orb extends Sprite{
  public static final int DAMAGE = 40;
  public boolean harmful = true;
  private Puck puck;
  private int velocity = 20;

  public Orb(Puck puck, String textureName){
    super(new Texture(textureName));
    this.puck = puck;
    this.setPosition(puck.getX(), puck.getY());
    if(puck.isFlipX()){
      velocity *= - 1;
    }
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
