package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.ExtraPaddle;


public class ExtraPaddleStrategy extends AbstractCollision {
    private static final float EXTRA_X_SIZE = 200;
    private static final float EXTRA_Y_SIZE = 20;
    private static final int MAX_COLLISIONS = 3;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;



    /**
     * Constructor
     * @param collisionStrategy
     * @param imageReader
     * @param inputListener
     * @param windowDimensions
     */
    public ExtraPaddleStrategy(CollisionStrategy collisionStrategy, ImageReader imageReader,
                               UserInputListener inputListener, Vector2 windowDimensions) {
        super(collisionStrategy);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;

    }

    @Override
    public GameObjectCollection getObjectCollection() {
        return super.getObjectCollection();
    }

    /**
     * Adds an Extra paddle same size as normal one that can collide certain amount of time
     * @param collidedObj the object that collided
     * @param colliderObj the object that collide the other one
     * @param bricksCounter global Bricks counter
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj,
                            Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        Renderable extraPaddleImg = imageReader.readImage("assets/botGood.png", false);
        if(!ExtraPaddle.extraPaddleAppears){
            Vector2 centerPaddle = new Vector2(windowDimensions.x()/2, windowDimensions.y()/2);
            Vector2 sizePaddle = new Vector2(EXTRA_X_SIZE, EXTRA_Y_SIZE);
            ExtraPaddle extraPaddle = new ExtraPaddle(centerPaddle, sizePaddle, extraPaddleImg,inputListener,
                    windowDimensions, 20, getObjectCollection(), MAX_COLLISIONS);
            getObjectCollection().addGameObject(extraPaddle);
        }


    }
}
