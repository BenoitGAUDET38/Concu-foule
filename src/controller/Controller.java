package controller;

import ihm.GUI;

import java.io.IOException;
import java.util.*;

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
    int index;


    public Controller(int height, int width, Position offSetPosition, Connector superController, int index) throws IOException {
        this.queue=new LinkedList<>();
        this.superController=superController;
        this.index=index;
        this.height_y = height;
        this.width_x = width;
        this.personInTransit=new ArrayList<>();
        this.offSetPosition=offSetPosition;
        grid=new Grid(height, width,offSetPosition, this);
        //this.personInTransit = new CSVManager().getPersonList(grid);

        if (DISPLAY) {
            gui=new GUI(grid);
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
            ArrayList<Person> allPersonToRemove=new ArrayList<>();
            for (Person person: personInTransit){
                if(!person.makeChoice(grid)) {
                    allPersonToRemove.add(person);
                    System.out.println("Finished:"+person);
                }
            }
            superController.removePersons(allPersonToRemove);
            personInTransit.removeAll(allPersonToRemove);
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
        if (DISPLAY)
            gui.close();
    }

    public int getHeight() {
        return height_y;
    }

    public int getWidth() {
        return width_x;
    }

}
