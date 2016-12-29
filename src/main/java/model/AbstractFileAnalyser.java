package model;

import java.io.*;

/**
 * Created by nicolas.moreno on 21/11/2016.
 *
 *
 *
 */
abstract public class AbstractFileAnalyser {


    private BufferedReader reader;
    private TextWriter textWriter;

    AbstractFileAnalyser(File file) throws IOException{
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Cp1252"));
        this.textWriter = new TextWriter(file);
    }
    /**
     * Metodo para ejecutar la primera regla
     * @param comprobante Aca se encuentran las lineas del file, en un String como la cabecera y
     *                    en un ArrayList de String como los detalles
     */
    abstract public void initFirstRule(Comprobante comprobante);

    /**
     * Método para ejecutar la segunda regla
     * @param comprobante Aca se encuentran las lineas del file, en un String como la cabecera y
     *                    en un ArrayList de String como los detalles
     */
    abstract public void initSecondRule(Comprobante comprobante);
    
    /**
     * Metodo para escribir al archivo .NEW, con el comprobante corregido
     * @param comprobante Comprobante corregido luego de pasar por las reglas.
     */
    abstract public void writeCorrectedComprobante(Comprobante comprobante);

    public String readLine(){
        try{
            return reader.readLine();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void writeOnLogFile(String line){
        textWriter.writeOnLogFile(line);
    }

    public void closeLogFile(){
        textWriter.closeLogFile();
    }

    public void writeOnFixedFile(String line){
        try{
            textWriter.writeOnFixedFile(line);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeFixedFile(){
        try{
            textWriter.closeFixedFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
