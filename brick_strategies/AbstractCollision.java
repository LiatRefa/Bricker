package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * An abstract class for collision - cannot be created but all strategies will implement the
 * presented methods. Other collisions will be decorated
 */
public abstract class AbstractCollision implements CollisionStrategy {

    private final CollisionStrategy collisionStrategy;

    /**
     * Constructor
     * @param collisionStrategy
     */
    public AbstractCollision(CollisionStrategy collisionStrategy){
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * delegates what happen in case of collision
     * @param collidedObj the object that collided
     * @param colliderObj the object that collide the other one
     * @param bricksCounter global Bricks counter
     */
    @Override
    public void onCollision(danogl.GameObject collidedObj, danogl.GameObject colliderObj,
                     danogl.util.Counter bricksCounter){
        collisionStrategy.onCollision(collidedObj, colliderObj, bricksCounter);
    }

    /**
     * All game objects currently in game
     * @return the game objects collection
     */
    @Override
    public GameObjectCollection getObjectCollection() {
        return collisionStrategy.getObjectCollection();
    }
}
