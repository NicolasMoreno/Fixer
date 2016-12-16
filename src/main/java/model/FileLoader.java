package model;

import java.io.File;
import java.io.IOException;

/**
 * Created by nicolas.moreno on 21/11/2016.
 */
public class FileLoader {

    private TextAnalyser textAnalyser = new TextAnalyser();

    public void start(String path, String filename) throws IOException{
        if(filename.toLowerCase().contains(".csv")){
            textAnalyser.analyse(new AmdocsFileAnalyser(new File(path+filename)));
        }
        else if(filename.toLowerCase().contains(".txt")){
            textAnalyser.analyse(new MovicsFileAnalyser(new File(path+filename)));
        }
    }
}
