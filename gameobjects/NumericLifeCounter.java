package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A text to display hp.
 */
public class NumericLifeCounter extends GameObject {
    private GameObjectCollection gameObjectCollection;
    private  static TextRenderable textRenderable = new TextRenderable("");
    private Counter livesCounter;

    /**
     * Constructor for the text
     * @param livesCounter
     * @param topLeftCorner
     * @param dimensions
     * @param gameObjectCollection
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter,
                               danogl.util.Vector2 topLeftCorner,
                               danogl.util.Vector2 dimensions,
                               danogl.collisions.GameObjectCollection gameObjectCollection){
        super(topLeftCorner, dimensions, textRenderable);
        this.livesCounter = livesCounter;
        textRenderable.setString(String.format("HP: %d", this.livesCounter.value()));
        if (this.livesCounter.value() == 3)
            textRenderable.setColor(Color.green);
        GameObject textLife = new GameObject(topLeftCorner, dimensions, textRenderable);
        this.gameObjectCollection = gameObjectCollection;
        this.gameObjectCollection.addGameObject(textLife, Layer.UI);
    }

    /**
     * Updates the color of the text
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(String.format("HP: %d", this.livesCounter.value()));
        if (this.livesCounter.value() >= 3)
            textRenderable.setColor(Color.green);
        if (this.livesCounter.value() == 2)
            textRenderable.setColor(Color.yellow);
        if (this.livesCounter.value() == 1)
            textRenderable.setColor(Color.red);
    }
}
