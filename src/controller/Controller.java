package controller;

import ihm.GUI;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static controller.Connector.DISPLAY;

public class Controller extends Thread{
    Grid grid;
    // persons that didn't finish yet
    List<Person> personInTransit;
    GUI gui;
    int height_y, width_x;
    //position of the top left corner
    Position offSetPosition;
    Connector superController;
    Queue<Person> queue;


    public Controller(int height, int width, Position offSetPosition, Connector superController, Color color) throws IOException {
        this.queue=new LinkedList<>();
        this.superController=superController;
        this.height_y = height;
        this.width_x = width;
        this.personInTransit=new ArrayList<>();
        this.offSetPosition=offSetPosition;
        grid=new Grid(height, width,offSetPosition, this);
        //this.personInTransit = new CSVManager().getPersonList(grid);

        if (DISPLAY) {
            gui=new GUI(grid, color);
            grid.setGui(gui);
        }
    }
    public synchronized void addPersonInQueue(Person person) {
        queue.add(person);
    }
    public synchronized Person getNextPersonInQueue(){
        return queue.poll();
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * start the simulation and finish when evrybody is out of the grid
     * @throws InterruptedException
     */
    public void execute() throws Exception {
        while (superController.personList.size()>0) {
            addNewPersons();
            ArrayList<Person> allPersonToRemoveFromControler=new ArrayList<>();
            ArrayList<Person> allPersonToRemoveFromGame=new ArrayList<>();

            for (Person person: personInTransit){
                int personDecision=person.makeChoice(grid);
                if(personDecision==Person.FINISH) {
                    allPersonToRemoveFromGame.add(person);
                }
                else if(personDecision==Person.MOVETOACONTROLER){
                    allPersonToRemoveFromControler.add(person);
                }
            }
            superController.removePersons(allPersonToRemoveFromGame);
            personInTransit.removeAll(allPersonToRemoveFromGame);
            personInTransit.removeAll(allPersonToRemoveFromControler);
        }
    }

    private void addNewPersons() {
        Person person=getNextPersonInQueue();
        while(person!=null){
            addNewPerson(person);
            person=getNextPersonInQueue();
        }
    }

    public void addNewPerson(Person person){
        this.personInTransit.add(person);
        grid.putPerson(person);
    }

    public void goToOtherController(Person person, Position position) throws Exception {
        Controller otherController = superController.getTheConnector(position);
        person.startPosition = position;
        person.destroy(grid);
        person.comptReset = 3;
        otherController.addPersonInQueue(person);
    }

    public void close() {
//        if (DISPLAY)
//            gui.close();
    }

    public int getHeight() {
        return height_y;
    }

    public int getWidth() {
        return width_x;
    }

}
