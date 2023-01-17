package es_34_veicoli;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author JENNIFERPAGANIN
 */
public class Veicoli {
    public static void leggiXML(String file, double val_soglia) throws ParserConfigurationException, SAXException, IOException {
        File fileXml = new File(file);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document docum = dBuilder.parse(fileXml);
        docum.getDocumentElement().normalize();

        NodeList lista_veicolo = docum.getElementsByTagName("veicolo");

        for (int i = 0; i < lista_veicolo.getLength(); i++) {
            Node veicoloN = lista_veicolo.item(i);

            if (veicoloN.getNodeType() == Node.ELEMENT_NODE) {
                Element veicoloE = (Element) veicoloN;
                String id = veicoloE.getElementsByTagName("ID").item(0).getTextContent();
                NodeList misuraL = veicoloE.getElementsByTagName("misura");
                boolean sotto_soglia = true;
                List<Long> timestamp_sopra_soglia = new ArrayList<>();

                for (int j = 0; j < misuraL.getLength(); j++) {
                    Node misuraN = misuraL.item(j);

                    if (misuraN.getNodeType() == Node.ELEMENT_NODE) {
                        Element misuraE = (Element) misuraN;
                        double temperature = Double.parseDouble(misuraE.getElementsByTagName("temperatura").item(0).getTextContent());
                        String dataOra = misuraE.getElementsByTagName("data_ora").item(0).getTextContent();

                        if (temperature > val_soglia) {
                            sotto_soglia = false;
                            //La classe LocalDateTime serve per convertire la stringa dataOra in un oggetto Timestamp
                            LocalDateTime date = LocalDateTime.parse(dataOra);
                            long timestamp = date.toEpochSecond(ZoneOffset.UTC);
                            timestamp_sopra_soglia.add(timestamp);
                        }
                    }
                }

                System.out.println("Il veicolo con questo ID: " + id + " ha tutte le temperature sotto la soglia: " + sotto_soglia);
                if (!sotto_soglia) {
                    System.out.println("Timestamp in cui la temperatura supera il valore di soglia: " + timestamp_sopra_soglia);
                }
            }
        }
    }
}
