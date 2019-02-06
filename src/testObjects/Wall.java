package testObjects;

import physics.Side;
import physics.objects.PhysicObject;

/**
 * This class represents a wall.
 * @author Tom Befieux
 *
 */
public class Wall extends PhysicObject {

    // implement the collision function
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        // it's a wall, we don't move!
        System.out.println("I'm named " + this.name + " and I've been hit on my " + side.toString() + " by " + object.getName());
    }
}
