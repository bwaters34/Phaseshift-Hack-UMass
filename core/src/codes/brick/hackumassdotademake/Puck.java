package codes.brick.hackumassdotademake;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Puck extends Hero {
  private Orb orb;

  public Puck() {
    super(100, "badlogic.jpg");
  }
  public void useOrb() {
    this.orb = new Orb(this,"orb.png");
  }

  @Override
  public void draw (Batch batch) {
    super.draw(batch);
    if(orb!= null){
      orb.draw(batch);
    }
  }
}
