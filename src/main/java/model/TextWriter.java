package model;

import java.io.*;


/**
 * Created by nicolas.moreno on 25/11/2016.
 */
public class TextWriter {

    private File archivoFixed;
    private File archivoLog;
    private BufferedWriter fixedFileWriter ;
    private PrintWriter logWriter;


    public TextWriter(File file) throws IOException{

        this.archivoFixed = new File(file.getParent()+"/"+file.getName().toLowerCase().replace(".txt","").replace(".csv","")
                .toUpperCase()+".NEW");
        this.archivoLog = new File(file.getParent()+"/"+file.getName().toLowerCase().replace(".txt","").replace(".csv","")
                .toUpperCase()+".LOG");
        this.fixedFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoFixed),"Cp1252"));
        this.logWriter = new PrintWriter(new FileWriter(archivoLog),true);

    }

    public void writeOnLogFile (String text){
        if(text.equals("\n")) logWriter.write(text);
        else logWriter.write(text+"; ");
    }
    public void closeLogFile(){
        logWriter.flush();
        logWriter.close();
    }

    public void writeOnFixedFile(String line) throws IOException {
        if(line.equals("\u0000")) return;
        fixedFileWriter.write(line+"\n");
    }

    public void closeFixedFile() throws IOException {
        fixedFileWriter.flush();
        fixedFileWriter.close();
    }
}
