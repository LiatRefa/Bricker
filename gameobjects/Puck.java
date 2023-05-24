package src.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball {
    /***
     * This is the ball object constructor.
     * It uses it's super's constructor and saves the sound file.
     * @param topLeftCorner position of the top left corner of the ball in the window.
     * @param dimensions  the dimensions of the ball
     * @param renderable the image object of the ball
     * @param sound the sound file object of the ball's collision.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound sound) {
        super(topLeftCorner, dimensions, renderable, sound);
    }
}
