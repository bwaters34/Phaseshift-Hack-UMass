package codes.brick.hackumassdotademake;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Puck extends Hero {
  public static final int COLLISION_REBOUND = 50;
  private Orb orb;
  private int currentOrbCooldown= 0; //is it on cooldown?  Starts not on cooldowjn
  private int maxOrbCooldown = 180; //3 second cooldown
  public Puck() {
    super(100, "badlogic.jpg");
  }

  @Override
  public void useFirstSpell(){
    if(currentOrbCooldown == 0){
      this.orb = new Orb(this,"orb.png");
      currentOrbCooldown = maxOrbCooldown;
    }
  }

  @Override
  public void draw (Batch batch) {
    super.draw(batch);
    if(orb!= null){
      orb.draw(batch);
    }
  }

  @Override
  public void update() {
    super.update();
    if(orb != null){
      orb.update();
    }
    if(currentOrbCooldown > 0){
      currentOrbCooldown -= 1;
    }
  }
  
  public Orb getOrb() {
	  return orb;
  }
}
