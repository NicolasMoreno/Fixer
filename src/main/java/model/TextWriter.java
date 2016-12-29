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
    private TypeFileState state;


    public TextWriter(File file) throws IOException{
        if(file.getName().toLowerCase().contains(".txt")){
            this.state = TypeFileState.MOVICSFILE;
            this.archivoFixed = new File(file.getParent()+"/"+file.getName().toLowerCase().replace(".txt","")
                    .toUpperCase()+".NEW");
            this.archivoLog = new File(file.getParent()+"/"+file.getName().toLowerCase().replace(".txt","")
                    .toUpperCase()+".LOG");
        }
        else if(file.getName().toLowerCase().contains(".csv")){
            this.state = TypeFileState.AMDOCSFILE;
            this.archivoFixed = new File(file.getParent()+"/"+file.getName().toLowerCase().replace(".csv","")
                    .toUpperCase()+".NEW");
            this.archivoLog = new File(file.getParent()+"/"+file.getName().toLowerCase().replace(".csv","")
                    .toUpperCase()+".LOG");
        }
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
        // El archivo amdocs ya tiene el \r al fin de linea (supongo). Si lo incluis genera unos caracteres raros.
        if(state == TypeFileState.AMDOCSFILE) fixedFileWriter.write(line+"\n");
        if(state == TypeFileState.MOVICSFILE) fixedFileWriter.write(line+"\r\n");
    }

    public void closeFixedFile() throws IOException {
        fixedFileWriter.flush();
        fixedFileWriter.close();
    }
}
