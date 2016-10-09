package codes.brick.hackumassdotademake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Orb extends Sprite{
  public static final int DAMAGE = 40;
  public static final int SIZE = Hero.SIZE/2;
  public boolean harmful = true;
  private int velocity = 20;

  public Orb(Puck puck, String textureName){
    super(new Texture(textureName));
    this.setPosition(puck.getX(), puck.getY() + puck.getHeight() / 2);
    if(puck.isFlipX()){
      velocity *= - 1;
    }
    this.setSize(SIZE, SIZE);
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
