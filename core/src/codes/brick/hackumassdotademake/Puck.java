package codes.brick.hackumassdotademake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

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
  private String textureName;

  private Texture[] currentAnimation;
  private Texture[] shootAnimation;
  private int animationIndex = 0;
  private Texture[] idleAnimation;
  private Texture[] hurtAnimation;

  public Puck(String textureName) {
    super(100, textureName + ".png");
    this.textureName = textureName;
    if (textureName.equals("puck")) {
      idleAnimation = new Texture[8];
      idleAnimation[0] = new Texture("idle1.png");
      idleAnimation[1] = new Texture("idle2.png");
      idleAnimation[2] = new Texture("idle3.png");
      idleAnimation[3] = new Texture("idle4.png");
      idleAnimation[4] = new Texture("idle5.png");
      idleAnimation[5] = new Texture("idle6.png");
      idleAnimation[6] = new Texture("idle7.png");
      idleAnimation[7] = new Texture("idle8.png");
      shootAnimation = new Texture[9];
      shootAnimation[0] = new Texture("shoot1.png");
      shootAnimation[1] = new Texture("shoot2.png");
      shootAnimation[2] = new Texture("shoot3.png");
      shootAnimation[3] = new Texture("shoot4.png");
      shootAnimation[4] = new Texture("shoot5.png");
      shootAnimation[5] = new Texture("shoot6.png");
      shootAnimation[6] = new Texture("shoot7.png");
      shootAnimation[7] = new Texture("shoot8.png");
      shootAnimation[8] = new Texture("shoot9.png");
      hurtAnimation = new Texture[7];
      hurtAnimation[0] = new Texture("hurt.png");
      hurtAnimation[1] = new Texture("hurt.png");
      hurtAnimation[2] = new Texture("hurt.png");
      hurtAnimation[3] = new Texture("hurt.png");
      hurtAnimation[4] = new Texture("hurt.png");
      hurtAnimation[5] = new Texture("hurt.png");
      hurtAnimation[6] = new Texture("hurt.png");
    } else if (textureName.equals("redpuck")) {
      idleAnimation = new Texture[8];
      idleAnimation[0] = new Texture("ridle1.png");
      idleAnimation[1] = new Texture("ridle2.png");
      idleAnimation[2] = new Texture("ridle3.png");
      idleAnimation[3] = new Texture("ridle4.png");
      idleAnimation[4] = new Texture("ridle5.png");
      idleAnimation[5] = new Texture("ridle6.png");
      idleAnimation[6] = new Texture("ridle7.png");
      idleAnimation[7] = new Texture("ridle8.png");
      shootAnimation = new Texture[9];
      shootAnimation[0] = new Texture("rshoot1.png");
      shootAnimation[1] = new Texture("rshoot2.png");
      shootAnimation[2] = new Texture("rshoot3.png");
      shootAnimation[3] = new Texture("rshoot4.png");
      shootAnimation[4] = new Texture("rshoot5.png");
      shootAnimation[5] = new Texture("rshoot6.png");
      shootAnimation[6] = new Texture("rshoot7.png");
      shootAnimation[7] = new Texture("rshoot8.png");
      shootAnimation[8] = new Texture("rshoot9.png");
      hurtAnimation = new Texture[7];
      hurtAnimation[0] = new Texture("rhurt.png");
      hurtAnimation[1] = new Texture("rhurt.png");
      hurtAnimation[2] = new Texture("rhurt.png");
      hurtAnimation[3] = new Texture("rhurt.png");
      hurtAnimation[4] = new Texture("rhurt.png");
      hurtAnimation[5] = new Texture("rhurt.png");
      hurtAnimation[6] = new Texture("rhurt.png");
    }
    currentAnimation = idleAnimation;
  }

  private void changeAnimation(Texture[] newAnimation) {
    currentAnimation = newAnimation;
    animationIndex = 0;
  }

  private void advanceAnimation() {
    if (animationIndex >= currentAnimation.length) {
      currentAnimation = idleAnimation;
      animationIndex = 0;
    }
    this.setTexture(currentAnimation[animationIndex]);
    animationIndex++;
  }

  @Override
  public void useFirstSpell(){
    if(currentOrbCooldown > 0){
      if( orb!= null){
        System.out.println("Teleport position = " + orb.getX() + ",\t" + "our height: " + this.getHeight() + " - " + orb.getY());
        this.setPosition(orb.getX(), orb.getY()-orb.getHeight()); //teleport to orb location
        if(this.getY() > GROUND_Y_VALUE){
          currentJumpState = State.FALLING;
          currentJumpSpeed = 0;
        }
        teleportSound.play(1f);
        deleteOrb();
      }
      return;
    }
    if (phaseShiftDuration > 0){
      return;
    }
    orb = new Orb(this, textureName + "orb.png");
    currentOrbCooldown = maxOrbCooldown;
    shootSound.play(0.8f);
    changeAnimation(shootAnimation);
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
    phaseShiftSound.play();
    //changeAnimation(phaseShiftSound)
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

  public void deleteOrb() {
    orb = null;
  }


  @Override
  public void update() {
    if (this.getY() == 1000 && phaseShiftDuration == 0){
      this.setY(previousY);
    }
    if (phaseShiftDuration == 0) {
      super.update();
      if (Gdx.graphics.getFrameId() % 4 == 0) {
        advanceAnimation();
      }
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

  public void reset(){
    orb = null;
    currentPhaseShiftCooldown = 0;
    currentOrbCooldown = 0;
  }

  @Override
  public void damage(int dmg) {
    super.damage(dmg);
    changeAnimation(hurtAnimation);
  }
}
