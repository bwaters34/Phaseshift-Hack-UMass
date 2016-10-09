package codes.brick.hackumassdotademake;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Puck extends Hero {
  public static final int COLLISION_REBOUND = 50;
  private Orb orb;
  private int currentOrbCooldown = 0; //is it on cooldown?  Starts not on cooldowjn
  private int maxOrbCooldown = 180; //3 second cooldown
  private int currentPhaseShiftCooldown = 0;
  private int maxPhaseShiftCooldown = 240; // 4 second cooldown
  private int phaseShiftDuration = 0;
  private int maxPhaseShiftDuration = 60;
  private float previousY = 0;
  
  public Puck() {
    super(100, "badlogic.jpg");
  }

  @Override
  public void useFirstSpell(){
    if(currentOrbCooldown > 0 || phaseShiftDuration > 0){
      return;
    }
    orb = new Orb(this, "orb.png");
    currentOrbCooldown = maxOrbCooldown;
  }
  
  @Override
  public void useSecondSpell(){
    if(currentPhaseShiftCooldown > 0){
      return;
    }
    phaseShiftDuration = maxPhaseShiftDuration;
    currentPhaseShiftCooldown = maxPhaseShiftCooldown;
    previousY = this.getY();
    this.setPosition(this.getX(), 1000);
  }

  @Override
  public void draw (Batch batch) {
    super.draw(batch);
  }
  
  public void drawOrb (Batch batch) {
    if(orb!= null){
      orb.draw(batch);
    }    
  }

  @Override
  public void update() {
    if (this.getY() == 1000 && phaseShiftDuration == 0){
      this.setY(previousY);
    }
    if (phaseShiftDuration == 0) {
      super.update();
    }
    if(orb != null){
      orb.update();
    }
    if(currentOrbCooldown-- > 0){
      currentOrbCooldown -= 1;
    }
    if(currentPhaseShiftCooldown > 0){
      currentPhaseShiftCooldown -= 1;
    }
    if(phaseShiftDuration > 0) {
       phaseShiftDuration -= 1;
    }
  }
  
  public Orb getOrb() {
	  return orb;
  }
}
