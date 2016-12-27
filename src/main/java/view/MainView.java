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


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(325,300);


        this.mainViewPanel = new MainViewPanel();
        this.add(mainViewPanel);
        this.startFixButton = new JButton("Start Fix");
        startFixButton.setBounds(100,70,90,25);
        this.add(startFixButton);


        this.setVisible(true);
    }

    public JButton getStartFixButton() {
        return startFixButton;
    }
    public JTextField getPathInput(){
        return mainViewPanel.getPathInput();
    }
    public JTextField getFileNameInput(){
        return mainViewPanel.getFileNameInput();
    }
}
