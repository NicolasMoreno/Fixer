package model;

import java.io.File;
import java.io.IOException;

/**
 * Created by nicolas.moreno on 21/11/2016.
 */
public class FileLoader {

    private File file;
    private TextAnalyser textAnalyser;

    public FileLoader(String path, String filename){ //path and filename?
            this.file = new File(path+filename);
            this.textAnalyser = new TextAnalyser();
    }

    public void start() throws IOException{
        if(file.getName().toLowerCase().contains(".csv")){
            textAnalyser.analyse(new AmdocsFileAnalyser(file));
        }
        else if(file.getName().toLowerCase().contains(".txt")){
            textAnalyser.analyse(new MovicsFileAnalyser(file));
        }
    }
}
