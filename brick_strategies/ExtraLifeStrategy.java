package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.Heart;


public class ExtraLifeStrategy extends AbstractCollision {
    private final ImageReader imageReader;

    /**
     * Constructor
     *
     * @param collisionStrategy
     * @param imageReader
     */
    public ExtraLifeStrategy(CollisionStrategy collisionStrategy, ImageReader imageReader, BrickerGameManager gameManager) {
        super(collisionStrategy);
        this.imageReader = imageReader;
    }

    @Override
    public GameObjectCollection getObjectCollection() {
        return super.getObjectCollection();
    }

    /**
     * On colision creates a heart the falls
     * @param collidedObj the object that collided
     * @param colliderObj the object that collide the other one
     * @param bricksCounter global Bricks counter
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);

        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        Heart heart = new Heart(collidedObj.getCenter(), new Vector2(20,20), heartImage, getObjectCollection());
        float heartVelocity = 100;
        heart.setVelocity(new Vector2(0, heartVelocity));
        this.getObjectCollection().addGameObject(heart);

    }
}
