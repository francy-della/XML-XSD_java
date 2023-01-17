package es_34_veicoli;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import static es_34_veicoli.Veicoli.leggiXML;
import java.io.IOException;

/**
 *
 * @author JENNIFERPAGANIN
 */
public class Es_34_veicoli {
    public static void main(String[] args) {
        double val_soglia = -30.5;
        String file = "veicoli.xml";
        
        System.out.println("-------------------------");
        System.out.println("VALORE DI SOGLIA: " + val_soglia);
        System.out.println("-------------------------");
        
        try {
            leggiXML(file, val_soglia);
            System.out.println("\n");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("ERRORE! " + e.getMessage());
        }
    }   
}
