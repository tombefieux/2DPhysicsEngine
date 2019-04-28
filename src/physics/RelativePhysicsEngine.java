package physics;

import physics.objects.PhysicObject;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;


/**
 * This class extends of the PhysicsEngine. It represents an engine with a relative positions mechanism.
 * It must have a reference object and the positions of the other objects can be given depending of the reference object.
 * The reference object has a liberty rectangle where is allowed to move without modifications of the positions of the other objects.
 * To track an object at the middle of the screen you need a rectangle of null width and height and an x equal to the screen width / 2
 * and a y equal to the screen height / 2.
 */
public class RelativePhysicsEngine extends PhysicsEngine {

    private PhysicObject referenceObject = null;            /** The reference object. */
    private Rectangle libertyRectangle;                     /** The rectangle of liberty for the focused object. */
    private double xPlan;                                   /** The x coordinate of the plan. (top left corner) */
    private double yPlan;                                   /** The y coordinate of the plan. (top left corner) */
    private double currentXLiberty;                         /** The current liberty of the reference object in x. */
    private double currentYLiberty;                         /** The current liberty of the reference object in y. */

    /**
     * Constructor.
     */
    public RelativePhysicsEngine()
    {
        super();
        this.libertyRectangle = new Rectangle(0, 0, 0, 0);
        this.xPlan = 0;
        this.yPlan = 0;
        this.currentXLiberty = 0;
        this.currentYLiberty = 0;

        focusOnReferenceObject();
    }

    /**
     * Constructor with a liberty rectangle
     * @param libertyRectangle: the liberty rectangle
     */
    public RelativePhysicsEngine(Rectangle libertyRectangle)
    {
        super();
        this.libertyRectangle = libertyRectangle;
        this.xPlan = 0;
        this.yPlan = 0;
        this.currentXLiberty = 0;
        this.currentYLiberty = 0;

        focusOnReferenceObject();
    }

    /**
     * Constructor to use gravity.
     * @param gravityValue
     * @param gravityDirection
     */
    public RelativePhysicsEngine(float gravityValue, Direction gravityDirection) {
        super(gravityValue, gravityDirection);

        this.libertyRectangle = new Rectangle(0, 0, 0, 0);
        this.xPlan = 0;
        this.yPlan = 0;
        this.currentXLiberty = 0;
        this.currentYLiberty = 0;

        focusOnReferenceObject();
    }

    /**
     * Constructor to use gravity.
     * @param gravityValue
     * @param gravityDirection
     * @param libertyRectangle
     */
    public RelativePhysicsEngine(float gravityValue, Direction gravityDirection, Rectangle libertyRectangle) {
        super(gravityValue, gravityDirection);

        this.libertyRectangle = libertyRectangle;
        this.xPlan = 0;
        this.yPlan = 0;
        this.currentXLiberty = 0;
        this.currentYLiberty = 0;

        focusOnReferenceObject();
    }

    /**
     * This function returns the relative position of an object according to the reference object.
     * @param object: the object
     * @return the relative position of an object.
     */
    public Point2D getRelativePosition(PhysicObject object) {
        return new Point2D(object.getPosition().getX() - this.xPlan, object.getPosition().getY() - this.yPlan);
    }

    @Override
    public void update(float delta) {
        Point2D savedReferenceObjectPosition = this.referenceObject.getPosition();
        super.update(delta);

        // see if the ref object moved
        Point2D movementOfFocusedObject = this.referenceObject.getPosition().subtract(savedReferenceObjectPosition);

        // x
        this.currentXLiberty += movementOfFocusedObject.getX();
        if(Math.abs(this.currentXLiberty) > this.libertyRectangle.getWidth() / 2.d) {
            if(this.currentXLiberty < 0) {
                this.xPlan -= Math.abs(this.libertyRectangle.getWidth() / 2.d - Math.abs(this.currentXLiberty));
                this.currentXLiberty = -1 * (this.libertyRectangle.getWidth() / 2.d);
            }
            else {
                this.xPlan += Math.abs(this.libertyRectangle.getWidth() / 2.d - Math.abs(this.currentXLiberty));
                this.currentXLiberty = this.libertyRectangle.getWidth() / 2.d;
            }
        }

        // y
        this.currentYLiberty += movementOfFocusedObject.getY();
        if(Math.abs(this.currentYLiberty) > this.libertyRectangle.getHeight() / 2.d) {
            if(this.currentYLiberty < 0) {
                this.yPlan -= Math.abs(this.libertyRectangle.getHeight() / 2.d - Math.abs(this.currentYLiberty));
                this.currentYLiberty = -1 * (this.libertyRectangle.getHeight() / 2.d);
            }
            else {
                this.yPlan += Math.abs(this.libertyRectangle.getHeight() / 2.d - Math.abs(this.currentYLiberty));
                this.currentYLiberty = this.libertyRectangle.getHeight() / 2.d;
            }
        }
    }

    /**
     * This function switch of reference object for the engine.
     * @param newReferenceObject: the new reference object (must be in the engine)
     */
    public void setReferenceObject(PhysicObject newReferenceObject) {
        if(objects.contains(newReferenceObject)) {
            referenceObject = newReferenceObject;
            focusOnReferenceObject();
        }
    }

    /**
     * To focus on the reference object.
     */
    public void focusOnReferenceObject() {
        if(referenceObject != null) {
            this.xPlan = referenceObject.getHitbox().getX() + referenceObject.getHitbox().getWidth() / 2 - (this.libertyRectangle.getWidth() / 2.d + this.libertyRectangle.getX());
            this.yPlan = referenceObject.getHitbox().getY() + referenceObject.getHitbox().getHeight() / 2 - (this.libertyRectangle.getHeight() / 2.d + this.libertyRectangle.getY());
            this.currentXLiberty = 0;
            this.currentYLiberty = 0;
        }
    }

    public Rectangle getLibertyRectangle() {
        return libertyRectangle;
    }

    /**
     * To change the width of liberty of movements for the reference object.
     * To focus on the reference object all the time, send 0.
     * @param libertyRectangle: the new rectangle of liberty
     */
    public void setLibertyRectangle(Rectangle libertyRectangle) {
        if (libertyRectangle.getWidth() >= 0 && libertyRectangle.getHeight() >= 0 && libertyRectangle.getX() >= 0 && libertyRectangle.getY() >= 0) {
            this.libertyRectangle = libertyRectangle;
            focusOnReferenceObject();
        }
    }
}
