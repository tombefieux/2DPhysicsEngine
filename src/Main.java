import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import physics.PhysicsEngine;
import testObjects.Player;
import testObjects.Wall;

/**
 * This class is a test class for the engine.
 * @author Tom Befieux
 *
 */
public class Main {

    public static void main(String[] args) {

        /*
         * Here we test the engine.
         * We'll create two objects, a wall and a player that will hit the wall from the bottom of the wall.
         * To do that we apply a velocity of 50 unit per second on the player.
         * We can resume the disposition of the objects like this :
         *
         *
         * ////////////////////////////////////////////         y = 300
         * /                                          /
         * /                                          /
         * /                                          /
         * /                                          /
         * /                                          /
         * /                 Wall                     /
         * /                                          /
         * /                                          /
         * /                                          /
         * /                                          /
         * /                                          /
         * ////////////////////////////////////////////         y = 200
         *
         *
         *
         *
         *
         *
         *     ^
         *    / \
         *     |
         *     |
         *     |
         *
         * ////////////         y = 64
         * /          /
         * /  Player  /
         * /          /
         * ////////////         y = 0
         *
         *
         * x = 0
         *
         */


        // create two objects
        // a wall
        Wall wall = new Wall();
        wall.setHitbox(new Rectangle(0, 200, 100, 100));
        wall.setName("Wall");

        // a player
        Player player = new Player();
        player.setHitbox(new Rectangle(0, 0, 64, 64));
        player.setVelocity(new Point2D(0, 50)); // apply a velocity
        player.setName("Player");

        // create engine
        PhysicsEngine engine = new PhysicsEngine();
        engine.addObject(wall);
        engine.addObject(player);

        // the FPS
        final int FPS = 30;

        /*
         * We are at 50 units of distance per second and we are at 30 FPS.
         * We need to browse at least 134 units of distance.
         * So the engine must take 134 / 50 = 2.68 seconds before the collision with the wall.
         */
        long start = System.currentTimeMillis();

        // and run until the player is stopped
        while (player.getVelocity().getY() != 0) {

            // update the engine
            engine.update(1 / (float) FPS);

            // see if the player will hit the wall on the next update
            if(engine.collisionOnNextUpdate(player, 1 / (float) FPS) && player.getVelocity().getY() != 0)
                System.out.println("I will hit something on the next update :'(");

            // print the y position
            System.out.println("Player y position: " + player.getPosition().getY() + '\n');

            try {
                Thread.sleep(1000 / (long) FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Time before collision: " + (System.currentTimeMillis() - start) / 1000.f + "s");
        System.out.println("(The difference with 2.68s is because of the println)");
    }

}