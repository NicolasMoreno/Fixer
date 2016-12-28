package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nicolas.moreno on 22/12/2016.
 */
public class MainView extends JFrame {

    private MainViewPanel mainViewPanel;
    private JButton startFixButton;
    private JLabel statusLabel;

    public MainView(){


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(325,300);


        this.mainViewPanel = new MainViewPanel();
        this.add(mainViewPanel);
        this.startFixButton = new JButton("Iniciar");
        startFixButton.setBounds(100,120,90,25);
        this.add(startFixButton);
        this.statusLabel = new JLabel("Esperando para iniciar");
        statusLabel.setBounds(90,170,160,25);
        this.add(statusLabel);


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

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
