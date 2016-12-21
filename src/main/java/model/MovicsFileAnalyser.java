package model;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


/**
 * Created by nicolas.moreno on 21/11/2016.
 */
public class MovicsFileAnalyser extends AbstractFileAnalyser {


    private final String FIFTEENZERO = "000000000000000";
    private final String FOURZERO = "0000";
    private final String FREESPACE = "                                                                                                                                                                                                                                                                      ";
    private StringBuilder cabeceraBuilder = new StringBuilder();
    private StringBuilder stringNumberParser = new StringBuilder();


    public MovicsFileAnalyser(File file) throws IOException{
        super(file);
    }

    @Override
    public void initFirstRule(Comprobante comprobante) {
        stringNumberParser.delete(0,stringNumberParser.length());
        cabeceraBuilder.delete(0,cabeceraBuilder.length());
        int cantAlicuotas = comprobante.getAlicuotas().size();
            for (int i = 0; i < cantAlicuotas; i++) {
                String alicuota = comprobante.getAlicuota(i);
                if(!(alicuota.substring(comprobante.getMovicsALICUOTASFieldPositions(5)-1,comprobante.getMovicsALICUOTASFieldPositions(6)-1).equals(FOURZERO)) && alicuota.endsWith(FIFTEENZERO)){
                    if(cantAlicuotas > 1){

                        toCorrectCaseB(comprobante,i);
                        super.writeOnLogFile("\n");
                        initFirstRule(comprobante);
                        break;

                    }else{
                        toCorrectCaseA(comprobante,i);
                        super.writeOnLogFile("\n");
                    }
                }
            }
    }

