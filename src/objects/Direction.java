package objects;

public enum Direction {
    Haut(0),
    Bas(1),
    Gauche(2),
    Droite(3);

    private final int number;

    Direction(int number) {
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }

}
