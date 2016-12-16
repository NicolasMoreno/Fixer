import model.AmdocsFileAnalyser;
import model.Comprobante;
import model.FileLoader;

import java.io.*;

import static org.junit.Assert.assertEquals;
/**
 * Created by nicolas.moreno on 05/12/2016.
 */
public class AmdocsTesting {

    private Comprobante comprobante;
    private final AmdocsFileAnalyser amdocsFileAnalyser = new AmdocsFileAnalyser(new File("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\main\\resources\\files\\R37_tax_afip_sales_Ago16_01.csv"));

    public AmdocsTesting() throws IOException {
    }

    @org.junit.Test
    public void testFirstRule() throws IOException {
        comprobante = new Comprobante();

        /* 1st rule test*/
        comprobante.setCabecera("1,20160801,08,N,2117,00002959,CB211700002959,DNI,12616431,FAVRE ELBA BEATRIZ,-49.01,.00,-40.59,21,-8.32,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66317368927191,20160811,-,1560972|1560972");
        comprobante.addAlicuota("1,20160801,08,N,2117,00002959,CB211700002959,DNI,12616431,FAVRE ELBA BEATRIZ,-49.01,.00,-40.59,21,-8.32,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66317368927191,20160811,-,1560972|1560972");
        comprobante.addAlicuota("1,20160801,08,N,2117,00002959,CB211700002959,DNI,12616431,FAVRE ELBA BEATRIZ,-49.01,.00,-40.59,10.5,-.10,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66317368927191,20160811,-,1560972|1560972");
        amdocsFileAnalyser.initFirstRule(comprobante);
        String correctAlicuota1 = "1,20160801,08,N,2117,00002959,CB211700002959,DNI,12616431,FAVRE ELBA BEATRIZ,-49.01,\u00004\u00000\u0000.\u00005\u00009\u0000,-40.59,21,-8.32,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66317368927191,20160811,-,1560972|1560972";
        String correctAlicuota2 = "1,20160801,08,N,2117,00002959,CB211700002959,DNI,12616431,FAVRE ELBA BEATRIZ,-49.01,\u00004\u00000\u0000.\u00005\u00009\u0000,-40.59,10.5,-.10,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66317368927191,20160811,-,1560972|1560972";

        assertEquals(correctAlicuota1,comprobante.getAlicuotas().get(0));
        assertEquals(correctAlicuota2,comprobante.getAlicuotas().get(1));
        comprobante.getAlicuotas().clear();


        /* 2nd case A rule test */

        comprobante.setCabecera("1,20160801,08,N,2229,02386471,CB222902386471,DNI,18831752,CAICHEO NANCY SOLANGE,-.01,.00,-.01,21,.00,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,1,,66317370727505,20160811,-,47366138|47366138");
        comprobante.addAlicuota("1,20160801,08,N,2229,02386471,CB222902386471,DNI,18831752,CAICHEO NANCY SOLANGE,-.01,.00,-.01,21,.00,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,1,,66317370727505,20160811,-,47366138|47366138");
        amdocsFileAnalyser.initSecondRule(comprobante);
        correctAlicuota1 = "1,20160801,08,N,2229,02386471,CB222902386471,DNI,18831752,CAICHEO NANCY SOLANGE,-.01,\u0000-\u0000.\u00000\u00001\u0000,\u0000.\u00000\u00000\u0000,\u00000\u0000,.00,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,1,,66317370727505,20160811,-,47366138|47366138";
        assertEquals(correctAlicuota1,comprobante.getAlicuota(0));
        comprobante.getAlicuotas().clear();

        /* 2nd case B rule test*/

        comprobante.setCabecera("1,20160801,08,N,2195,00001881,CB219500001881,DNI,12367679,IFRAN ROBERTO,-44.21,.00,-0.01,21,.00,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66337578406012,20160822,-,68178525|501679215");
        comprobante.addAlicuota("1,20160801,08,N,2195,00001881,CB219500001881,DNI,12367679,IFRAN ROBERTO,-44.21,.00,-0.01,21,.00,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66337578406012,20160822,-,68178525|501679215");
        comprobante.addAlicuota("1,20160801,08,N,2195,00001881,CB219500001881,DNI,12367679,IFRAN ROBERTO,-44.21,.00,-40.00,10.5,-4.20,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,2,,66337578406012,20160822,-,68178525|501679215");
        amdocsFileAnalyser.initSecondRule(comprobante);
        correctAlicuota1 = "1,20160801,08,N,2195,00001881,CB219500001881,DNI,12367679,IFRAN ROBERTO,-44.21,\u0000-\u0000.\u00000\u00001\u0000,-40.00,10.5,-4.20,.00,.00,.00,.00,.00,.00,.00,F,PES,0001000000,\u00001\u0000,,66337578406012,20160822,-,68178525|501679215";
        assertEquals(correctAlicuota1,comprobante.getAlicuota(0));
        assertEquals(1,(comprobante.getAlicuotas().size()));
        comprobante.getAlicuotas().clear();
    }

    @org.junit.Test
    public void testFile() throws IOException {
        this.comprobante = new Comprobante();
        FileLoader fl = new FileLoader("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\main\\resources\\files\\","R37_tax_afip_sales_Ago16_01.csv");
        fl.start();
        BufferedReader originFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\main\\resources\\files\\R37_tax_afip_sales_Ago16_01.csv")),"Cp1252"));
        BufferedReader fixedFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\nicolas.moreno\\IdeaProjects\\Fixer\\src\\main\\resources\\files\\R37_tax_afip_sales_Ago16_01.NEW")),"Cp1252"));
        int initFileLines = countLines(originFileReader);
        int fixedFileLines = countLines(fixedFileReader);
        System.out.println("originalFileLines " + initFileLines + " fixedFileLines " + fixedFileLines );
        assertEquals(initFileLines,fixedFileLines);

    }
    private int countLines(BufferedReader reader) throws IOException {
        int counter = 0;
        String line = reader.readLine();
        while ( line != null){
            if(!(line.equals("\u0000"))) counter++;
            line = reader.readLine();
        }
        return counter;
    }
}
