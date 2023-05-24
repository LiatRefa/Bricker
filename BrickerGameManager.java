package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BasicStrategy;
import src.brick_strategies.CollisionStrategy;
import src.brick_strategies.StrategyFactory;
import src.gameobjects.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * A Game manager for Bricker game. builds the game environment and
 * ends the game.
 */
public class BrickerGameManager extends GameManager {

    public static final float BORDER_WIDTH = 10.0f;
    private static final float BALL_SPEED = 250f;
    private static final int BRICKS_PER_ROW = 8;
    private static final int BRICKS_PER_COL = 7;
    private static final int BRICKS_DISTANCE = 1;
    private static final int WALL_DISTANCE = 5;
    private static final int LIVES = 3;
    private Counter brickCounter;
    private Counter livesCounter;
    private String windowTitle;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private GameObject ball;
    private UserInputListener inputListener;

    /**
     * Constructor for the game managar.
     * @param windowTitle
     * @param windowDimensions
     */
    public BrickerGameManager(java.lang.String windowTitle,
                               danogl.util.Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
        this.windowDimensions = windowDimensions;
        this.windowTitle = windowTitle;

    }

    /**
     * Starts the game.
     * @param imageReader
     * @param soundReader
     * @param inputListener
     * @param windowController
     */
    @Override
    public void initializeGame(danogl.gui.ImageReader imageReader, danogl.gui.SoundReader soundReader,
                               danogl.gui.UserInputListener inputListener,
                               danogl.gui.WindowController windowController){
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.brickCounter = new Counter(0);
        this.livesCounter = new Counter(LIVES);
        this.windowController = windowController;
        this.inputListener = inputListener;
        // Create Ball
        createBall(imageReader, windowController, soundReader);
;
        // Create Paddle
        createPaddle(imageReader, windowController, inputListener);
        // create bricks
        createBricks(imageReader, soundReader);
        // create background
        createBackground(imageReader, windowController);
        // create walls
        createWalls();
        // create lives
        createLife(imageReader);
        createTextLife();


    }

    private void createBall(ImageReader imageReader, WindowController windowController,
                            SoundReader soundReader){
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        GameObject ball = new Ball(new Vector2(Vector2.ZERO), new Vector2(20,20), ballImage, collisionSound);
        windowDimensions = windowController.getWindowDimensions();
        gameObjects().addGameObject(ball);
        setBallLocation(ball);
        this.ball = ball;
    }

    private void createLife(ImageReader imageReader){
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        GameObject graphicLifeCounter =
                new GraphicLifeCounter(new Vector2(BORDER_WIDTH + 5, windowDimensions.y() -40),
                        new Vector2(25,25), livesCounter, heartImage,gameObjects(), LIVES);
        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND);

    }


    private void createPaddle(ImageReader imageReader,
                              WindowController windowController, UserInputListener inputListener){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(200,20), paddleImage,
                inputListener, windowController.getWindowDimensions(), BORDER_WIDTH);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);
    }

    private void createBricks(ImageReader imageReader, SoundReader soundReader){
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        float brickSize = (windowDimensions.x() - ((BRICKS_PER_COL -1) * BRICKS_DISTANCE)
                - (2 * BORDER_WIDTH) - (2 * WALL_DISTANCE)) / BRICKS_PER_COL;
        StrategyFactory strategyFactory = new StrategyFactory(gameObjects(), imageReader,
                soundReader, windowDimensions, inputListener, windowController, this);
        for(int row =0; row < BRICKS_PER_ROW; row++){
            for(int col=0; col< BRICKS_PER_COL; col++){
                CollisionStrategy collisionStrategy = strategyFactory.chooseStrategy();
                GameObject brick = new Brick(Vector2.ZERO, new Vector2(brickSize, 15), brickImage,
                        collisionStrategy,brickCounter);
                brick.setCenter(new Vector2(BORDER_WIDTH + WALL_DISTANCE + (col * BRICKS_DISTANCE) +
                        (col * brickSize) + (brickSize/2),
                        (BORDER_WIDTH + BRICKS_DISTANCE + ( (float) 15/2) + row*BRICKS_DISTANCE + (row * 15))));
                gameObjects().addGameObject(brick,Layer.STATIC_OBJECTS);
                brickCounter.increment();
            }
        }
    }

    private void createBackground(ImageReader imageReader, WindowController windowController){
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",
                false);
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createWalls(){
        GameObject rightWall = new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                new RectangleRenderable(Color.BLACK));

        GameObject leftWall = new GameObject(new Vector2(windowDimensions.x()-BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                new RectangleRenderable(Color.BLACK));

        GameObject ceiling = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH),
                new RectangleRenderable(Color.BLACK));

        gameObjects().addGameObject(rightWall);
        gameObjects().addGameObject(leftWall);
        gameObjects().addGameObject(ceiling);

    }

    private void createTextLife() {
        GameObject numericLifeCounter = new NumericLifeCounter(livesCounter,
                new Vector2(BORDER_WIDTH + 5, windowDimensions.y() - 70),
                new Vector2(20,20),this.gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.BACKGROUND);
    }

    /**
     * Update when the game ends.
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkGameEnd();

    }
    private void checkGameEnd(){
        String msg = "";
        if(brickCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            msg = "WINNER! play again?";
            if(windowController.openYesNoDialog(msg)){
                windowController.resetGame();
            }
            else
                windowController.closeWindow();
        }
        else{
            if (windowDimensions.y() < ball.getCenter().y()){
                if(livesCounter.value() == 1){
                    msg = "YOU LOST! play again?";
                    if (windowController.openYesNoDialog(msg)) {
                        windowController.resetGame();
                    }
                    else{
                        windowController.closeWindow();
                    }
                }
                else{
                    livesCounter.decrement();
                    ball.setCenter(windowDimensions.mult(0.5f));
                }

            }
        }
    }


    private void setBallLocation(GameObject ball){
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX = ballVelX * -1;
        }
        if(rand.nextBoolean()){
            ballVelY = ballVelY * -1;
        }
        ball.setVelocity(new Vector2(ballVelX,ballVelY));
        ball.setCenter(windowDimensions.mult(0.5f));
    }

    public static void main(java.lang.String[] args){
        new BrickerGameManager("Bricker",
                                new Vector2(700,500)).run();

    }




}
