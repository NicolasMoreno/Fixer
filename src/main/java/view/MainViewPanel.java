package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nicolas.moreno on 22/12/2016.
 */
public class MainViewPanel extends JPanel {

    private JLabel pathLabel;
    private JTextField pathInput;
    private JLabel fileNameLabel;
    private JTextField fileNameInput;

    public MainViewPanel(){

        this.setLayout(new GridLayout(2,2));
        this.setSize(300,50);
//        this.setBackground(Color.RED);
        this.pathLabel = new JLabel("label path");
        pathLabel.setSize(100,50);
        this.pathInput = new JTextField("input path");
        pathInput.setSize(90,50);
        this.fileNameLabel = new JLabel("label fileName");
        fileNameLabel.setSize(100,50);
        this.fileNameInput = new JTextField("label path");
        fileNameInput.setSize(90,50);

        this.add(pathLabel); this.add(fileNameLabel);
        this.add(pathInput); this.add(fileNameInput);

        this.setOpaque(true);


    }

    public JTextField getPathInput() {
        return pathInput;
    }

    public JTextField getFileNameInput() {
        return fileNameInput;
    }
}
