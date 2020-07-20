package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import objects.Direction;
import objects.Point;
import objects.Snake;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    public static final double RADIUS = 25;
    private static final int FPS_BASE = (int) RADIUS / 3;
    private static final Paint WHITE = Paint.valueOf("White");
    private static final Color FOOD_COLOR = Color.RED;
    private static final Color SNAKE_COLOR = Color.LIGHTGREEN;
    private static final Paint BACKGROUND = Paint.valueOf("Black");
    private static final int FPS_CHEAT = 30;
    private static final int FPS_SPEED = 2;
    private static int foodMove = 0;
    private static final int difficultyFood_base = 6;
    private static final int difficultyFood = difficultyFood_base;
    public static int FPS = FPS_BASE;
    public StringBuilder stringBuilder = new StringBuilder();
    private int compte = 0;
    private Snake snake;
    public static int score;
    private int maxScore = 0;
    private boolean pause = false;
    private boolean difficulty = false;
    private boolean square = false;
    private boolean cheatCode = false;
    public static boolean invinsible = false;
    private final List<String> codeList = new ArrayList<>();
    private boolean slowmo = false;
    private boolean speed = false;
    private boolean changeSize = false;


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
                    gc.fillText("Paused", 30, 110);//when the p key is pressed the game is paused
                }

            }
        };
    }

    private void lose() {
        if (snake.lose()) {
            if (maxScore < score) {
                maxScore = score;
            }
            speed = false;
            slowmo = false;
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
        gc.fillText("Cheat Code  " + textBool(cheatCode), 30, 90);
        gc.fillText(stringBuilder.toString(), 300, 30);


        int i = 0;
        for (String s : codeList) {
            gc.fillText(s, 300, 50 + i * 20);
            i++;
        }

        gc.setFill(FOOD_COLOR);

        gc.fillOval(snake.getFood().getX(), snake.getFood().getY(), RADIUS, RADIUS);
        gc.setFill(SNAKE_COLOR);
        double i1 = 0;
        for (Point point : snake.getPosition()) {
            changeColor(gc, i1);
            double x = point.getX();
            double y = point.getY();
            if (changeSize) {
                switch (snake.direction) {
                    case Gauche:
                        y += (RADIUS - size(i1, true)) / 2;
                        x += (RADIUS - size(i1, changeSize)) / 2;
                        break;
                    case Haut:
                    case Bas:
                        x += (RADIUS - size(i1, true)) / 2;
                        y += (RADIUS - size(i1, changeSize)) / 2;
                        break;
                    case Droite:
                        y += (RADIUS - size(i1, true)) / 2;
                        break;
                }

            }
            if (!square) {
                gc.fillOval(x, y, size(i1, changeSize), size(i1, changeSize));
            } else {
                gc.fillRect(x, y, size(i1, changeSize), size(i1, changeSize));
            }
            i1++;

        }
    }

    private void changeColor(GraphicsContext gc, double i1) {
        double red = color(SNAKE_COLOR.getRed(), i1, snake.getPosition().size());
        double green = color(SNAKE_COLOR.getGreen(), i1, snake.getPosition().size());
        double blue = color(SNAKE_COLOR.getBlue(), i1, snake.getPosition().size());
        gc.setFill(Color.color(red, green, blue));
    }


    private void keyPressed(Scene canvas) {


        canvas.setOnKeyPressed(event -> {
            if (cheatCode) {
                if (!event.getCode().equals(KeyCode.ENTER))
                    stringBuilder.append(event.getText());
            }
            switch (event.getCode()) {
                case RIGHT:
                    if (snake.direction != Direction.Gauche)
                        snake.setDirection(Direction.Droite);
                    break;
                case LEFT:
                    if (snake.direction != Direction.Droite)
                        snake.setDirection(Direction.Gauche);
                    break;
                case DOWN:
                    if (snake.direction != Direction.Haut)
                        snake.setDirection(Direction.Bas);
                    break;
                case UP:
                    if (snake.direction != Direction.Bas)
                        snake.setDirection(Direction.Haut);
                    break;
                case D:
                    difficulty = !difficulty;
                    break;
                case P:
                    if (!cheatCode)
                        pause = !pause;
                    break;
                case S:
                    square = !square;
                    break;
                case SPACE:
                    changeSize = !changeSize;
                    break;
                case C:
                    cheatCode = !cheatCode;
                    if (!cheatCode)
                        stringBuilder = new StringBuilder();
                case ENTER:

                    switch (stringBuilder.toString()) {
                        case "grow":
                            snake.eat();
                            break;
                        case "slowmo":
                            compte = 0;
                            slowmo = !slowmo;
                            if (slowmo)
                                FPS = FPS_CHEAT;
                            else
                                FPS = FPS_BASE;
                            break;
                        case "speed":
                            compte = 0;
                            speed = !speed;
                            if (speed)
                                FPS = FPS_SPEED;
                            else
                                FPS = FPS_BASE;
                            break;
                        case "fps":
                            compte = 0;
                            FPS = FPS_BASE;
                            break;
                        case "invinsible":
                            invinsible = !invinsible;
                    }
                    mode(slowmo, "Mode Slow-motion activated");
                    mode(speed, "Mode speed activated");
                    mode(invinsible, "Mode invinsible activated");
                    stringBuilder = new StringBuilder();
                    break;
                default:
                    break;


            }
        });

    }

    private void mode(boolean bool, String mode) {
        if (bool) {
            if (!codeList.contains(mode)) {
                codeList.add(mode);
            }
        } else
            codeList.remove(mode);
    }

    private String textBool(boolean bool) {
        if (bool)
            return "On";
        else
            return "Off";
    }

    private double color(double color,double i1,double max){
        return color * (1-(i1/max)*(color-1)/(color));
    }

    private double size(double i, boolean bool) {
        if (bool)
            return RADIUS * (0.5 * (2 - i / snake.getPosition().size()));
        else
            return RADIUS;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
