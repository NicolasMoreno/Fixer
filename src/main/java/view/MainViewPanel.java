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

        this.setLayout(new GridLayout(4,0));
        this.setSize(300,100);
//        this.setBackground(Color.RED);
        this.pathLabel = new JLabel(" Path de la carpeta");
        pathLabel.setSize(100,50);
        this.pathInput = new JTextField("");
        pathInput.setSize(90,50);
        this.fileNameLabel = new JLabel(" Nombre del archivo junto con su extension (.TXT)");
        fileNameLabel.setSize(100,50);
        this.fileNameInput = new JTextField("");
        fileNameInput.setSize(90,50);

        this.add(pathLabel);
        this.add(pathInput);
        this.add(fileNameLabel);
        this.add(fileNameInput);

        this.setOpaque(true);


    }

    public JTextField getPathInput() {
        return pathInput;
    }

    public JTextField getFileNameInput() {
        return fileNameInput;
    }
}