    private void toCorrectCaseA(Comprobante comprobante, int posicionAlicuota) {
        String cabecera = comprobante.getCabecera();//obtenemos la cabecera
        super.writeOnLogFile("Regla 1 caso A en " + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(8)-1
                ,comprobante.getMovicsCBTESFieldPositions(9)-1) + " con id = "
                + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(7)-1
                ,comprobante.getMovicsCBTESFieldPositions(8)-1) );
        String alicuota = comprobante.getAlicuota(posicionAlicuota); //obtenemos la alicuota que esta erronea
        Double importeNetoGravado = Double.parseDouble(alicuota // parseamos el field importeNetoGravado
                .substring(comprobante.getMovicsALICUOTASFieldPositions(4)-1
                        ,comprobante.getMovicsALICUOTASFieldPositions(5)-1));
        Double conceptosOperaciones = Double.parseDouble(cabecera   // parseamos el field conceptosOperaciones
                .substring(comprobante.getMovicsCBTESFieldPositions(11)-1
                        ,comprobante.getMovicsCBTESFieldPositions(12)-1));
        super.writeOnLogFile("Viejo valor Conceptos/Operaciones = " + (conceptosOperaciones/100));
        //System.out.println(conceptosOperaciones.toString());
        String newData = String.valueOf((importeNetoGravado+conceptosOperaciones)).replace(".0","");

        stringNumberParser.insert(0,FIFTEENZERO);
                if(newData.charAt(0) != '-') stringNumberParser.replace(15-newData.length(),15,newData );
                else stringNumberParser.replace(0,1,""+newData.charAt(0)).replace(15-newData.length(),15,newData.replace("-","0" ));
        super.writeOnLogFile("Nuevo valor Conceptos/Operaciones = " + String.valueOf((importeNetoGravado+conceptosOperaciones)/100));
        //System.out.println(stringNumberParser.toString());
        cabeceraBuilder.insert(0,cabecera);
        cabeceraBuilder.replace(comprobante.getMovicsCBTESFieldPositions(11)-1
                ,comprobante.getMovicsCBTESFieldPositions(12)-1
                , stringNumberParser.toString());

        stringNumberParser.delete(0,stringNumberParser.length()).insert(0,alicuota).
                replace(comprobante.getMovicsALICUOTASFieldPositions(4)-1
                        ,comprobante.getMovicsALICUOTASFieldPositions(7)-1
                        ,FIFTEENZERO + FOURZERO + FIFTEENZERO );
        comprobante.setCabecera(cabeceraBuilder.toString());
        comprobante.changeAlicuota(0,stringNumberParser.toString());
        super.writeOnLogFile("Cambiado todos los field de la alicuota erronea en ceros");
    }

    private void toCorrectCaseB(Comprobante comprobante, int posicionAlicuota) {
        String cabecera = comprobante.getCabecera(); //obtenemos la cabecera
        super.writeOnLogFile("Regla 1 caso B en " + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(8)-1
                ,comprobante.getMovicsCBTESFieldPositions(9)-1) + " con id = "
                + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(7)-1
                ,comprobante.getMovicsCBTESFieldPositions(8)-1) );
        String alicuota = comprobante.getAlicuota(posicionAlicuota); //obtenemos la alicuota que esta erronea
        Double importeNetoGravado = Double.parseDouble(alicuota // parseamos el
                .substring(comprobante.getMovicsALICUOTASFieldPositions(4)-1
                        ,comprobante.getMovicsALICUOTASFieldPositions(5)-1));
        Double conceptosOperaciones = Double.parseDouble(cabecera
                .substring(comprobante.getMovicsCBTESFieldPositions(11)-1
                        ,comprobante.getMovicsCBTESFieldPositions(12)-1));
        super.writeOnLogFile("Viejo valor Conceptos/Operaciones = " + (conceptosOperaciones/100));
        Integer cantidadAlicuotas = Integer.parseInt(cabecera
                .substring(comprobante.getMovicsCBTESFieldPositions(10)-1
                        ,comprobante.getMovicsCBTESFieldPositions(11)-1));
        super.writeOnLogFile("Viejo valor Cantidad de alicuotas de IVA = " + cantidadAlicuotas.toString());
        double sum = (importeNetoGravado+conceptosOperaciones);
        String newData = String.valueOf(sum).replace(".0","");
        stringNumberParser.insert(0,FIFTEENZERO);
            if(newData.charAt(0) != '-') stringNumberParser.replace(15-newData.length(),15,newData );
            else stringNumberParser.replace(0,1,""+newData.charAt(0)).replace(15-newData.length(),15,newData.replace("-","0" ));
        if(cantidadAlicuotas > 1) {
            comprobante.getAlicuotas().remove(posicionAlicuota);
            super.writeOnLogFile("Eliminada Alicuota Erronea");
        }
        cabeceraBuilder.insert(0,cabecera);
        cabeceraBuilder.replace(comprobante.getMovicsCBTESFieldPositions(10)-1
                ,comprobante.getMovicsCBTESFieldPositions(11)-1, String.valueOf(comprobante.getAlicuotas().size()));
        super.writeOnLogFile("Nuevo valor Cantidad de alicuotas de IVA = " + comprobante.getAlicuotas().size());
        cabeceraBuilder.replace(comprobante.getMovicsCBTESFieldPositions(11)-1
                ,comprobante.getMovicsCBTESFieldPositions(12)-1
                , stringNumberParser.toString());
        comprobante.setCabecera(cabeceraBuilder.toString());
        super.writeOnLogFile("Nuevo valor Conceptos/Operaciones = " + (sum)/100);
    }

    @Override
    public void initSecondRule(Comprobante comprobante) {
        stringNumberParser.delete(0,stringNumberParser.length());
        cabeceraBuilder.delete(0,cabeceraBuilder.length());
        String cabecera = comprobante.getCabecera();
        int posiciónInicial = comprobante.getMovicsCBTESFieldPositions(9)-1;
        Double importeTotal =  Double.parseDouble((cabecera
                .substring(posiciónInicial,posiciónInicial+15)));
        Double importeTotalReal = 0.00;

        for (int i = 0; i < 8; i++) {
                importeTotalReal += Double.parseDouble(cabecera
                        .substring(posiciónInicial+1+(15*(i+1)),posiciónInicial+1+(15*(i+2))));
        }
        for (int i = 0; i < comprobante.getAlicuotas().size(); i++) {
            String alicuota = comprobante.getAlicuota(i);
            for (int j = 0; j < 3; j++) {
                if(j!=1) {
                    Double totalGravado = Double.parseDouble(alicuota.substring(comprobante.getMovicsALICUOTASFieldPositions(4+j)-1
                            ,comprobante.getMovicsALICUOTASFieldPositions(5+j)-1));
                    importeTotalReal += totalGravado;
                }
            }
        }

        if(!(Objects.equals(importeTotal, importeTotalReal))){
            super.writeOnLogFile("Regla 2 en " + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(8)-1
                    ,comprobante.getMovicsCBTESFieldPositions(9)-1) + " con id = "
                    + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(7)-1
                    ,comprobante.getMovicsCBTESFieldPositions(8)-1) );
            Double conceptoOperaciones = Double.parseDouble(cabecera
                    .substring(posiciónInicial+15+1,posiciónInicial+(15*2)+1));


            super.writeOnLogFile("Viejo valor Conceptos/Operaciones = " + conceptoOperaciones/100);
            conceptoOperaciones += (importeTotal-importeTotalReal);
            super.writeOnLogFile("Nuevo valor Conceptos/Operaciones = " + conceptoOperaciones/100);
            String newData = conceptoOperaciones.toString().replace(".0","");
            stringNumberParser.insert(0,FIFTEENZERO);
            if(newData.charAt(0) != '-') stringNumberParser.replace(15-newData.length(),15,newData );
            else stringNumberParser.replace(0,1,""+newData.charAt(0)).replace(15-newData.length(),15,newData.replace("-","0" ));
            cabeceraBuilder.insert(0,cabecera).replace(comprobante.getMovicsCBTESFieldPositions(11)-1
                    ,comprobante.getMovicsCBTESFieldPositions(12)-1,stringNumberParser.toString());
            comprobante.setCabecera(cabeceraBuilder.toString());
            super.writeOnLogFile("\n");
        }
    }

    /*NOTA: No se si esta regla es real, me la dijo Juan, pero NO la especificaron desde Telefonica.*/
    @Override
    public void initThirdRule(Comprobante comprobante) {
        stringNumberParser.delete(0,stringNumberParser.length());
        cabeceraBuilder.delete(0,cabeceraBuilder.length());
        String cabecera = comprobante.getCabecera();
        int cantAlicuotas = Integer.parseInt(cabecera.substring(comprobante.getMovicsCBTESFieldPositions(10)-1
                ,comprobante.getMovicsCBTESFieldPositions(11)-1));
        int cantidadRealDeAlicuotas = comprobante.getAlicuotas().size();
        if(cantAlicuotas != cantidadRealDeAlicuotas){
            super.writeOnLogFile("Regla ? en " + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(8)-1
                    ,comprobante.getMovicsCBTESFieldPositions(9)-1) + " con id = "
                    + cabecera.substring(comprobante.getMovicsCBTESFieldPositions(7)-1
                    ,comprobante.getMovicsCBTESFieldPositions(8)-1) );
            super.writeOnLogFile("Valor viejo = " + cantAlicuotas + " Valor nuevo = " +cantidadRealDeAlicuotas);
            cabeceraBuilder.insert(0,cabecera)
                    .replace(comprobante.getMovicsCBTESFieldPositions(10)-1,comprobante.getMovicsCBTESFieldPositions(11)-1
                            ,""+cantidadRealDeAlicuotas);
            comprobante.setCabecera(cabeceraBuilder.toString());
            super.writeOnLogFile("\n");
        }
    }

    @Override
    public void writeCorrectedComprobante(Comprobante comprobante){
        super.writeOnFixedFile(comprobante.getCabecera());
        for (int i = 0; i < comprobante.getAlicuotas().size(); i++) {
            super.writeOnFixedFile(comprobante.getAlicuota(i)
                    +FREESPACE);
        }
    }
}
