package model;

/**
 * Created by nicolas.moreno on 21/11/2016.
 */
public class TextAnalyser {

    private Comprobante comprobante;

    public TextAnalyser(){
        this.comprobante =  new Comprobante();
    }

    /**
     * Metodo que empieza a analizar un file y dependiendo del tipo del file ejecuta un determinado metodo
     * @param file archivo a ser analizado
     */
    public void analyse(AmdocsFileAnalyser file){
        String header = file.readLine();
        while (header != null && !(header.equals("\u0000"))){
            String replaceAllHeader = header.replaceAll("\u0000","");
            comprobante.setCabecera(replaceAllHeader);
            comprobante.getAlicuotas().clear();
            comprobante.addAlicuota(replaceAllHeader);
            int cantAlicuotas = Integer.parseInt((replaceAllHeader.split(",")[25]));
            while (cantAlicuotas > 1){
                header = file.readLine();
                    if(header.equals("\u0000")) comprobante.addAlicuota((file.readLine().replaceAll("\u0000","")));
                cantAlicuotas--;
            }
            file.initFirstRule(comprobante);
            file.initSecondRule(comprobante);
            file.writeFinalComprobante(comprobante);
            header = file.readLine();
            if(header == null) break;
            if(header.equals("\u0000")) header = file.readLine();
        }
        file.closeFixedFile();
    }

    public void analyse(MovicsFileAnalyser file){
        String header = file.readLine();
        //cantidad = 0;
        while (header != null){
            comprobante.getAlicuotas().clear();
            comprobante.setCabecera(header);
            int cantAlicuotas = Integer.parseInt(header.substring(comprobante.getMovicsCBTESFieldPositions(10)-1
                    , comprobante.getMovicsCBTESFieldPositions(11)-1));
            for (int i = 0; i < cantAlicuotas; i++) {
                comprobante.addAlicuota(file.readLine());
            }
            //file.initFirstRule(comprobante);
            file.initSecondRule(comprobante);
            file.initThirdRule(comprobante);
            file.writeFinalComprobante(comprobante);
            header = file.readLine();
        }
        file.closeFixedFile();
        file.closeLogFile();
    }


}
