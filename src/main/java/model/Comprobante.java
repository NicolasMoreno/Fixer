package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas.moreno on 22/11/2016.
 */
public class Comprobante {

    private final int[] movicsCBTESFieldPositions = {1,9,12,13,17,37,57,59,79,109,124,125,140,155,170,185,200,
            215,230,245,248,258,259,273,281,289,297,312,324,336};
    private final int[] movicsALICUOTASFieldPositions = {1,4,8,28,48,63,67,82};


    private String cabecera;
    private ArrayList<String> alicuotas;

    public Comprobante(){
        this.cabecera = "";
        this.alicuotas = new ArrayList<>();
    }

    public int getMovicsCBTESFieldPositions(int i) {
        return movicsCBTESFieldPositions[i];
    }

    public int getMovicsALICUOTASFieldPositions(int i) {
        return movicsALICUOTASFieldPositions[i];
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public List<String> getAlicuotas() {
        return alicuotas;
    }
    public void addAlicuota(String alicuota){
        this.alicuotas.add(alicuota);
    }
    public String getAlicuota(int index){
        return alicuotas.get(index);
    }
    public void changeAlicuota(int index,String alicuota){
        this.alicuotas.set(index,alicuota);
    }

}
