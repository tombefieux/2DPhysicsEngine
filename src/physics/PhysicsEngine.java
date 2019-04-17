package physics;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import physics.objects.PhysicEntity;
import physics.objects.PhysicObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the physics engine.
 * To use this class, add objects to the engine and update it. The update function of the class
 * updates all the objects for a delta (in second) and handles all the collisions between the objects.
 * When objects are in collision, their function called "collisionTriggeredOnSide" is used.
 *
 * The engine can handle gravity. The PhysicObjects are considered as walls and the entities will be
 * stop in their fall by the objects. You can choose the direction of the gravity and it's value.
 *
 * IMPORTANT : The origin is the top-left corner.
 *
 * @author Tom Befieux
 *
 */
public class PhysicsEngine implements Updatable {

    protected List<PhysicObject> objects;                       /** All the objects handled by the engine. */
    private boolean useGravity = false;                         /** If the engine use gravity or not. */
    private Direction gravityDirection = Direction.DOWN;        /** The direction of the gravity. */
    private Side sideToStopGravityOnCollision = Side.BOTTOM;    /** The side where we need to stop the gravity if there's a collision. */

    /**
     * The value of the gravity for the engine.
     * This value is add to the objects every seconds.
     */
    private float gravityValue = 7.f;

    /**
     * The constructor.
     */
    public PhysicsEngine() {
        this.objects = new ArrayList<>();
    }

    /**
     * Constructor with a gravity value. Means that the engine will apply gravity on objects.
     * @param gravityValue: the gravity value
     * @param gravityDirection: the direction of the gravity
     */
    public PhysicsEngine(float gravityValue, Direction gravityDirection) {
        this.useGravity = true;
        this.gravityValue = gravityValue;
        setGravityDirection(gravityDirection);
    }

