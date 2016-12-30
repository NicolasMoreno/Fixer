package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nicolas.moreno on 21/11/2016.
 */
public class TextAnalyser {

    private Comprobante comprobante;
    private HashMap<String,String> keys;
    private ArrayList<String> toEliminateList;

    public TextAnalyser(){
        this.comprobante =  new Comprobante();
        this.keys = new HashMap<>();
        this.toEliminateList = new ArrayList<>();
    }

    /**
     * Metodo que empieza a analizar un file de AMDOCS
     * @param file Archivo amdocs a ser analizado
     */
    public void analyse(AmdocsFileAnalyser file){
        String header = file.readLine();
        while (header != null && !(header.equals("\u0000"))){
            comprobante.setCabecera(header);
            comprobante.getAlicuotas().clear();
            comprobante.addAlicuota(header);
            int cantAlicuotas = Integer.parseInt((header.split(",")[25]).replaceAll("\u0000",""));
            while (cantAlicuotas > 1){
                header = file.readLine();
                    if(header.equals("\u0000")) {
                        header = file.readLine();
                        comprobante.addAlicuota((header));
                    }
                cantAlicuotas--;
            }
            header = file.readLine();
            file.initFirstRule(comprobante);
            file.initSecondRule(comprobante);
            file.writeCorrectedComprobante(comprobante);
            if(header == null) break;
            if(header.equals("\u0000")) header = file.readLine();
        }
        file.closeFixedFile();
        file.closeLogFile();
    }

    /**
     * Metodo que empieza a analizar un file de MOVICS
     * @param file Archivo amdocs a ser analizado
     */
    public void analyse(MovicsFileAnalyser file){
        keys.clear();
        String header = file.readLine();
        while (header != null){
            comprobante.getAlicuotas().clear();
            comprobante.setCabecera(header.trim());
            String fechaComprobante = header.substring(comprobante.getMovicsCBTESFieldPositions(0)-1
                    ,comprobante.getMovicsCBTESFieldPositions(1)-1);
            String tipoComprobante = header.substring(comprobante.getMovicsCBTESFieldPositions(1)-1
                    ,comprobante.getMovicsCBTESFieldPositions(2)-1);
            String nroComprobante = tipoComprobante + header.substring(comprobante.getMovicsCBTESFieldPositions(3)-1
                    ,comprobante.getMovicsCBTESFieldPositions(5)-1); //Obtenemos el ID del comprobante
            int cantAlicuotas = Integer.parseInt(header.substring(comprobante.getMovicsCBTESFieldPositions(10)-1
                    , comprobante.getMovicsCBTESFieldPositions(11)-1));
            if(keys.containsKey(nroComprobante)){
                // TODO HAY QUE COMPRARAR FECHAS, VER  JAVA.DATE O JAVA.CALENDAR

            }else keys.put(nroComprobante,fechaComprobante);
            for (int i = 0; i < cantAlicuotas; i++) {
                comprobante.addAlicuota(file.readLine().trim());
            }
            file.initFirstRule(comprobante);
            file.initSecondRule(comprobante);
            file.writeCorrectedComprobante(comprobante);
            header = file.readLine();
        }
        file.closeFixedFile();
        file.closeLogFile();
    }


}
