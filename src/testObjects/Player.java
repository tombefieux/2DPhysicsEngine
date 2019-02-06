package testObjects;

import javafx.geometry.Point2D;
import physics.Side;
import physics.objects.PhysicEntity;
import physics.objects.PhysicObject;

/**
 * This class represents a player.
 * @author Tom Befieux
 *
 */
public class Player extends PhysicEntity {

    // implement the collision function
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        // if we hit a wall stop the player on the right side
        if(object instanceof Wall) {
            // top or bottom
            if (side == Side.TOP || side == Side.BOTTOM)
                this.velocity = new Point2D(this.velocity.getX(), 0);

            // right or left
            else
                this.velocity = new Point2D(0, this.velocity.getY());
        }

        System.out.println("I'm named " + this.name + " and I've been hit on my " + side.toString() + " by " + object.getName());
    }
}
