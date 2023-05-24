package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * Factory to choose which strategy to give to a brick
 */
public class StrategyFactory {
    private final int MAX_DOUBLE_STRATEGIES = 3;
    private final int PUCK_STRATEGY = 0;
    private final int EXTRA_PUDDLE_STRATEGY = 1;
    private final int EXTRA_LIFE_STRATEGY = 2;
    private final int CAMERA_CHANGE_STRATEGY = 3;
    private final int DOUBLE_BEHAVIOR_STRATEGY = 4;
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a factory for choosing strategy for a brick.
     * @param gameObjects game object collection
     * @param imageReader reads image
     * @param soundReader reads sound
     * @param windowDimensions dimensions of the window
     * @param inputListener listen to user's input
     * @param windowController window controller
     * @param brickerGameManager the game manager for bricker game
     */
    public StrategyFactory(GameObjectCollection gameObjects,
                           ImageReader imageReader,
                           SoundReader soundReader,
                           Vector2 windowDimensions, UserInputListener inputListener,
                           WindowController windowController,
                           BrickerGameManager brickerGameManager) {
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Randomly choose a strategy.
     * first one: 1/6 probability any
     * if double: 1/5 any
     * if double again: 1/4 (without double)
     * else just takes the probabilities chosen.
     * @return
     */
    public CollisionStrategy chooseStrategy(){
        CollisionStrategy collisionStrategy = new BasicStrategy(gameObjects);

        int doubleStrategyCounter = 0;
        int rand = new Random().nextInt(6);
        if (!(rand == DOUBLE_BEHAVIOR_STRATEGY)) {
            collisionStrategy = mergeStrategies(rand, collisionStrategy);
        }
        else{
            int prevDouble = new Random().nextInt(5);
            if (prevDouble == DOUBLE_BEHAVIOR_STRATEGY){
                for(int j =0; j < MAX_DOUBLE_STRATEGIES; j++){
                    collisionStrategy = mergeStrategies(new Random().nextInt(4),
                            collisionStrategy);
                }
            }
            else{
                collisionStrategy = mergeStrategies(prevDouble, collisionStrategy);
                collisionStrategy = mergeStrategies(new Random().nextInt(4), collisionStrategy);
            }
        }
        return collisionStrategy;
    }

    /**
     * choose a strategy to decorate and imply to a brick.
     * @param strategyNum the randomly choosen number
     * @param strategy the strategy to decorate
     * @return the decorated strategy
     */
    public CollisionStrategy mergeStrategies(int strategyNum,
                                             CollisionStrategy strategy){
        switch (strategyNum){
            case PUCK_STRATEGY:
                return new PuckStrategy(strategy, imageReader, soundReader);
            case EXTRA_PUDDLE_STRATEGY:
                return new ExtraPaddleStrategy(strategy, imageReader,
                        inputListener, windowDimensions);
            case EXTRA_LIFE_STRATEGY:
                return new ExtraLifeStrategy(strategy, imageReader, brickerGameManager);
            case CAMERA_CHANGE_STRATEGY:
                return new ChangeCameraStrategy(strategy, windowController,
                        brickerGameManager);
            default:
                return new BasicStrategy(gameObjects);



        }

    }
}
