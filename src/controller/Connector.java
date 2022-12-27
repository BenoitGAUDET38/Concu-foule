package controller;

import ihm.GUI;
import input.CSVManager;
import input.PersonGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Connector {

    public static final int HEIGHT = 20;
    public static final int WIDTH = 20;
    public static final int NUMBER_OF_PERSON = 100;
    public static final int TIME_TO_SLEEP = 0;
    public static final boolean GENERATE_PERSON = true;
    public static  final boolean DISPLAY = true;
    List<Person> personList;
    List<Controller> controllers;


    public Connector() throws Exception {

        if (GENERATE_PERSON)
            new PersonGenerator().createArrayPositionDepart();
        controllers=new ArrayList<>();
        personList = new CSVManager().getPersonList();
        createControlers();

        for (Person person: personList){
            addPersonInController(person);
        }
        for (Controller contr: controllers){
            contr.start();
        }
        while(!personList.isEmpty()){
            Thread.sleep(1000);
            System.out.println(personList.size());
        }
        System.out.println("=====================IS EMPTY==========================");
    }

    public void addPersonInController(Person person) throws Exception {
        getTheConnector(person.position).addPersonInQueue(person);
    }

    public Controller getTheConnector(Position position) throws Exception {
        for (Controller controller:controllers){
            if (position.x >= controller.offSetPosition.x && position.x < controller.offSetPosition.x + controller.getWidth()
                    && position.y >= controller.offSetPosition.y && position.y < controller.offSetPosition.y + controller.getHeight()) {
                return controller;
            }
        }
        throw new Exception("Controller not found");
    }

    public void createControlers() throws IOException {
        int midHeight=HEIGHT/2;
        int midWidth=WIDTH/2;
        controllers.add(new Controller(midHeight,midWidth,new Position(0,0),this,0));
        controllers.add(new Controller(midHeight,WIDTH-midWidth,new Position(midWidth,0),this,0));
        controllers.add(new Controller(HEIGHT-midWidth,midWidth,new Position(0,midHeight),this,0));
        controllers.add(new Controller(HEIGHT-midWidth,WIDTH-midWidth,new Position(midWidth,midHeight),this,0));
    }



    public synchronized void removePersons(ArrayList<Person> allPersonToRemove) {
        personList.removeAll(allPersonToRemove);
    }
}
