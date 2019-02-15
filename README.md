# 2D Physics Engine

A very basic 2D physics engine in JAVA with rectangular hit box.
You can use it if you want to develop simple games like Pacman for instance.
The origin of the engine is the top-left corner.

## How does it work ?
The project contains physical objects and the physics engine itself.

### The physical objects
The physical objects represent rectangular objects for the physics engine.
There are two types of object:
* Simple objects: Objects that don't move
* Entities: Simple objects that move when you apply a velocity to them.

### The physics engine
The physics engine applies the changes to the objects (as moving the entities according to their velocities) and detects the collisions.


## Getting Started
Copy the physics package of this repository in your project to use it. Then you have to create your game objects and create your engine. You have two options to create an engine.

### Create your objects
Create your game objects extended of the PhysicObject or of the PhysicEntity class. When you extended these classes you have to implement
this function:
```
public void collisionTriggeredOnSide(Side side, PhysicObject object);
```
This function is called when a collision happens with another physical object. The side of the object where the collision happened and the
other object with which the collision happened is given. With this method you can easily write the actions to perform when there is a collision
with a specific type of object.

### First option: create an engine with a pattern image
You can use a pattern image to load a map easily. A pattern image is an image in grayscale containing colored rectangles. One rectangle represents
an object, depending of its color. So you can store 255 types of objects in a pattern image and you don't have limit concerning its size.


#### 1) Implement a loader
To load a pattern image, you need to create a class extended of the abstract class ObjectsImageLoader. In this new class you need to give the list
of colors that are used in the pattern image. You also need to implement the function named "getObjectFromColor". In this function you just need
to return a new object for a specific color value. For more details look the MyImagePatternLoader class of the project.

#### 2) Use your loader
We you've created it, you can instance your loader and use the getEngineWithPatternImage function that take a buffered image in parameter (the pattern image).
This function gives you directly the engine corresponding to your pattern image!

### Second option: create an engine from scratch
Create a physics engine object and add all the physical objects that you've created into it. 

### You're done!
Update the engine in the game loop with the correct delta (delta is the time in seconds passed between this update and the previous one).
You don't need to do more. The engine will update the objects and will call the function of collision of each object when they will happen! 
This repository contains an example with a wall and a player to help you to understand. You can choose in the example, if you want to use
a pattern image or not to create the engine.

TIPS : you can override the update function of the physical objects to add some stuff to do in each update.

## Contributing
Feel free to contribute to this project!

## Contributors
* [Tom Befieux](https://github.com/tombefieux)
