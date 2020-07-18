package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import objects.Direction;
import objects.Point;
import objects.Snake;


public class Main extends Application {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    public static final double RADIUS = 25;
    private static final int FPS_BASE = (int) RADIUS / 3;
    private static final Paint WHITE = Paint.valueOf("White");
    private static final Color FOOD_COLOR = Color.RED;
    private static final Color SNAKE_COLOR = Color.LIGHTGREEN;
    private static final Paint BACKGROUND = Paint.valueOf("Black");
    private static int foodMove = 0;
    private static final int difficultyFood_base = 6;
    private static int difficultyFood = difficultyFood_base;
    public static int FPS = FPS_BASE;
    private int compte = 0;
    private Snake snake;
    public static int score;
    private int maxScore = 0;
    private boolean pause = false;
    private boolean difficulty = false;
    private boolean square = false;
    private int code = 0;


    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {

        snake = new Snake();
        var root = new BorderPane();


        var canvas = new Canvas(WIDTH, HEIGHT);
        var gc = canvas.getGraphicsContext2D();

        root.setCenter(canvas);
        var scene = new Scene(root, WIDTH, HEIGHT, Color.WHITESMOKE);

        keyPressed(scene);

        stage.setTitle("SNAKE");
        stage.setScene(scene);
        stage.show();

        AnimationTimer animationTimer = getAnimationTimer(gc);

        animationTimer.start();
    }

    private AnimationTimer getAnimationTimer(GraphicsContext gc) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!pause) {
                    compte++;
                    if (compte == FPS) {//FPS define the speed of the game
                        if (difficulty) {//the food will move if the difficulty is on
                            if (foodMove == difficultyFood) {
                                snake.foodMove();
                                foodMove = 0;
                            }
                            foodMove++;
                        }
                        gc.setFill(BACKGROUND);
                        gc.fillRect(0, 0, WIDTH, HEIGHT);
                        snake.isRunning();//Allow the snake to move and to eat
                        draw(gc);//draw the snake and the food
                        lose();//restart the party
                        compte = 0;

                    }
                } else {
                    gc.fillText("Paused", 30, 90);//when the p key is pressed the game is paused
                }

            }
        };
    }

    private void lose() {
        if (snake.lose()) {
            if (maxScore < score) {
                maxScore = score;
            }
            snake.lose();
            FPS = FPS_BASE;//the basic speed (if the snake eats, the speed increase)
            snake = new Snake();
        }
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(WHITE);
        gc.fillText("Score : " + score + "       Max Score : " + maxScore, 30, 30);
        gc.fillText("Difficulty Mode : " + textBool(difficulty), 30, 50);
        gc.fillText("Square mode : " + textBool(square), 30, 70);


        gc.setFill(FOOD_COLOR);

        gc.fillOval(snake.getFood().getX(), snake.getFood().getY(), RADIUS, RADIUS);
        gc.setFill(SNAKE_COLOR);
        for (Point point : snake.getPosition()) {
            if (!square) {
                gc.fillOval(point.getX(), point.getY(), RADIUS, RADIUS);
            } else {
                gc.fillRect(point.getX(), point.getY(), RADIUS, RADIUS);
            }
        }
    }


    private void keyPressed(Scene canvas) {


        canvas.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT:
                    if (snake.direction != Direction.Gauche)
                        snake.setDirection(Direction.Droite);
                    code = 0;
                    break;
                case LEFT:
                    if (snake.direction != Direction.Droite)
                        snake.setDirection(Direction.Gauche);
                    code = 0;
                    break;
                case DOWN:
                    if (snake.direction != Direction.Haut)
                        snake.setDirection(Direction.Bas);
                    code = 0;
                    break;
                case UP:
                    if (snake.direction != Direction.Bas)
                        snake.setDirection(Direction.Haut);
                    code = 0;
                    break;
                case D:
                    difficulty = !difficulty;
                    code = 0;
                    break;
                case P:
                    pause = !pause;
                    code = 0;
                    break;
                case C:
                    code++;
                    break;
                case H:
                    if (code == 1) {
                        code++;
                    } else {
                        code = 0;
                    }
                    break;
                case E:
                    if (code == 2) {
                        code++;
                    } else {
                        code = 0;
                    }
                    break;
                case A:
                    if (code == 3) {
                        code++;
                    } else {
                        code = 0;
                    }
                    break;
                case T:
                    if (code == 4) {
                        snake.eat();
                        code = 0;
                    } else {
                        code = 0;
                    }
                    break;
                case S:
                    square = !square;
                    break;


            }
        });

    }

    private String textBool(boolean bool) {
        if (bool)
            return "On";
        else
            return "Off";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
