package controller;

import ihm.GUI;
import input.CSVManager;
import input.PersonGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controller.SuperController.DISPLAY;

public class Controller {
    Grid grid;
    // persons that didn't finish yet
    List<Person> personInTransit;
    GUI gui;
    int height, width;
    //position of the top left corner
    Position position;


    public Controller(int height, int width,Position position) throws IOException {
        this.height = height;
        this.width = width;
        grid=new Grid(height, width);
        //this.personInTransit = new CSVManager().getPersonList(grid);

        if (DISPLAY) {
            gui=new GUI(grid);
            grid.setGui(gui);
        }
        for (Person person: personInTransit){
            grid.putPerson(person);
        }
    }

    /**
     * start the simulation and finish when evrybody is out of the grid
     * @throws InterruptedException
     */
    public void execute() throws InterruptedException {
        while (personInTransit.size()>0) {
            ArrayList<Person> allPersonToRemove=new ArrayList<>();
            for (Person person: personInTransit){
                if(!person.makeChoice()) {
                    allPersonToRemove.add(person);
                    System.out.println("Finished:"+person);
                }
            }
            personInTransit.removeAll(allPersonToRemove);
        }
    }

    public void close() {
        if (DISPLAY)
            gui.close();
    }
}
