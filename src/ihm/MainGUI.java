package ihm;

import controller.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainGUI {
    private static final int border = 800;
    private JFrame myFrame;

    public MainGUI(List<GUI> guis) {
        myFrame = setupFrame();
        JPanel panel = setupMainPanel(guis);
        myFrame.setContentPane(panel);
        myFrame.setVisible(true);
    }

    JPanel setupMainPanel(List<GUI> guis) {
        // Setup the grid inside a gridLayout;
        // Each case of the grid is a JPanel of the matrix
        GridLayout layout = new GridLayout(guis.size()/2, guis.size() - guis.size()/2);
        JPanel panel = new JPanel(layout);

        for (int i = 0; i < guis.size(); i++) {
            panel.add(guis.get(i).getMyPanel());
        }

        return panel;
    }

    JFrame setupFrame() {
        JFrame frame = new JFrame();
        frame.setSize(border, border);
        frame.setResizable(true);
        frame.setTitle("Super Concurrence Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // try to get icon image
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("images\\icon.png")));
            frame.setIconImage(icon.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // center frame
        frame.setLocationRelativeTo(null);

        return frame;
    }

    public void close() {
        myFrame.dispose();
    }
}
