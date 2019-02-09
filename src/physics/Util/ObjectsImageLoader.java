package physics.Util;

import javafx.scene.shape.Rectangle;
import physics.PhysicsEngine;
import physics.objects.PhysicObject;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is useful to load a pattern image and get physical objects from it.
 * @author Tom Befieux
 *
 */
public abstract class ObjectsImageLoader {

    private int[] colorsOfObjects;                        /** The colors representing objects in pattern images. */

    /**
     * Constructor of the loader.
     * @param colorsOfObjects: the list of colors that are in the pattern images
     */
    public ObjectsImageLoader(int[] colorsOfObjects) {
        this.colorsOfObjects = colorsOfObjects;
    }

    /**
     * This function create an engine based on a pattern image.
     * This image must be a grayscale image and the objects must be represented by rectangles of a specific color.
     * A color represents an objects.
     * @param image: the grayscale image
     * @return an engine with the objects of the pattern image loaded
     */
    public PhysicsEngine getEngineWithPatternImage(BufferedImage image) {
        PhysicsEngine engine = new PhysicsEngine();

        for (PhysicObject object: getObjectsFromPatternImage(image))
            engine.addObject(object);

        return engine;
    }

    /**
     * This function loads physical objects from a pattern image.
     * This image must be a grayscale image and the objects must be represented by rectangles of a specific color.
     * A color represents an objects.
     * @param image: the grayscale image
     * @return the list of the physical objects found in the pattern image.
     */
    public List<PhysicObject> getObjectsFromPatternImage(BufferedImage image) {
        List<PhysicObject> result = new ArrayList<>();

        for(int color : this.colorsOfObjects) {
            List<Rectangle> rectangles = getRectanglesOfColor(image, color);

            for (Rectangle hitbox : rectangles) {
                PhysicObject objectLoaded = getObjectFromColor(color);
                objectLoaded.setHitbox(hitbox);
                result.add(objectLoaded);
            }
        }

        return result;
    }

    /**
     * This function returns all the rectangles of a specific color found in a grayscale image.
     * @param image: the grayscale image
     * @param color: the color of the rectangles (0 to 255)
     * @return the list of the rectangles found
     */
    private List<Rectangle> getRectanglesOfColor(BufferedImage image, int color) {
        List<Rectangle> result = new ArrayList<>();

        Raster raster = image.getData();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                // if we are not already in a rectangle
                boolean alreadyInRectangle = false;
                for (Rectangle rectangle : result) {
                    if(
                        i >= rectangle.getX() && i <= rectangle.getX() + rectangle.getWidth() &&
                        j >= rectangle.getY() && j <= rectangle.getY() + rectangle.getHeight()
                    )
                        alreadyInRectangle = true;
                }

                if(!alreadyInRectangle) {
                    int pixel = raster.getSample(i, j, 0);

                    // we've match with the color
                    if (pixel == color) {
                        // find the width
                        boolean findWidth = false;
                        int width = 1;
                        for (int k = i + 1; !findWidth && k < image.getWidth(); k++) {
                            if (raster.getSample(k, j, 0) != color)
                                findWidth = true;
                            else
                                width++;
                        }

                        // find the height
                        boolean findHeight = false;
                        int height = 1;
                        for (int k = j + 1; !findHeight && k < image.getHeight(); k++) {
                            if (raster.getSample(i, k, 0) != color)
                                findHeight = true;
                            else
                                height++;
                        }

                        // add it
                        result.add(new Rectangle(i, j, width, height));

                        // jump after the rectangle
                        j += height;
                    }
                }
            }
        }

        return result;
    }

    /**
     * This function returns a new physical object created according to the color.
     * @param color: the color representing the object
     * @return the object created
     */
    protected abstract PhysicObject getObjectFromColor(int color);

    /**
     * Setter for the list of colors.
     * @param colorsOfObjects: the new colors
     */
    public void setColorsOfObjects(int[] colorsOfObjects) {
        this.colorsOfObjects = colorsOfObjects;
    }
}