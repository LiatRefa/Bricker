package src.gameobjects;

import danogl.gui.Sound;
import danogl.util.Vector2;
import danogl.util.Counter;


public class Ball extends danogl.GameObject {

    private Counter collisionCounter;
    private final Sound collisionSound;

    /***
     * This is the ball object constructor.
     * It uses it's super's constructor and saves the sound file.
     * @param topLeftCorner position of the top left corner of the ball in the window.
     * @param dimensions  the dimensions of the ball
     * @param renderable the image object of the ball
     * @param sound the sound file object of the ball's collision.
     */
    public Ball(danogl.util.Vector2 topLeftCorner,
                 danogl.util.Vector2 dimensions,
                 danogl.gui.rendering.Renderable renderable,
                 danogl.gui.Sound sound){
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = sound;
        this.collisionCounter = new Counter(0);
    }

    /***
     * This method overwrites the OnCollisionEnter of GameObject.
     * When it collides with another object, it flips its direction.
     * @param other the object that the ball collided with
     * @param collision  the collision parameters
     */
    @Override
    public void onCollisionEnter(danogl.GameObject other, danogl.collisions.Collision collision){
        if(!(other instanceof Ball)){
            super.onCollisionEnter(other, collision);
            Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
            setVelocity(newVelocity);
            collisionSound.play();
            collisionCounter.increment();

        }
    }

    public int getCollisionCount(){
        return this.collisionCounter.value();
    }



}
