package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * A general interface for collision with a brick,
 * contains 2 method each class should hold: what to do in case of collision,
 * Also, a reference and getter for the global Game Object Collection.
 */

public interface CollisionStrategy {
    void onCollision(GameObject collidedObj, GameObject colliderObj, Counter counter);
    GameObjectCollection getObjectCollection();
}
