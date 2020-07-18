package objects;

import gui.Main;

import java.util.Objects;

public class Point {

    private double x;
    private double y;
    private Point anciennePos;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        anciennePos = this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    public static Point Point(Point point, double x, double y) {
        return new Point(point.getX() + x, point.getY() + y);
    }

    public void move() {
        double x;
        double y;


        if (Math.random() < 0.5) {
            x = 0;
            y = Main.RADIUS;
            y = Math.random() < 0.5 ? -y : y;
        } else {
            y = 0;
            x = Main.RADIUS;
            x = Math.random() < 0.5 ? -x : x;
        }


        this.x += x;
        this.y += y;
        if (this.x < 0)
            this.x += Main.RADIUS;
        if (this.y < 0)
            this.y += Main.RADIUS;
        if (this.x >= Main.WIDTH)
            this.x -= Main.RADIUS;
        if (this.y >= Main.HEIGHT)
            this.y -= Main.RADIUS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Point newPoint(Point point) {
        return new Point(point.getX(), point.getY());
    }


    public void avance(Direction direction) {
        anciennePos = newPoint(this);
        nouvellePosition(direction);
    }

    private void nouvellePosition(Direction direction) {
        switch (direction.getNumber()) {
            case 0:
                this.y -= Main.RADIUS;
                break;
            case 1:
                this.y += Main.RADIUS;
                break;
            case 2:
                this.x -= Main.RADIUS;
                break;
            case 3:
                this.x += Main.RADIUS;
                break;
        }
    }

    public void suit(Point point) {
        anciennePos = newPoint(this);
        x = point.anciennePos.getX();
        y = point.anciennePos.getY();
    }
}

