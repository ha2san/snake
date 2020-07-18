package objects;


import gui.Main;

import java.util.List;

public class Snake {
    public Direction direction;
    private final Corps corps;

    public Point getFood() {
        return food;
    }

    private Point food;


    public Snake() {
        this.direction = Direction.Droite;
        this.corps = new Corps(0, 0);
        double x = ((int) (Math.random() * Main.WIDTH / Main.RADIUS)) * Main.RADIUS;
        double y = ((int) (Math.random() * Main.WIDTH / Main.RADIUS)) * Main.RADIUS;
        food = new Point(x, y);
        Main.score = corps.size();
    }

    public boolean lose() {
        return corps.lose();
    }


    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void foodMove() {
        this.food.move();
        System.out.printf("x : %f, y: %f %n", food.getX(), food.getY());
    }

    public void mange() {
        if (corps.position().get(0).equals(food)) {
            eat();
            double x = ((int) (Math.random() * Main.WIDTH / Main.RADIUS)) * Main.RADIUS;
            double y = ((int) (Math.random() * Main.WIDTH / Main.RADIUS)) * Main.RADIUS;
            food = new Point(x, y);
            Main.score = corps.size();
            if (Main.FPS != 2 && corps.size() % 3 == 0)
                Main.FPS--;
        }
    }

    public void eat() {
        corps.mange(this.direction);

    }


    public List<Point> getPosition() {
        return corps.position();
    }

    public void avance() {
        corps.avance(this.direction);
    }

}
