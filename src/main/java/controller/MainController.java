package controller;

import model.FileLoader;
import view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by nicolas.moreno on 27/12/2016.
 */
public class MainController {

    private FileLoader fileLoader;
    private MainView mainView;

    public MainController(){
        this.mainView = new MainView();
        this.fileLoader = new FileLoader();
        addActionListeners();
    }

    private void addActionListeners() {
        mainView.getStartFixButton().addActionListener(new StartFix());
    }


    private class StartFix implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String path = mainView.getPathInput().getText();
                String fileName = mainView.getFileNameInput().getText();
                fileLoader.start(path,fileName);
                mainView.getStatusLabel().setText("Archivo Fixeado...");
            }catch (IOException exception){
                exception.printStackTrace();
                mainView.getStatusLabel().setText("Error, check data input");
            }
        }
    }
}
