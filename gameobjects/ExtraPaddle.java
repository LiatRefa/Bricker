package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Extra Paddle for the ExtraPaddleStrategy. adds a mock paddle to board
 */
public class ExtraPaddle extends Paddle {
    private final Counter collisionsHappened;
    private int maxCollisions;
    private final GameObjectCollection gameObjects;
    public static boolean extraPaddleAppears = false;

    /**
     * Constructor for the mock paddle
     * @param topLeftCorner
     * @param dimensions
     * @param renderable
     * @param inputListener
     * @param windowDimensions
     * @param minDistFromEdge
     * @param gameObjects
     * @param maxCollisions
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions,
                       Renderable renderable, UserInputListener inputListener,
                       Vector2 windowDimensions, int minDistFromEdge,
                       GameObjectCollection gameObjects, int maxCollisions) {
        super(topLeftCorner, dimensions, renderable, inputListener,
                windowDimensions, minDistFromEdge);
        this.gameObjects = gameObjects;
        extraPaddleAppears = true;
        this.maxCollisions = maxCollisions;
        this.collisionsHappened = new Counter(0);

    }

    /**
     * remove the paddle after ceratain collisions
     * @param other
     * @param collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball){
            collisionsHappened.increment();
            if(this.collisionsHappened.value() == maxCollisions){
                extraPaddleAppears = false;
                gameObjects.removeGameObject(this);
            }
        }
    }

}
