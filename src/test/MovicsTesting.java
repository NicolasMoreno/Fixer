import model.Comprobante;
import model.FileLoader;
import model.MovicsFileAnalyser;

import java.io.*;


import static org.junit.Assert.assertEquals;

/**
 * Created by nicolas.moreno on 02/12/2016.
 */
public class MovicsTesting {

    private Comprobante comprobante;
    private final MovicsFileAnalyser movicsFileAnalyser = new MovicsFileAnalyser(new File("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\NUEVARGAFIP112015CRM.TXT"));

    public MovicsTesting() throws IOException {
    }

    @org.junit.Test
    public void ruleTesting(){
        comprobante = new Comprobante();

        /* 2nd rule case A test*/
        comprobante.setCabecera("20151107003N209400000000000000074417000000000000000744178000000000030682554939MILCO PUBLICIDAD SA           -000000000000011000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000PES0001000000 3502010276543620170220000000000000000000000000000000000000046914300000809458820151201");
        comprobante.addAlicuota("00320940000000000000007441700000000000000074417-000000000000012100000000000000000");
        movicsFileAnalyser.initSecondRule(comprobante);
        String correctHeading = "20151107003N209400000000000000074417000000000000000744178000000000030682554939MILCO PUBLICIDAD SA           -000000000000011-00000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000PES0001000000 3502010276543620170220000000000000000000000000000000000000046914300000809458820151201";
        String correctAlicuota = "003209400000000000000074417000000000000000744170000000000000000000000000000000000";

        assertEquals(correctAlicuota,comprobante.getAlicuota(0));
        assertEquals(correctHeading,comprobante.getCabecera());
        comprobante.getAlicuotas().clear();



        /* 2nd rule case B test*/
        comprobante.setCabecera("20151107001N209300000000000004777343000000000000047773438000000000030708028734DDN BROKERS S. A              0000000000364082000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001145000000000000275PES0001000000 6545737240241920151117000000000000000000000000000000000000046961800000819228620151201");
        comprobante.addAlicuota("001209300000000000004777343000000000000047773430000000000275492700000000000007438");
        comprobante.addAlicuota("001209300000000000004777343000000000000047773430000000000000012100000000000000000");
        movicsFileAnalyser.initSecondRule(comprobante);
        correctHeading = "20151107001N209300000000000004777343000000000000047773438000000000030708028734DDN BROKERS S. A              0000000000364081000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000001145000000000000275PES0001000000 6545737240241920151117000000000000000000000000000000000000046961800000819228620151201";
        correctAlicuota = "001209300000000000004777343000000000000047773430000000000275492700000000000007438";

        assertEquals(correctHeading,comprobante.getCabecera());
        assertEquals(1,comprobante.getAlicuotas().size());
        assertEquals(correctAlicuota,comprobante.getAlicuota(0));
        comprobante.getAlicuotas().clear();

        comprobante.setCabecera("20151107006N209300000000000003259105000000000000032591058000000000020076189618SAINI               JORGE MARI0000000000018371000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000PES0001000000 6545737302427920151117000000000000000000000000000000000004471327000000411075220151201");
        comprobante.addAlicuota("006209300000000000003259105000000000000032591050000000000015182100000000000000319");
        movicsFileAnalyser.initSecondRule(comprobante);

        /* 3rd rule test*/
        comprobante.getAlicuotas().clear();
        comprobante.setCabecera("20151107003N209400000000000000074721000000000000000747218000000000030707757112BARRANCAS DEL SUR S.A         -000000004000001-00000000400000-00000000400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000PES0001000000N3502010276543620170220000000000000000000000000000000000002629302200002607018220151201");
        comprobante.addAlicuota("003209400000000000000074721000000000000000747210000000000000000000000000000000000");
        movicsFileAnalyser.initThirdRule(comprobante);
        correctHeading = "20151107003N209400000000000000074721000000000000000747218000000000030707757112BARRANCAS DEL SUR S.A         -000000004000001000000000000000-00000000400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000PES0001000000N3502010276543620170220000000000000000000000000000000000002629302200002607018220151201";
        correctAlicuota = "003209400000000000000074721000000000000000747210000000000000000000000000000000000";

        assertEquals(correctHeading,comprobante.getCabecera());
        assertEquals(correctAlicuota,comprobante.getAlicuota(0));
    }

    @org.junit.Test
    public void fileTesting() throws IOException {
        this.comprobante = new Comprobante();
        FileLoader fl = new FileLoader("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\","NUEVARGAFIP112015CRM.TXT");
        fl.start();
        BufferedReader originFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\NUEVARGAFIP112015CRM.TXT")),"Cp1252"));
        BufferedReader fixedFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\NUEVARGAFIP112015CRM.NEW")),"Cp1252"));
        int initFileLines = countLines(originFileReader);
        int fixedFileLines = countLines(fixedFileReader);
        System.out.println("originalFileLines " + initFileLines + " fixedFileLines " + fixedFileLines );
        assertEquals(initFileLines,fixedFileLines);
        }

    private int countLines(BufferedReader reader) throws IOException {
        int counter = 0;
        String line = reader.readLine();
        while ( line != null){
            int cantAlicuotas = Integer.parseInt(line.substring(comprobante.getMovicsCBTESFieldPositions(10)-1
                    , comprobante.getMovicsCBTESFieldPositions(11)-1));
            for (int i = 0; i < cantAlicuotas; i++) {
                reader.readLine();
            }
            counter++;
            line = reader.readLine();
        }
        return counter;
}


}
