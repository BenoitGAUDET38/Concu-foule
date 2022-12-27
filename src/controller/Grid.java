package controller;

import ihm.GUI;

public class Grid {
    final Person [][] tab;
    int height;
    int width;
    GUI gui;


    public Grid(int height,int width){
        this.height = height;
        this.width = width;
        this.tab = new Person[height][width];
    }
    public void moveInGrid(Position start, Position arrival, Person person){
        tab[start.y][start.x]=null;
        tab[arrival.y][arrival.x]=person;

        if (SuperController.DISPLAY)
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
        tab[position.y][position.x]=null;

        if (SuperController.DISPLAY)
            gui.deletePersonByPosition(position);
    }
    public void finishGame(Position position) {
        tab[position.y][position.x]=null;

        if (SuperController.DISPLAY)
            gui.finishGame(position);
    }

    public void putPerson(Person person) {
        Person neighboor=tab[person.position.y][person.position.x];
        if (neighboor!=null)
        {
            person.destroy(this);
        }
        else
        {
            tab[person.position.y][person.position.x]=person;

            if (SuperController.DISPLAY)
                gui.putPerson(person);
        }
    }
}
