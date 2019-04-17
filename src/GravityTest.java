import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import physics.Direction;
import physics.PhysicsEngine;
import physics.Side;
import physics.objects.PhysicEntity;
import physics.objects.PhysicObject;

/**
 * To test the gravity.
 */
public class GravityTest {


    public static void main(String[] args) {


        /**
         *
         * Simple test for the gravity.
         *
         *
         * ////////////  <-- -200
         * |         |
         * |  Player |
         * |         |
         * ///////////
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *      ////////////  <-- 0
         *      |         |
         *      |  Wall   |
         *      |         |
         *      ///////////
         *
         *
         * Player must be stop on x = -100 (100 height)
         *
         */

        PhysicEntity player = new PhysicEntity(new Rectangle(0, -200, 100, 100)) {
            @Override
            public void collisionTriggeredOnSide(Side side, PhysicObject object) {

            }
        };

        PhysicObject wall = new PhysicObject(new Rectangle(50, 0, 100, 100)) {
            @Override
            public void collisionTriggeredOnSide(Side side, PhysicObject object) {

            }
        };

        PhysicsEngine engine = new PhysicsEngine();
        engine.setUseGravity(true);
        engine.setGravityDirection(Direction.DOWN);
        engine.addObject(wall);
        engine.addObject(player);

        while (true) {

            // update the engine
            engine.update(1 / (float) 30);

            // print the y position
            System.out.println("Player y position: " + player.getPosition().getY() + '\n');

            try {
                Thread.sleep(1000 / (long) 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
