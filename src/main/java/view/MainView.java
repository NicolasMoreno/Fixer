package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nicolas.moreno on 22/12/2016.
 */
public class MainView extends JFrame {

    private MainViewPanel mainViewPanel;
    private JButton startFixButton;

    public MainView(){


        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(270,300);
        this.setLayout(null);

        this.mainViewPanel = new MainViewPanel();
        this.add(mainViewPanel);




    }
}
