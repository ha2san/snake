package objects;

import gui.Main;

import java.util.ArrayList;
import java.util.List;

public class Corps {
    private final List<Point> corps;

    public Corps(int pos_x, int pos_y) {
        corps = new ArrayList<>();
        corps.add(new Point(pos_x, pos_y));
    }

    public int size() {
        return corps.size();
    }

    public boolean lose() {
        if (Main.invinsible){
            if(corps.get(0).getX() < 0) corps.get(0).setX(Main.WIDTH-1); ;
            if(corps.get(0).getY() < 0) corps.get(0).setY(Main.HEIGHT-1); ;
            if(corps.get(0).getX() >= Main.WIDTH) corps.get(0).setX(0); ;
            if(corps.get(0).getY() >= Main.HEIGHT) corps.get(0).setY(0); ;
            return false;
        }

        for (int i = 1; i < corps.size(); i++) {
            if (corps.get(0).equals(corps.get(i))) {
                return true;
            }
        }

        return corps.get(0).getX() < 0 || corps.get(0).getX() >= Main.WIDTH ||
                corps.get(0).getY() < 0 || corps.get(0).getY() >= Main.HEIGHT;

    }

    public List<Point> position() {
        return List.copyOf(corps);
    }


    public void mange(Direction direction) {
        double x = 0;
        double y = 0;

        switch (direction) {
            case Droite -> x = 2 * Main.RADIUS;
            case Bas -> y = -2 * Main.RADIUS;
            case Haut -> y = 2 * Main.RADIUS;
            case Gauche -> x = -2 * Main.RADIUS;
        }
        Point point = Point.Point(corps.get(corps.size() - 1), 2 * x, 2 * y);
        corps.add(point);
        Main.score++;
    }

    public void avance(Direction direction) {
        corps.get(0).avance(direction);
        for (int i = 1; i < corps.size(); i++) {
            corps.get(i).suit(corps.get(i - 1));
        }
    }
}
