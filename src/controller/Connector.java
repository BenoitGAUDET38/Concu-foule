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

    public static final int HEIGHT = 120;
    public static final int WIDTH = 100;
    public static final int NUMBER_OF_PERSON = 10000;
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
        Controller controller=new Controller(HEIGHT,WIDTH,new Position(0,0),this,0);
        controllers.add(controller);

        for (Person person: personList){
            addPersonInController(person);
        }
        for (Controller contr: controllers){
            contr.start();
        }
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



    public synchronized void removePersons(ArrayList<Person> allPersonToRemove) {
        personList.removeAll(allPersonToRemove);
    }
}
