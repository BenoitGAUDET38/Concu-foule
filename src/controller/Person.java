package controller;

import java.awt.*;
import java.util.Random;

public class Person{
    final Person [][] tab;
    Position startPosition;
    Position position;
    Position goal;
    Color color;
    Grid grid;
    // comptReset is used to check how much round since last destruction
    int comptReset=4;
    int id;

    public Person(Position position,Position goal,Grid grid,int id ){
        this.id=id;
        this.startPosition=position;
        this.position=new Position(position.x,position.y);
        this.goal=goal;
        this.tab=grid.tab;
        this.grid=grid;
        Random random = new Random();
        this.color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    public Person(Position position,Position goal, int id, Color color){
        this.id=id;
        this.startPosition=position;
        this.position=new Position(position.x,position.y);
        this.goal=goal;
        this.tab=grid.tab;
        this.grid=grid;
        Random random = new Random();
        this.color = color;
    }

    /**
     * check comptReset to know if the Person is still destroyed or not after that make move the Person.
     * @return true if he has made a choice, return false if he reached his goal
     */
    public boolean makeChoice() throws InterruptedException {
        Thread.sleep(SuperController.TIME_TO_SLEEP);

        comptReset++;
        if (comptReset<3) {
            return true;
        }
        else if (comptReset==3){
            grid.putPerson(this);
        }


        if (comptReset!=0 && !makeMooveLine()) if(!makeMoveColon()) {
            grid.finishGame(position);
            return false;
        }
        return true;
    }


    public boolean makeMooveLine() {
        Person neighboor;
        if (position.x== goal.x)
            return false;
        int move=1;
        if (position.x > goal.x)
            move = -1;

        neighboor=grid.tab[position.y][position.x+move];
        if (clearTheWay(neighboor)){
        grid.moveInGrid(position,new Position(position.x+move, position.y),this);
        position.x+=move;}
        return true;
    }


    public boolean makeMoveColon() {
        Person neighboor;
        if (position.y == goal.y)
            return false;

        int move = 1;
        if (position.y > goal.y)
            move = -1;

        neighboor=grid.tab[position.y+move][position.x];
        if (clearTheWay(neighboor)){
            grid.moveInGrid(position,new Position(position.x, position.y+move),this);
            position.y+=move;}
        return true;
    }

    /**
     * remove himself from the grid and will restart at his initial position in 3 rounds
     */
    void destroy() {
        grid.deletePerson(position);
        position=new Position(startPosition.x,startPosition.y);
        comptReset=0;
        System.out.println("DESTROYED " + id);
    }

    /**
     * decide who is going to be destroyed
     * @param neighboor
     * @return
     */
    public boolean clearTheWay(Person neighboor){
        if (neighboor==null)
            return true;
        else if (neighboor.id<this.id)
        {
            return false;
        }
        else
        {this.destroy();
            return false;}
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public Position getGoal() {
        return goal;
    }

    @Override
    public String toString() {
        return "Person{" +
                "startPosition=" + startPosition +
                ", goal=" + goal +
                ", id=" + id +
                '}';
    }
}