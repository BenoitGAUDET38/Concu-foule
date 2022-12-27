package controller;

import ihm.GUI;

public class Grid {
    final Person [][] tab;
    int height;
    int width;
    GUI gui;
    Position offSetPosition;


    public Grid(int height,int width,Position offSetPosition){
        this.offSetPosition=offSetPosition;
        this.height = height;
        this.width = width;
        this.tab = new Person[height][width];
    }
    public void moveInGrid(Position start, Position arrival, Person person){
        start=Position.createPositionWithOffSet(start,offSetPosition);
        arrival=Position.createPositionWithOffSet(arrival,offSetPosition);
        tab[start.y][start.x]=null;
        tab[arrival.y][arrival.x]=person;

        if (Connector.DISPLAY)
            gui.updatePersonPosition(person,start,arrival);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public Person[][] getTab() {
        return tab;
    }

    public void deletePerson(Position position) {
        position=Position.createPositionWithOffSet(position,offSetPosition);

        tab[position.y][position.x]=null;

        if (Connector.DISPLAY)
            gui.deletePersonByPosition(position);
    }
    public void finishGame(Position position) {
        position=Position.createPositionWithOffSet(position,offSetPosition);

        tab[position.y][position.x]=null;

        if (Connector.DISPLAY)
            gui.finishGame(position);
    }

    public void putPerson(Person person) {
        Position personPosition=Position.createPositionWithOffSet(person.getPosition(),offSetPosition);

        Person neighboor=tab[personPosition.y][personPosition.x];
        if (neighboor!=null)
        {
            person.destroy(this);
        }
        else
        {
            tab[personPosition.y][personPosition.x]=person;

            if (Connector.DISPLAY)
                gui.putPerson(person);
        }
    }

    public Person getPerson(Position position) {
        position=Position.createPositionWithOffSet(position,offSetPosition);

        return(tab[position.y][position.x]);
    }
}
