package controller;


public class Position {
    int x;
    int y;
    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }

    public static Position createPositionWithOffSet(Position position, Position offSetPosition) {
        return(new Position(position.x- offSetPosition.x,position.y-offSetPosition.y));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
