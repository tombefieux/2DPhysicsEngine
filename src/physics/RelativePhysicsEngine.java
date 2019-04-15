package physics;

import physics.objects.PhysicObject;
import javafx.geometry.Point2D;


/**
 * This class extends of the PhysicsEngine. It represents an engine with a relative positions mechanism.
 * It must have a reference object and the positions of the other objects can be given depending of the reference object.
 */
public class RelativePhysicsEngine extends PhysicsEngine {

    private PhysicObject referenceObject = null;            /** The reference object. */
    private int libertyWidth;                               /** The width of liberty for the movements of the reference object. */
    private int libertyHeight;                              /** The height of liberty for the movements of the reference object. */
    private int screenWidth;                                /** The width of the screen. */
    private int screenHeight;                               /** The height of the screen. */
    private double xPlan;                                   /** The x coordinate of the plan. */
    private double yPlan;                                   /** The y coordinate of the plan. */
    private double currentXLiberty;                         /** The current liberty of the reference object in x. */
    private double currentYLiberty;                         /** The current liberty of the reference object in y. */

    /**
     * Constructor with the reference object.
     * @param screenWidth: the width of the screen.
     * @param screenHeight: the height of the screen.
     */
    public RelativePhysicsEngine(int screenWidth, int screenHeight)
    {
        super();
        this.libertyWidth = 0;
        this.libertyHeight = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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
        if(Math.abs(this.currentXLiberty) > this.libertyWidth / 2.d) {
            if(this.currentXLiberty < 0) {
                this.xPlan -= Math.abs(this.libertyWidth / 2.d - Math.abs(this.currentXLiberty));
                this.currentXLiberty = -1 * (this.libertyWidth / 2.d);
            }
            else {
                this.xPlan += Math.abs(this.libertyWidth / 2.d - Math.abs(this.currentXLiberty));
                this.currentXLiberty = this.libertyWidth / 2.d;
            }
        }

        // y
        this.currentYLiberty += movementOfFocusedObject.getY();
        if(Math.abs(this.currentYLiberty) > this.libertyHeight / 2.d) {
            if(this.currentYLiberty < 0) {
                this.yPlan -= Math.abs(this.libertyHeight / 2.d - Math.abs(this.currentYLiberty));
                this.currentYLiberty = -1 * (this.libertyHeight / 2.d);
            }
            else {
                this.yPlan += Math.abs(this.libertyHeight / 2.d - Math.abs(this.currentYLiberty));
                this.currentYLiberty = this.libertyHeight / 2.d;
            }
        }
    }

    /**
     * This function switch of reference object for the engine.
     * @param newReferenceObject: the new reference object (must be in the engine)
     */
    public void setReferenceObject(PhysicObject newReferenceObject) {
        if(objects.contains(newReferenceObject))
            referenceObject = newReferenceObject;
    }

    /**
     * To focus on the reference object.
     */
    public void focusOnReferenceObject() {
        if(referenceObject != null) {
            this.xPlan = referenceObject.getHitbox().getX() + referenceObject.getHitbox().getWidth() / 2 - screenWidth / 2.d;
            this.yPlan = referenceObject.getHitbox().getY() + referenceObject.getHitbox().getHeight() / 2 - screenHeight / 2.d;
            this.currentXLiberty = 0;
            this.currentYLiberty = 0;
        }
    }

    public int getLibertyWidth() {
        return libertyWidth;
    }

    /**
     * To change the width of liberty of movements for the reference object.
     * To focus on the reference object all the time, send 0.
     * @param libertyWidth
     */
    public void setLibertyWidth(int libertyWidth) {
        if (libertyWidth >= 0) {
            this.libertyWidth = libertyWidth;
            focusOnReferenceObject();
        }
    }

    public int getLibertyHeight() {
        return libertyHeight;
    }

    /**
     * To change the height of liberty of movements for the reference object.
     * To focus on the reference object all the time, send 0.
     * @param libertyHeight
     */
    public void setLibertyHeight(int libertyHeight) {
        if(libertyHeight >= 0) {
            this.libertyHeight = libertyHeight;
            focusOnReferenceObject();
        }
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        if(screenWidth >= 0) {
            this.screenWidth = screenWidth;
            focusOnReferenceObject();
        }
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        if (screenHeight >= 0) {
            this.screenHeight = screenHeight;
            focusOnReferenceObject();
        }
    }
}
