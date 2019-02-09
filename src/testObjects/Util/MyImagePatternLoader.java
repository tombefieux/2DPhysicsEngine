package testObjects.Util;

import javafx.geometry.Point2D;
import physics.Util.ObjectsImageLoader;
import physics.objects.PhysicEntity;
import physics.objects.PhysicObject;
import testObjects.Player;
import testObjects.Wall;

/**
 * This class is an example of how to use the pattern image loader class.
 * @author Tom Befieux
 *
 */
public class MyImagePatternLoader extends ObjectsImageLoader {

    /**
     * Constructor with the colors that interest us.
     */
    public MyImagePatternLoader() {
        super(new int[]{124, 255});
    }

    // implementation of the function
    @Override
    protected PhysicObject getObjectFromColor(int color) {

        PhysicObject result = null;

        if(color == 124) {
            result = new Wall();
            result.setName("Wall");
        }

        else if(color == 255) {
            result = new Player();
            ((PhysicEntity) result).setVelocity(new Point2D(0, -50)); // apply a velocity
            result.setName("Player");
        }

        return result;
    }
}
