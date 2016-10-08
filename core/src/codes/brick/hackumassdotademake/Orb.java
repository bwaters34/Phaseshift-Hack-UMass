package codes.brick.hackumassdotademake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Orb extends Sprite{
  private Puck puck;

  public Orb(Puck puck, String textureName){
    super(new Texture(textureName));
    this.puck = puck;

  }


}