    /**
     * The main function of the engine.
     * This function updates all the objects and handles the collisions.
     * It also applies the gravity if the engine has to.
     * @param delta: the delta to apply (the time between this update and the previous one usually)
     */
    public void update(float delta) {

        // for each object -- gravity and update
        for (int i = 0; i < objects.size(); i++) {

            // apply gravity if it's an entity
            if (this.useGravity && objects.get(i) instanceof PhysicEntity) {
                switch (this.gravityDirection) {
                    case DOWN:
                        ((PhysicEntity) objects.get(i)).addVelocity(new Point2D(0, this.gravityValue * delta));
                        break;

                    case UP:
                        ((PhysicEntity) objects.get(i)).subtractVelocity(new Point2D(0, this.gravityValue * delta));
                        break;

                    case RIGHT:
                        ((PhysicEntity) objects.get(i)).addVelocity(new Point2D(this.gravityValue * delta, 0));
                        break;

                    case LEFT:
                        ((PhysicEntity) objects.get(i)).subtractVelocity(new Point2D(this.gravityValue * delta, 0));
                        break;
                }
            }

            // update it
            objects.get(i).update(delta);
        }

        // for each object -- collisions
        for (int i = 0; i < objects.size(); i++) {
            // look for collisions
            for (int j = i + 1; j < objects.size(); j++) {

                Side result = calculateCollision(objects.get(i), objects.get(j));
                if (result != null) {

                    // if we use gravity
                    if(this.useGravity) {
                        // if there's one entity at least
                        if (objects.get(i) instanceof PhysicEntity || objects.get(j) instanceof PhysicEntity) {

                            // if the objects are not an entity, we check if the collision implies to stop the gravity and we correct the position
                            if (!(objects.get(i) instanceof PhysicEntity)) {
                                if (getOppositeSide(result) == this.sideToStopGravityOnCollision || getOppositeSide(result) == Side.AROUND || getOppositeSide(result) == Side.IN) {

                                    // stop velocity
                                    if(this.sideToStopGravityOnCollision == Side.BOTTOM || this.sideToStopGravityOnCollision == Side.TOP)
                                        ((PhysicEntity) objects.get(j)).setVelocity(new Point2D(((PhysicEntity) objects.get(j)).getVelocity().getX(), 0));
                                    else
                                        ((PhysicEntity) objects.get(j)).setVelocity(new Point2D(0, ((PhysicEntity) objects.get(j)).getVelocity().getY()));

                                    // correct position
                                    switch (this.sideToStopGravityOnCollision) {
                                        case LEFT:
                                            objects.get(j).setPosition(new Point2D(objects.get(i).getPosition().getX() + objects.get(i).getHitbox().getWidth(), objects.get(j).getPosition().getY()));
                                            break;

                                        case RIGHT:
                                            objects.get(j).setPosition(new Point2D(objects.get(i).getPosition().getX() - objects.get(j).getHitbox().getWidth(), objects.get(j).getPosition().getY()));
                                            break;

                                        case BOTTOM:
                                            objects.get(j).setPosition(new Point2D(objects.get(j).getPosition().getX(), objects.get(i).getPosition().getY() - objects.get(j).getHitbox().getHeight()));
                                            break;

                                        case TOP:
                                            objects.get(j).setPosition(new Point2D(objects.get(j).getPosition().getX(), objects.get(i).getPosition().getY() + objects.get(i).getHitbox().getHeight()));
                                            break;
                                    }
                                }
                            } else if (!(objects.get(j) instanceof PhysicEntity)) {
                                if (result == this.sideToStopGravityOnCollision || result == Side.AROUND || result == Side.IN) {

                                    // stop velocity
                                    if(this.sideToStopGravityOnCollision == Side.BOTTOM || this.sideToStopGravityOnCollision == Side.TOP)
                                        ((PhysicEntity) objects.get(i)).setVelocity(new Point2D(((PhysicEntity) objects.get(i)).getVelocity().getX(), 0));
                                    else
                                        ((PhysicEntity) objects.get(i)).setVelocity(new Point2D(0, ((PhysicEntity) objects.get(i)).getVelocity().getY()));

                                    // correct position
                                    switch (this.sideToStopGravityOnCollision) {
                                        case LEFT:
                                            objects.get(i).setPosition(new Point2D(objects.get(j).getPosition().getX() + objects.get(j).getHitbox().getWidth(), objects.get(i).getPosition().getY()));
                                            break;

                                        case RIGHT:
                                            objects.get(i).setPosition(new Point2D(objects.get(j).getPosition().getX() - objects.get(i).getHitbox().getWidth(), objects.get(i).getPosition().getY()));
                                            break;

                                        case BOTTOM:
                                            objects.get(i).setPosition(new Point2D(objects.get(i).getPosition().getX(), objects.get(j).getPosition().getY() - objects.get(i).getHitbox().getHeight()));
                                            break;

                                        case TOP:
                                            objects.get(i).setPosition(new Point2D(objects.get(i).getPosition().getX(), objects.get(j).getPosition().getY() + objects.get(j).getHitbox().getHeight()));
                                            break;
                                    }
                                }
                            }
                        }
                    }

                    // call the collision functions
                    objects.get(i).collisionTriggeredOnSide(result, objects.get(j));
                    objects.get(j).collisionTriggeredOnSide(getOppositeSide(result), objects.get(i));
                }
            }
        }
    }

    /**
     * This function returns the opposite side of a side in parameter
     * @param side: the side
     * @return the opposite side
     */
    public Side getOppositeSide(Side side)
    {
        Side result = null;

        switch (side){
            case TOP:
                result = Side.BOTTOM;
                break;

            case BOTTOM:
                result = Side.TOP;
                break;

            case LEFT:
                result = Side.RIGHT;
                break;

            case RIGHT:
                result = Side.LEFT;
                break;

            case IN:
                result = Side.AROUND;
                break;

            case AROUND:
                result = Side.IN;
                break;
        }

        return result;
    }

    /**
     * This function gets the collisions between two objects.
     * @param firstObject: the first object
     * @param secondObject: the second object
     * @return null if there is no collision or the side of the first object where the collision happened
     */
    private Side calculateCollision(PhysicObject firstObject, PhysicObject secondObject) {
        if(firstObject == null || secondObject == null) return null;

        Point2D veloOne = new Point2D(0, 0);
        Point2D veloTwo = new Point2D(0, 0);

        if(firstObject instanceof PhysicEntity)
            veloOne = ((PhysicEntity) firstObject).getVelocity();
        if(secondObject instanceof PhysicEntity)
            veloTwo = ((PhysicEntity) secondObject).getVelocity();

        return calculateCollision(firstObject.getHitbox(), veloOne, secondObject.getHitbox(), veloTwo);
    }

