package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;
import danogl.collisions.GameObjectCollection;

public class GraphicLifeCounter extends GameObject {
    private int numOfLives;
    private final Counter livesCounter;
    private final Renderable renderable;
    private final Vector2 widgetTopLeftCorner;
    private final Vector2 widgetDimensions;
    private final GameObjectCollection gameObjectsCollection;
    private GameObject[] hearts;


    /**
     * Constructor for the life counter.
     * @param widgetTopLeftCorner
     * @param widgetDimensions
     * @param livesCounter
     * @param renderable
     * @param gameObjectsCollection
     * @param numOfLives
     */
    public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner,
                               danogl.util.Vector2 widgetDimensions,
                               danogl.util.Counter livesCounter,
                               danogl.gui.rendering.Renderable renderable,
                               danogl.collisions.GameObjectCollection gameObjectsCollection,
                               int numOfLives){
        super(widgetTopLeftCorner, widgetDimensions, renderable);
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;
        this.renderable = renderable;
        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.widgetDimensions = widgetDimensions;
        displayLifeGraphics(widgetTopLeftCorner, widgetDimensions, renderable);

    }

    /**
     * Draw the hearts on board
     * @param widgetTopLeftCorner
     * @param widgetDimensions
     * @param renderable
     */
    private void displayLifeGraphics(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Renderable renderable){
        hearts = new GameObject[numOfLives];
        for(int i = 0; i < numOfLives; i++){
            Vector2 location = new Vector2(widgetTopLeftCorner.x() + (i * (5 + widgetDimensions.y())),
                    widgetTopLeftCorner.y());
            GameObject heart = new GameObject(location, widgetDimensions, this.renderable);
            hearts[i] = heart;
            this.gameObjectsCollection.addGameObject(hearts[i], Layer.BACKGROUND);
        }

    }

    /**
     * Increase when get an extra life, decrease when balls out of bounds
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(numOfLives > livesCounter.value()){
            numOfLives--;
            this.gameObjectsCollection.removeGameObject(hearts[numOfLives], Layer.BACKGROUND);
        }
        if (livesCounter.value() < 4 && Heart.extraLife){
            livesCounter.increment();
            for(int i = 0; i < hearts.length; i++){
                this.gameObjectsCollection.removeGameObject(hearts[i], Layer.BACKGROUND);
            }
            numOfLives++;
            displayLifeGraphics(widgetTopLeftCorner, widgetDimensions, renderable);
            Heart.extraLife = false;

        }

    }
}
