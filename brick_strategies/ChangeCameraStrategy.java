package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.CollisionCameraCounter;
import src.gameobjects.Puck;

public class ChangeCameraStrategy extends AbstractCollision {
    private static final int COLLISIONS_TILL_OFF = 4;
    private final WindowController windowController;
    private GameManager gameManager;
    private CollisionCameraCounter collisionCameraCounter;

    /**
     * Constructor
     *  @param collisionStrategy
     * @param windowController
     * @param gameManager
     */
    public ChangeCameraStrategy(CollisionStrategy collisionStrategy, WindowController windowController, BrickerGameManager gameManager){
        super(collisionStrategy);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * When colision happens, camera focus on the ball for certain amount of moves
     * @param collidedObj the object that collided
     * @param colliderObj the object that collide the other one
     * @param bricksCounter global Bricks counter
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        if(colliderObj instanceof Ball &&
                (!(colliderObj instanceof Puck)) &&
                gameManager.getCamera() == null){
            gameManager.setCamera(
                    new Camera(
                            colliderObj, //object to follow
                            Vector2.ZERO, //follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
                            windowController.getWindowDimensions() //share the window dimensions
                    )
            );
            collisionCameraCounter = new
                    CollisionCameraCounter((Ball) colliderObj,
                    ((Ball) colliderObj).getCollisionCount() + COLLISIONS_TILL_OFF,
                    this);
            getObjectCollection().addGameObject(collisionCameraCounter);


        }

    }

    /**
     * Turns of the camera.
     */
    public void turnOffCamera(){
        gameManager.setCamera(null);
        getObjectCollection().removeGameObject(collisionCameraCounter);
    }
}
