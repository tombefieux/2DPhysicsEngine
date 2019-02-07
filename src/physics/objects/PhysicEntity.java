package physics.objects;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * This class represents an physic entity for the physics engine.
 * It's a simple physic object but that can move.
 * @author Tom Befieux
 *
 */
public abstract class PhysicEntity extends PhysicObject {
	
	protected Point2D velocity;						/** The velocity of the object in physics unit per second. */
	
	/**
	 * The constructor of the object.
	 */
	public PhysicEntity() {
		super();
		this.velocity = new Point2D(0, 0);
	}
	
	/**
	 * The constructor of the object with the hit box.
	 * @param hitbox: the hit box
	 */
	public PhysicEntity(Rectangle hitbox) {
		super(hitbox);
		this.velocity = new Point2D(0, 0);
	}
	
	/**
	 * The constructor of the object with the hit box.
	 * @param name: the name of the object
	 * @param hitbox: the hit box
	 */
	public PhysicEntity(String name, Rectangle hitbox) {
		super(name, hitbox);
		this.velocity = new Point2D(0, 0);
	}
	
	@Override
	public void update(float delta) {
		// change the position of the object with velocity
		setPosition(getNextPosition(delta));
	}

	/**
	 * This function returns the next position according with the velocity with a delta.
	 * @param delta: the delta time
	 * @return the next position
	 */
	public Point2D getNextPosition(float delta) {
		double x = this.getPosition().getX() + this.velocity.getX() * delta;
		double y = this.getPosition().getY() + this.velocity.getY() * delta;
		return new Point2D(x, y);
	}

	/**
	 * To add a velocity to the object.
	 * @param velocity: the velocity
	 */
	public void addVelocity(Point2D velocity) {
		this.velocity.add(velocity);
	}
	
	/**
	 * To subtract a velocity.
	 * @param velocity: the velocity
	 */
	public void subtractVelocity(Point2D velocity) {
		this.velocity.subtract(velocity);
	}
	
	/**
	 * To stop the velocity of the object.
	 */
	public void stopMovement() {
		setVelocity(new Point2D(0, 0));
	}
	
	/**
	 * Getter for the velocity of the object.
	 * @return the velocity.
	 */
	public Point2D getVelocity() {
		return velocity;
	}

	/**
	 * Setter for the velocity.
	 * @param velocity: the new velocity.
	 */
	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}
}
