package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;


/**
 * Creates 3 Pucks when a brick breaks.
 */
public class PuckStrategy extends AbstractCollision {
    private Puck puck;
    private static final int PUCK_NUM = 3;
    private static final float PUCK_VELO = 250;
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * Constructor
     *
     * @param collisionStrategy
     */
    public PuckStrategy(CollisionStrategy collisionStrategy, ImageReader imageReader,
                        SoundReader soundReader) {
        super(collisionStrategy);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Creates 3 pucks in the center of collided brick
     * @param collidedObj the object that collided
     * @param colliderObj the object that collide the other one
     * @param bricksCounter global Bricks counter
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        Renderable puckImage = imageReader.readImage("assets/mockBall.png", true);
        Sound puckSound = soundReader.readSound("assets/blop_cut_silenced.wav");

        for (int i = 0; i < PUCK_NUM; i++) {
            puck = new Puck(collidedObj.getCenter(),
                    new Vector2(collidedObj.getDimensions().x() / 3,
                            collidedObj.getDimensions().x() / 3),
                    puckImage, puckSound);
            setPuckVelocity(puck);
            this.getObjectCollection().addGameObject(puck);

        }
    }

    /**
     * Give a puck random direction
     * @param puck
     */
    private void setPuckVelocity(Puck puck) {
        float puckVelocityX = PUCK_VELO;
        float puckVelocityY = PUCK_VELO;
        Random rand = new Random();
        if (rand.nextBoolean())
            puckVelocityX *= -1;
        if (rand.nextBoolean())
            puckVelocityY *= -1;
        puck.setVelocity(new Vector2(puckVelocityX, puckVelocityY));
    }


}
