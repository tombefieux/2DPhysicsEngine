package physics;

/**
 * The objects that implement this interface are updatable for the physics engine.
 * @author Tom Befieux
 *
 */
public interface Updatable {

	/**
	 * This function updates the object.
	 * @param delta: the delta in second needed to update
	 */
	public void update(float delta);
	
}