    /**
     * This function gets the collisions between two hit box.
     * @param firstHitbox: the first object
     * @param secondHitbox: the second object
     * @param firstHitboxVelocity: the velocity of the first hit box
     * @param secondHitboxVelocity: the velocity of the second hit box
     * @return null if there is no collision or the side of the first hit box where the collision happened
     */
    private Side calculateCollision(Rectangle firstHitbox, Point2D firstHitboxVelocity, Rectangle secondHitbox, Point2D secondHitboxVelocity) {

        if(firstHitbox == null || secondHitbox == null) return null;

        Side result = null;

        double x1 = firstHitbox.getX(),
                y1 = firstHitbox.getY(),
                x2 = secondHitbox.getX(),
                y2 = secondHitbox.getY();

        double w1 = firstHitbox.getWidth(),
                h1 = firstHitbox.getHeight(),
                w2 = secondHitbox.getWidth(),
                h2 = secondHitbox.getHeight();

        // bottom
        if (
                (
                        (x1 > x2 && x1 + w1 < x2 + w2) ||
                                (x1 >= x2 && x1 < x2 + w2) ||
                                (x1 + w1 > x2 && x1 + w1 <= x2 + w2) ||
                                (x1 < x2 && x1 + w1 > x2 + w2)
                ) &&
                        (y2 < y1 + h1 && y1 < y2 && y2 + h2 > y1 + h1)
                &&
                        (firstHitboxVelocity.getY() > 0 || secondHitboxVelocity.getY() < 0 ||
                                (firstHitboxVelocity.getY() == 0 && secondHitboxVelocity.getY() == 0 &&
                                        firstHitboxVelocity.getX() == 0 && secondHitboxVelocity.getX() == 0)
                        )
        )
            result = Side.BOTTOM;

        // top
        if (
                (
                        (x1 > x2 && x1 + w1 < x2 + w2) ||
                                (x1 >= x2 && x1 < x2 + w2) ||
                                (x1 + w1 > x2 && x1 + w1 <= x2 + w2) ||
                                (x1 < x2 && x1 + w1 > x2 + w2)
                ) &&
                        (y2 + h2 > y1 && y2 + h2 < y1 + h1 && y2 < y1)
                &&
                        (firstHitboxVelocity.getY() < 0 || secondHitboxVelocity.getY() > 0 ||
                                (firstHitboxVelocity.getY() == 0 && secondHitboxVelocity.getY() == 0 &&
                                        firstHitboxVelocity.getX() == 0 && secondHitboxVelocity.getX() == 0)
                        )
        )
            result = Side.TOP;

        // left
        if (
                (
                        (y1 + h1 > y2 + h2 && y1 < y2) ||
                                (y1 + h1 < y2 + h2 && y1 > y2) ||
                                (y1 >= y2 && y1 < y2 + h2) ||
                                (y1 + h1 > y2 && y1 + h1 <= y2 + h2)
                ) &&
                        (x1 < x2 + w2 && x1 > x2 && x1 + w1 > x2)
                &&
                        (firstHitboxVelocity.getX() < 0 || secondHitboxVelocity.getX() > 0 ||
                                (firstHitboxVelocity.getY() == 0 && secondHitboxVelocity.getY() == 0 &&
                                        firstHitboxVelocity.getX() == 0 && secondHitboxVelocity.getX() == 0)
                        )
        )
            result = Side.LEFT;

        // right
        if(
                (
                        (y1 + h1 > y2 + h2 && y1 < y2) ||
                                (y1 + h1 < y2 + h2 && y1 > y2) ||
                                (y1 >= y2 && y1 < y2 + h2) ||
                                (y1 + h1 > y2 && y1 + h1 <= y2 + h2)
                ) &&
                        (x1 + w1 >= x2 && x1 + w1 < x2 + w2 && x2 + w2 > x1 + w1)
                &&
                        (firstHitboxVelocity.getX() > 0 || secondHitboxVelocity.getX() < 0 ||
                                (firstHitboxVelocity.getY() == 0 && secondHitboxVelocity.getY() == 0 &&
                                        firstHitboxVelocity.getX() == 0 && secondHitboxVelocity.getX() == 0)
                        )
        )
            result = Side.RIGHT;

        // in (first object into the second one)
        if(
                (y1 + h1 < y2 + h2 && y1 > y2) &&
                        (x1 > x2 && x1 + w1 < x2 + w2)
        )
            result = Side.IN;

        return result;
    }

