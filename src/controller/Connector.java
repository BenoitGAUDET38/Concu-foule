package controller;

import ihm.GUI;
import ihm.MainGUI;
import input.CSVManager;
import input.PersonGenerator;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Connector {

    public static int HEIGHT = 20;
    public static int WIDTH = 20;
    public static int NUMBER_OF_PERSON = 100;
    public static final int TIME_TO_SLEEP = 0;
    public static final boolean GENERATE_PERSON = true;
    public static  final boolean DISPLAY = true;
    public static  final boolean VERBOSE = false;
    List<Person> personList;
    List<Controller> controllers;
    MainGUI mainGUI;
    List<GUI> guis;


    public Connector(int size, int nb_persons) throws Exception {
        HEIGHT = size;
        WIDTH = size;
        NUMBER_OF_PERSON = nb_persons;
    }

    public long executeSimulation() throws Exception {
        if (GENERATE_PERSON)
            new PersonGenerator().createArrayPositionDepart();
        controllers=new ArrayList<>();
        personList = new CSVManager().getPersonList();
        createControlers();

        guis = new ArrayList<>();
        for (Controller controller : controllers) {
            guis.add(controller.gui);
        }
        if (DISPLAY)
            mainGUI = new MainGUI(guis);

        for (Person person: personList){
            addPersonInController(person);
        }

        long startTime = System.nanoTime();

        for (Controller contr: controllers){
            contr.start();
        }

        for (Controller contr: controllers) {
            contr.join();
        }

        if (VERBOSE) {
            while(!personList.isEmpty()){
                System.out.println("===========================================================");
                System.out.println("controler0:"+controllers.get(0).personInTransit.size());
                System.out.println("controler1:"+controllers.get(1).personInTransit.size());
                System.out.println("controler2:"+controllers.get(2).personInTransit.size());
                System.out.println("controler3:"+controllers.get(3).personInTransit.size());
                System.out.println("===========================================================");
                Thread.sleep(400);
            }
            System.out.println("=====================IS EMPTY==========================");
        }

        long stopTime = System.nanoTime();
        long timeInMs = (stopTime - startTime) / 1000000;

        if (DISPLAY)
            mainGUI.close();

        return timeInMs;
    }

    public void addPersonInController(Person person) throws Exception {
        //getTheConnector(person.position).addPersonInQueue(person);
        getTheConnector(person.position).addNewPerson(person);
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
        controllers.add(new Controller(midHeight,midWidth,new Position(0,0),this, Color.decode("#B397BA")));
        controllers.add(new Controller(midHeight,WIDTH-midWidth,new Position(midWidth,0),this, Color.decode("#61BCD9")));
        controllers.add(new Controller(HEIGHT-midHeight,midWidth,new Position(0,midHeight),this, Color.decode("#B1D62A")));
        controllers.add(new Controller(HEIGHT-midHeight,WIDTH-midWidth,new Position(midWidth,midHeight),this, Color.decode("#E3CB71")));
    }



    public synchronized void removePersons(ArrayList<Person> allPersonToRemove) {
        personList.removeAll(allPersonToRemove);
    }
}
