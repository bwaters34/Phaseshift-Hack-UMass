package codes.brick.hackumassdotademake;

import com.badlogic.gdx.math.Rectangle;

public class CollisionDetector {
	private Puck p1, p2;
	public static final int COLLISION_DAMAGE = 5;

	public CollisionDetector(Puck player1, Puck player2) {
		p1 = player1;
		p2 = player2;
	}

	public void detectCollisions() {
		Rectangle p1Rect = p1.getBoundingRectangle();
		Rectangle p2Rect = p2.getBoundingRectangle();
		Orb p1Orb = p1.getOrb();
		Orb p2Orb = p2.getOrb();
		if (p1Orb != null && p1Orb.harmful) {
			Rectangle orb1Rect = p1Orb.getBoundingRectangle();
			if (p2Rect.overlaps(orb1Rect)) {
				p2.damage(Orb.DAMAGE);
				p1Orb.harmful = false;
			}
		}
		if (p2Orb != null && p2Orb.harmful) {
			Rectangle orb2Rect = p2Orb.getBoundingRectangle();
			if (p1Rect.overlaps(orb2Rect)) {
				p1.damage(Orb.DAMAGE);
				p2Orb.harmful = false;
			}
		}
		if (p1Rect.overlaps(p2Rect)) {
			if (p1.getX() < p2.getX()) {
				p1.setPosition(p1.getX() - Puck.COLLISION_REBOUND, p1.getY());
				p2.setPosition(p2.getX() + Puck.COLLISION_REBOUND, p2.getY());
				if (!p1.isFlipX() && p2.isFlipX()) {
					// If p1 is facing right and p2 is facing left
					p1.damage(COLLISION_DAMAGE);
					p2.damage(COLLISION_DAMAGE);
				} else if (p1.isFlipX() && p2.isFlipX()) {
					// If p1 is facing left and p2 is facing left
					p1.damage(COLLISION_DAMAGE);
				} else if (p1.isFlipX() && !p2.isFlipX()) {
					// If p1 is facing left and p2 is facing right
					// no damage
				} else if (!p1.isFlipX() && !p2.isFlipX()) {
					// If p1 is facing right and p2 is facing right
					p2.damage(COLLISION_DAMAGE);
				}

			}
			else if (p1.getX() > p2.getX()){
				p1.setPosition(p1.getX() + Puck.COLLISION_REBOUND, p1.getY());
				p2.setPosition(p2.getX() - Puck.COLLISION_REBOUND, p2.getY());
				if (!p1.isFlipX() && p2.isFlipX()) {
					// If p1 is facing right and p2 is facing left
					// no damage
				} else if (p1.isFlipX() && p2.isFlipX()) {
					// If p1 is facing left and p2 is facing left
					p2.damage(COLLISION_DAMAGE);
				} else if (p1.isFlipX() && !p2.isFlipX()) {
					// If p1 is facing left and p2 is facing right
					p1.damage(COLLISION_DAMAGE);
					p2.damage(COLLISION_DAMAGE);
				} else if (!p1.isFlipX() && !p2.isFlipX()) {
					// If p1 is facing right and p2 is facing right
					p1.damage(COLLISION_DAMAGE);
				}
			} else {
				p1.setPosition(p1.getX() + Puck.COLLISION_REBOUND, p1.getY());
				p2.setPosition(p2.getX() - Puck.COLLISION_REBOUND, p2.getY());
				// no damage if they are on top of each other exactly
			}
		}
		validatePosition(p1);
		validatePosition(p2);
	}

	public void validatePosition(Puck player) {
		if (player.getX() < 0) {
			player.setX(0);
		}
		else if (player.getX() > MyGdxGame.VIEWPORT_WIDTH - player.getWidth()) {
			player.setX(MyGdxGame.VIEWPORT_WIDTH - player.getWidth());
		}

		if(player.getOrb() != null && (player.getOrb().getX() < 0 || player.getOrb().getX() >  MyGdxGame.VIEWPORT_WIDTH - player.getOrb().getWidth())){
			player.deleteOrb();
		}
	}


}
