package ihm;

import controller.Grid;
import controller.Person;
import controller.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Objects;

public class GUI {
    private static final int border = 400;
    private final JPanel[][] myGridPanel;
    JPanel myPanel;
    Color myColor;

    public GUI(Grid grid, Color color) {
        myGridPanel = setupGridPanel(grid);
        myColor = color;

        myPanel = setupMainPanel(grid);
        for (JPanel[] jpanels:myGridPanel){
            for (JPanel jpanel:jpanels)
                jpanel.setBackground(myColor);
        }
    }

    public JPanel getMyPanel() {
        return myPanel;
    }

    public void updatePersonPosition(Person person, Position previousPosition, Position nextPosition) {
        myGridPanel[previousPosition.getY()][previousPosition.getX()].setBackground(myColor);
        myGridPanel[nextPosition.getY()][nextPosition.getX()].setBackground(person.getColor());
    }
    public void putPerson(Position position,Person person) {
        myGridPanel[position.getY()][position.getX()].setBackground(person.getColor());
    }

    public void deletePersonByPosition(Position position) {
        myGridPanel[position.getY()][position.getX()].setBackground(myColor);
    }
    public void finishGame(Position position) {
        myGridPanel[position.getY()][position.getX()].setBackground(myColor);
        myGridPanel[position.getY()][position.getX()].setBorder(BorderFactory.createLineBorder(Color.red));
    }

    JPanel setupMainPanel(Grid grid) {
        // Setup the grid inside a gridLayout;
        // Each case of the grid is a JPanel of the matrix
        GridLayout layout = new GridLayout(grid.getHeight(), grid.getWidth());
        JPanel panel = new JPanel(layout);

        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                panel.add(myGridPanel[i][j]);
            }
        }

        return panel;
    }

    JPanel[][] setupGridPanel(Grid grid) {
        JPanel[][] gridPanel = new JPanel[grid.getHeight()][grid.getWidth()];

        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                Person currentPerson = grid.getTab()[i][j];
                if (currentPerson != null) {
                    panel.setBackground(currentPerson.getColor());
                }
                gridPanel[i][j] = panel;
            }
        }

        return gridPanel;
    }

}
