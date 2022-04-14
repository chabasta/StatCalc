package export;

import export.type.ExportDatJSON;
import export.type.ExportDatXML;
import statistics.StatisticCalculator;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.util.List;

public class ExportGenerator {
    ExportDatXML    xml;
    ExportDatJSON   json;

    public ExportGenerator(StatisticCalculator stc, List<String> typesStatistic) {
        xml = new ExportDatXML(stc);
        json = new ExportDatJSON(stc);
    }

    public void generateFile(List<String> typesExports, List<String> typesStatistic) throws TransformerException, FileNotFoundException {
        for (String type: typesExports){
            switch (type){
                case "XML": xml.createXML(typesStatistic);
                case "JSON": json.createJson(typesStatistic);
            }
        }

    }

}
