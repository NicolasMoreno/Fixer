package model;

import java.io.File;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by nicolas.moreno on 21/11/2016.
 */
public class AmdocsFileAnalyser extends AbstractFileAnalyser {

    private StringBuilder stringBuilder;

    public AmdocsFileAnalyser(File file) throws IOException{
        super(file);
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void initFirstRule(Comprobante comprobante) {
        int cantidadAlícuotas = (comprobante.getAlicuotas().size());
        initFirstRule(comprobante,cantidadAlícuotas);

    }

    private void initFirstRule(Comprobante comprobante, int cantAlicuota) {
        String[] splittedAlicuota = comprobante.getCabecera().split(",");
        String name = splittedAlicuota[9];
        BigDecimal totalDeOperacion = new BigDecimal(Double.parseDouble((splittedAlicuota[10].replaceAll("\u0000",""))));
        BigDecimal noGravado = new BigDecimal(Double.parseDouble((splittedAlicuota[11].replaceAll("\u0000",""))));
        BigDecimal sumaDeImportes = new BigDecimal(0.00);
        for (int i = 0; i < cantAlicuota; i++) {
            splittedAlicuota = comprobante.getAlicuota(i).split(",");

            for (int j = 0; j < 10 ; j++) {
                if(j!=2){
                    sumaDeImportes = sumaDeImportes.add(new BigDecimal(Double.parseDouble((splittedAlicuota[11 + j].replaceAll("\u0000","")))));
                }
            }


        }

        if(!(totalDeOperacion.equals(sumaDeImportes))){
            super.writeOnLogFile("1er regla en "+ name.replaceAll("\u0000","") + " id comprobante = " + splittedAlicuota[5].replaceAll("\u0000","") );
            BigDecimal newValue = totalDeOperacion.subtract(sumaDeImportes).add(noGravado);
            String stringedNumber = newValue.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
            for (int i = 0; i < cantAlicuota; i++) {
                splittedAlicuota = comprobante.getAlicuota(i).split(",");
                splittedAlicuota[11] =  fillWithNullChar(stringedNumber);
                String newAlicuota = Arrays.toString(splittedAlicuota).replace("[","").replace("]","").replace(" ","")
                        .replace(name.replace(" ",""),name);
                comprobante.changeAlicuota(i,newAlicuota);
                super.writeOnLogFile("Viejo valor No gravado = " + noGravado.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                super.writeOnLogFile("Nuevo valor No gravado = " + stringedNumber);
                super.writeOnLogFile("\n");
            }

        }
    }

    private String fillWithNullChar(String stringedNumber) {
        int addedChars = 0;
        stringBuilder.delete(0,stringBuilder.length());
        stringBuilder.insert(0,stringedNumber);
        for (int i = 0; i <= stringedNumber.length(); i++) {
            stringBuilder.insert(i+addedChars,"\u0000");
            addedChars++;
        }
        return stringBuilder.toString();
    }

    @Override
    public void initSecondRule(Comprobante comprobante) {
        String[] splittedAlicuota;
        int cantidadAlícuotas = (comprobante.getAlicuotas().size());
        for (int i = 0; i < cantidadAlícuotas; i++) {
            splittedAlicuota = comprobante.getAlicuota(i).split(",");
            if(splittedAlicuota[14].replaceAll("\u0000","").equals(".00") && !(splittedAlicuota[10].replaceAll("\u0000","").equals(splittedAlicuota[11].replaceAll("\u0000","")))
                    && !(splittedAlicuota[13].replaceAll("\u0000","").equals("0"))){
                if (cantidadAlícuotas>1) {
                    toCorrectCaseB(comprobante,i);
                    super.writeOnLogFile("\n");
                    break;
                }
                else {
                    toCorrectCaseA(comprobante,0);
                    super.writeOnLogFile("\n");
                }
            }
        }
    }

    private void toCorrectCaseB(Comprobante comprobante, int index) {
        String splittedAlicuota[] = comprobante.getAlicuota(index).split(",");
        String name = splittedAlicuota[9];
        int oldAlicuotas = (comprobante.getAlicuotas().size());
        super.writeOnLogFile("2da regla caso B en "+ name + " id comprobante = " + splittedAlicuota[5] );
        BigDecimal netoGravado = new BigDecimal(Double.parseDouble(splittedAlicuota[12].replaceAll("\u0000","")));
        comprobante.getAlicuotas().remove(index);
        int cantAlicuotas = (comprobante.getAlicuotas().size());
        for (int i = 0; i < cantAlicuotas ; i++) {
            splittedAlicuota = comprobante.getAlicuota(i).split(",");
            BigDecimal noGravadoPlusNetoGravado = netoGravado
                    .add(new BigDecimal(Double.parseDouble(splittedAlicuota[11].replaceAll("\u0000","")))); //NoGravado + NetoGravado
            String newValue = noGravadoPlusNetoGravado.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
            if(newValue.charAt(1) == '0') newValue = newValue.replaceFirst("0","");
            super.writeOnLogFile("Viejo valor No Gravado = " + splittedAlicuota[11].replaceAll("\u0000",""));
            splittedAlicuota[11] = fillWithNullChar(newValue) ;
            super.writeOnLogFile("Nuevo valor No Gravado = " + newValue);
            super.writeOnLogFile("Viejo valor Cantidad Alícuotas = " + oldAlicuotas);
            splittedAlicuota[25] = fillWithNullChar(""+(cantAlicuotas));
            super.writeOnLogFile("Nuevo valor Cantidad Alícuotas = " + (cantAlicuotas));
            String newAlicuota = Arrays.toString(splittedAlicuota).replace("[","").replace("]","").replace(" ","")
                    .replace(name.replace(" ",""),name);
            comprobante.changeAlicuota(i,newAlicuota);
        }
    }

    private void toCorrectCaseA(Comprobante comprobante, int index) {
        String splittedAlicuota[] = comprobante.getAlicuota(index).split(",");
        String name = splittedAlicuota[9];
        super.writeOnLogFile("2da regla caso A en "+ name + " id comprobante = " + splittedAlicuota[5].replaceAll("\u0000","") );
        BigDecimal noGravado = new BigDecimal(Double.parseDouble(splittedAlicuota[11].replaceAll("\u0000","")))
                .add(new BigDecimal(Double.parseDouble(splittedAlicuota[12].replaceAll("\u0000","")))); //NoGravado + NetoGravado
        String newValue = noGravado.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
        if(newValue.charAt(1) == '0') newValue = newValue.replaceFirst("0","");
        super.writeOnLogFile("Viejo valor No Gravado = " + splittedAlicuota[11].replaceAll("\u0000",""));
        splittedAlicuota[11] = fillWithNullChar(newValue);
        super.writeOnLogFile("Nuevo valor No Gravado = " + newValue);
        super.writeOnLogFile("Viejo valor Neto Gravado = " + splittedAlicuota[12].replaceAll("\u0000",""));
        splittedAlicuota[12] =fillWithNullChar(".00");
        super.writeOnLogFile("Nuevo valor No Gravado = " + ".00");
        super.writeOnLogFile("Viejo valor IVA = " + splittedAlicuota[13].replaceAll("\u0000",""));
        splittedAlicuota[13] = fillWithNullChar("0");
        super.writeOnLogFile("Nuevo valor IVA = " + "0");
        String newAlicuota = Arrays.toString(splittedAlicuota).replace("[","").replace("]","").replace(" ","")
                .replace(name.replace(" ",""),name);
        comprobante.changeAlicuota(index,newAlicuota);

    }

    @Override
    public void initThirdRule(Comprobante comprobante) {
    }

    @Override
    public void writeFinalComprobante(Comprobante comprobante) {
        for (int i = 0; i < comprobante.getAlicuotas().size(); i++) {
            super.writeOnFixedFile(comprobante.getAlicuota(i));
        }
    }
}
