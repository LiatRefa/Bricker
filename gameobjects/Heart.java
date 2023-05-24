package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * an Heart.
 */
public class Heart extends GameObject {
    private final GameObjectCollection gameObjects;
    public static boolean extraLife = false;
    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameObjects
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
    }

    /**
     * if collided, gives extra hp
     * @param other
     * @param collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        extraLife = true;
        gameObjects.removeGameObject(this);
    }

    /**
     * to make sure only collides with paddle
     * @param other
     * @return
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other instanceof Paddle && (!(other instanceof ExtraPaddle)))
            return super.shouldCollideWith(other);
        return false;
    }
}