    /**
     * This function returns if there is a collision on this entity on the next update.
     * @param entity: the object
     * @param delta: the delta to apply
     * @return the object with which the collision will happen or null
     */
    public PhysicObject collisionOnNextUpdate(PhysicEntity entity, float delta) {

        PhysicObject result = null;
        Point2D nextPos = entity.getNextPosition(delta);
        Rectangle hitbox = new Rectangle(nextPos.getX(), nextPos.getY(), entity.getHitbox().getWidth(), entity.getHitbox().getHeight());
        Point2D veloOne = entity.getVelocity();


        for (PhysicObject temp : objects) {
            if(temp != entity) {
                Rectangle tempHitbox = temp.getHitbox();
                Point2D veloTwo = new Point2D(0, 0);

                // if it's an entity
                if (temp instanceof PhysicEntity) {
                    Point2D tempNextPos = ((PhysicEntity) temp).getNextPosition(delta);
                    tempHitbox = new Rectangle(tempNextPos.getX(), tempNextPos.getY(), temp.getHitbox().getWidth(), temp.getHitbox().getHeight());
                    veloTwo = ((PhysicEntity) temp).getVelocity();
                }

                if (calculateCollision(hitbox, veloOne, tempHitbox, veloTwo) != null)
                    result = temp;
            }
        }

        return result;
    }

    /**
     * This function returns all the objects around the object in parameter with a perimeter.
     * @param object: the object
     * @param perimeter: the perimeter around the object
     * @return the objects around
     */
    public List<PhysicObject> getObjectsAround(PhysicObject object, int perimeter) {
        List<PhysicObject> result = new ArrayList<>();
        Rectangle perimeterRectangle = new Rectangle(
                object.getHitbox().getX() - perimeter,
                object.getHitbox().getY() - perimeter,
                object.getHitbox().getWidth() + perimeter * 2,
                object.getHitbox().getHeight() + perimeter * 2
        );

        for (PhysicObject temp: this.objects)
            if(temp != object && calculateCollision(perimeterRectangle, new Point2D(0, 0), temp.getHitbox(), new Point2D(0, 0)) != null)
                result.add(temp);

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

    /**
     * Returns the objects of the engine with a specific name.
     * @param name: the name
     * @return the list of objects
     */
    public List<PhysicObject> getObjectsByName(String name) {
        List<PhysicObject> result = new ArrayList<>();

        for (PhysicObject object : this.objects)
            if(object.getName().equals(name))
                result.add(object);

        return result;
    }

    public boolean isUsingGravity() {
        return useGravity;
    }

    public void setUseGravity(boolean useGravity) {
        this.useGravity = useGravity;
    }

    public float getGravityValue() {
        return gravityValue;
    }

    public void setGravityValue(float gravityValue) {
        if(gravityValue > 0) {
            this.useGravity = true;
            this.gravityValue = gravityValue;
        }
    }

    public Direction getGravityDirection() {
        return gravityDirection;
    }

    public void setGravityDirection(Direction gravityDirection) {
        this.gravityDirection = gravityDirection;
        this.useGravity = true;

        switch (this.gravityDirection) {
            case DOWN:
                this.sideToStopGravityOnCollision = Side.BOTTOM;
                break;

            case UP:
                this.sideToStopGravityOnCollision = Side.TOP;
                break;

            case LEFT:
                this.sideToStopGravityOnCollision = Side.LEFT;
                break;

            case RIGHT:
                this.sideToStopGravityOnCollision = Side.RIGHT;
                break;
        }
    }
}
