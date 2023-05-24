package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * follow how many times the ball collide while cameraStrategy is on to turn off the camera.
 */
public class CollisionCameraCounter extends GameObject {
    private final Ball ball;
    private final int collisions;
    private final ChangeCameraStrategy cameraStrategy;

    /**
     * Constructor for the counter of the cameraStrategy
     * @param ball
     * @param collisions
     * @param cameraStrategy
     */
    public CollisionCameraCounter(Ball ball, int collisions, ChangeCameraStrategy cameraStrategy) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.collisions = collisions;
        this.cameraStrategy = cameraStrategy;

    }

    /**
     * Turns off camera after cerain collisions
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(ball.getCollisionCount() > collisions){
            cameraStrategy.turnOffCamera();
            System.out.println(ball.getCollisionCount());

        }

    }
}
