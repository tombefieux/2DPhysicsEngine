package physics.objects;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import physics.Side;
import physics.Updatable;

/**
 * This class represents an object for the physics engine.
 * This object is the simplest possible, it's an object that doesn't move.
 * @author Tom Befieux
 *
 */
public abstract class PhysicObject implements Updatable {
	
	protected Rectangle hitbox;					/** The hit box of the object. */
	protected String name;						/** The name of this object. */
	
	/**
	 * The constructor of the object.
	 */
	public PhysicObject() {
		this.hitbox = new Rectangle();
		this.name = "none";
	}
	
	/**
	 * The constructor of the object with the hit box.
	 * @param hitbox: the hit box
	 */
	public PhysicObject(Rectangle hitbox) {
		this.hitbox = hitbox;
		this.name = "none";
	}
	
	/**
	 * The constructor of the object with the hit box.
	 * @param name: the name of the object
	 * @param hitbox: the hit box
	 */
	public PhysicObject(String name, Rectangle hitbox) {
		this.hitbox = hitbox;
		this.name = name;
	}

	/**
	 * This function must be run by the engine and must be overridden to know what the object has to do
	 * in a collision with another object.
	 * @param side: the side of the object where the collision happened
	 * @param object: the other object with which the collision happened
	 */
	public abstract void collisionTriggeredOnSide(Side side, PhysicObject object);
	
	/**
	 * Getter of the hit box.
	 * @return the hit box
	 */
	public Rectangle getHitbox() {
		return hitbox;
	}

	/**
	 * To get the position of the object.
	 * @return the position
	 */
	public Point2D getPosition() {
		return new Point2D(this.hitbox.getX(), this.hitbox.getY());
	}
	
	/**
	 * Change the position of the object.
	 * @param position: the position
	 * @return 
	 */
	public void setPosition(Point2D position) {
		this.hitbox = new Rectangle(position.getX(), position.getY(), this.hitbox.getWidth(), this.hitbox.getHeight());
	}
	
	/**
	 * Setter of the hit box.
	 * @param hitbox: the new hit box
	 */
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	/**
	 * Getter for the name.
	 * @return the name of the object
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name.
	 * @param name: the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void update(float delta) {
		// here nothing
	}
}
