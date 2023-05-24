package src.gameobjects;

import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;

public class Brick extends GameObject {

    private final Counter brickCounter;
    private CollisionStrategy collisionStrategy;

    /**
     * Constructor for a brick.
     * @param topLeftCorner
     * @param dimensions
     * @param renderable
     * @param strategy
     * @param counter
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 CollisionStrategy strategy,
                 Counter counter){
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = strategy;
        this.brickCounter = counter;
    }

    /**
     * Apply colission strategy on a brick
     * @param other
     * @param collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, brickCounter);
    }
}
