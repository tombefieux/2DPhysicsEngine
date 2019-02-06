package physics;

import physics.objects.PhysicObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the physics engine.
 * To use this class, add objects to the engine and update it. The update function of the class
 * updates all the objects for a delta (in second) and handles all the collisions between the objects.
 * When objects are in collision, their function called "collisionTriggeredOnSide" is used.
 *
 * IMPORTANT : The origin is the bottom-left corner.
 *
 * @author Tom Befieux
 *
 */
public class PhysicsEngine {

    protected List<PhysicObject> objects;             /** All the objects handled by the engine. */

    /**
     * The constructor.
     */
    public PhysicsEngine() {
        this.objects = new ArrayList<>();
    }

    /**
     * The main function of the engine.
     * This function updates all the objects and handles the collisions.
     * @param delta: the delta to apply (the time between this update and the previous one usually)
     */
    public void update(float delta) {
        // update objects
        for (PhysicObject object : objects)
            object.update(delta);

        // look for collisions
        for (PhysicObject firstObject : objects)
            for (PhysicObject secondObject : objects) {
                if(firstObject != secondObject) {
                    Side result = calculateCollision(firstObject, secondObject);
                    if (result != null)
                        firstObject.collisionTriggeredOnSide(result, secondObject);
                }
            }
    }

    /**
     * This function gets the collisions between two objects.
     * @param firstObject: the first object
     * @param secondObject: the second object
     * @return null if there is no collision or the side of the first object where the collision happened
     */
    private Side calculateCollision(PhysicObject firstObject, PhysicObject secondObject) {

        if(firstObject == null || secondObject == null) return null;

        Side result = null;

        double x1 = firstObject.getPosition().getX(),
                y1 = firstObject.getPosition().getY(),
                x2 = secondObject.getPosition().getX(),
                y2 = secondObject.getPosition().getY();

        double w1 = firstObject.getHitbox().getWidth(),
                h1 = firstObject.getHitbox().getHeight(),
                w2 = secondObject.getHitbox().getWidth(),
                h2 = secondObject.getHitbox().getHeight();

        // bottom
        if (
                (
                    (x1 > x2 && x1 + w1 < x2 + w2) ||
                    (x1 >= x2 && x1 < x2 + w2) ||
                    (x1 + w1 > x2 && x1 + w1 <= x2 + w2) ||
                    (x1 < x2 && x1 + w1 > x2 + w2)
                ) &&
                    (y1 <= y2 + h2 && y1 > y2)
        )
            result = Side.BOTTOM;

        // top
        else if (
                (
                    (x1 > x2 && x1 + w1 < x2 + w2) ||
                    (x1 >= x2 && x1 < x2 + w2) ||
                    (x1 + w1 > x2 && x1 + w1 <= x2 + w2) ||
                    (x1 < x2 && x1 + w1 > x2 + w2)
                ) &&
                    (y1 + h1 > y2 && y1 + h1 < y2 + h2)
        )
            result = Side.TOP;

        // left
        else if (
                (
                    (y1 + h1 > y2 + h2 && y1 < y2) ||
                    (y1 + h1 < y2 + h2 && y1 > y2) ||
                    (y1 >= y2 && y1 < y2 + h2) ||
                    (y1 + h1 > y2 && y1 + h1 <= y2 + h2)
                ) &&
                    (x1 < x2 + w2 && x1 > x2)
        )
            result = Side.LEFT;

        // right
        else if(
                (
                    (y1 + h1 > y2 + h2 && y1 < y2) ||
                    (y1 + h1 < y2 + h2 && y1 > y2) ||
                    (y1 >= y2 && y1 < y2 + h2) ||
                    (y1 + h1 > y2 && y1 + h1 <= y2 + h2)
                ) &&
                    (x1 + w1 >= x2 && x1 + w1 < x2 + w2)
        )
            result = Side.RIGHT;

        // in (second object into the first one)
        else if(
            (y1 + h1 >= y2 + h2 && y1 <= y2) &&
            (x1 <= x2 && x1 + w1 >= x2 + w2)
        )
            result = Side.IN;

        return result;
    }

    /**
     * To add a physic object in the engine.
     * @param object: the object to add
     */
    public void addObject(PhysicObject object) {
        if(object != null)
            this.objects.add(object);
    }

    /**
     * To remove a physic object of the engine.
     * @param object: the object to remove
     */
    public void removeObject(PhysicObject object) {
        if(object != null)
            this.objects.remove(object);
    }

    /**
     * Getter of all the objects of the engine.
     * @return all the objects managed by the engine
     */
    public List<PhysicObject> getObjects() {
        return objects;
    }
}
