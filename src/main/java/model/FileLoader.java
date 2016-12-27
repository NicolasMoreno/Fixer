package model;

import java.io.File;
import java.io.IOException;

/**
 * Created by nicolas.moreno on 21/11/2016.
 */
public class FileLoader {

    private TextAnalyser textAnalyser = new TextAnalyser();


    /**
     * Metodo que Arranca la secuencia para Fixear un archivo, sea movics o amdocs
     * @param path Path de la carpeta donde se encuentra el archivo
     * @param filename Nombre del archivo a fixear
     * @throws IOException En caso de no encontrarse el archivo.
     */
    public void start(String path, String filename) throws IOException{
        if(filename.toLowerCase().contains(".csv")){
            textAnalyser.analyse(new AmdocsFileAnalyser(new File(path+filename)));
        }
        else if(filename.toLowerCase().contains(".txt")){
            textAnalyser.analyse(new MovicsFileAnalyser(new File(path+filename)));
        }
        else{
            throw new IOException();
        }
    }
}
