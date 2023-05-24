package src.gameobjects;

import danogl.gui.UserInputListener;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Paddle object to prevent the ball from falling
 */
public class Paddle extends danogl.GameObject {
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private float minDistFromEdge;
    public static final int MOVE_SPEED = 500;

    /**
     * Constructor for paddle.
     * @param topLeftCorner
     * @param dimensions
     * @param renderable
     * @param inputListener
     * @param windowDimensions
     * @param minDistFromEdge
     */
    public Paddle(danogl.util.Vector2 topLeftCorner,
                   danogl.util.Vector2 dimensions,
                   danogl.gui.rendering.Renderable renderable,
                   danogl.gui.UserInputListener inputListener,
                   danogl.util.Vector2 windowDimensions,
                   float minDistFromEdge){
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;

    }

    /**
     * moves the paddle according to input
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 moveDirection = Vector2.ZERO;

        //get player's input
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            moveDirection = moveDirection.add(Vector2.RIGHT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            moveDirection = moveDirection.add(Vector2.LEFT);
        }
        setVelocity(moveDirection.mult(MOVE_SPEED));

        // change location according to input
        Vector2 topLeftCorner = getTopLeftCorner();
        float rightEdge = windowDimensions.x() - minDistFromEdge - getDimensions().x();

        if (minDistFromEdge > topLeftCorner.x()){
            transform().setTopLeftCornerX(minDistFromEdge);
        }
        if (topLeftCorner.x() > rightEdge){
            transform().setTopLeftCornerX(rightEdge);
        }

    }
}
