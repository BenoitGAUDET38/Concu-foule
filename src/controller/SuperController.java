package controller;

import input.CSVManager;
import input.PersonGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuperController {

    public static final int HEIGHT = 120;
    public static final int WIDTH = 100;
    public static final int NUMBER_OF_PERSON = 1000;
    public static final int TIME_TO_SLEEP = 0;
    public static final boolean GENERATE_PERSON = true;
    public static  final boolean DISPLAY = false;

    List<Person> personList;


    public SuperController() throws IOException {
        if (GENERATE_PERSON)
            new PersonGenerator().createArrayPositionDepart();

        personList = new CSVManager().getPersonList();
    }
}
