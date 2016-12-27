import model.FileLoader;
import view.MainView;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by nicolas.moreno on 22/11/2016.
 */
public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Por favor ingrese el path de la carpeta del archivo a fixear");
        String path = scan.nextLine();
        System.out.println("Por favor ingrese el nombre del archivo a fixear (Ingresando la extensión .txt por ejemplo)");
        String fileName = scan.nextLine();
        FileLoader fl = new FileLoader();
        try {
            fl.start(path,fileName);
            System.out.println("Archivo "+fileName+" fixeado correctamente");
        } catch (IOException e) {
            System.out.println("Error al cargar archivo, checkee los valores ingresados");
            e.printStackTrace();
        }
//        new MainView();
    }
}
