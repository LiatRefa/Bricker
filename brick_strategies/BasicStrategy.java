package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicStrategy implements CollisionStrategy {

    private GameObjectCollection gameObjects;

    /**
     * Constructor for the class.
     * @param gameObjects all game objects in the game
     */
    public BasicStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    @Override
    /**
     * Removes the brick from the game objects when collided
     */
    public void onCollision(danogl.GameObject collidedObj, danogl.GameObject colliderObj,
                            danogl.util.Counter bricksCounter){
        if(gameObjects.removeGameObject(collidedObj, Layer.STATIC_OBJECTS)){
            bricksCounter.decrement();
        }

    }

    /**
     * Returns a copy of the game objects
     * @return the game objects' collection
     */
    @Override
    public GameObjectCollection getObjectCollection() {
        return gameObjects;
    }
}
