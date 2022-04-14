package export.type;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import statistics.StatisticCalculator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ExportDatXML {

    private DocumentBuilder             builder = null;
    private Document                    document;
    private final StatisticCalculator   stc;

    public ExportDatXML(StatisticCalculator stc)  {
        this.stc = stc;
    }

    public void createXML(List<String> typesStatistic) throws TransformerException, FileNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        document = builder.newDocument();

        Element report = document.createElement("report");

        Element created = document.createElement("created");
        Text actualDate = document.createTextNode(dtf.format(now));
        created.appendChild(actualDate);

        Element statisticResult = document.createElement("statisticResult");

        for (String type: typesStatistic){
            switch (type){
                case "AC": statisticResult.appendChild(addAllCyclistStatistic());
                    break;
                case "ACT": statisticResult.appendChild(addAvarageCyclistTravels());
                    break;
                case "ACF": statisticResult.appendChild(addAllCyclistsFromDay());
                    break;
                case "MCD": statisticResult.appendChild(addMaxCyclistOnDay());
                    break;
                default:
                    System.out.println("Neznama statistica");
                    break;
            }
        }
        report.appendChild(created);
        report.appendChild(statisticResult);

        document.appendChild(report);

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/main/resources/exporFiles/xml_export.xml")));
    }

    private Element addAllCyclistStatistic(){
        Element statistics = document.createElement("statistics");

        Element name = document.createElement("name");
        Text description = document.createTextNode("All cyclists from all days");
        name.appendChild(description);

        Element result = document.createElement("result");
        Text value = document.createTextNode(String.valueOf(stc.getAc().getValue()));
        result.appendChild(value);

        statistics.appendChild(name);
        statistics.appendChild(result);

        return statistics;
    }

    private  Element addAvarageCyclistTravels(){
        Element statistics = document.createElement("statistics");

        Element name = document.createElement("name");
        Text description = document.createTextNode("Arithmetic mean of cyclists' journeys for all days");
        name.appendChild(description);

        Element result = document.createElement("result");
        Text value = document.createTextNode(String.valueOf(stc.getAct().getValue()));
        result.appendChild(value);

        statistics.appendChild(name);
        statistics.appendChild(result);

        return statistics;
    }

    private Element addAllCyclistsFromDay(){
        Element statistics = document.createElement("statistics");

        Element name = document.createElement("name");
        Text description = document.createTextNode("Cyclists' travels from every day");
        name.appendChild(description);

        statistics.appendChild(name);

        Map<String, Integer> result = stc.getAcf().getMap().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for (Map.Entry<String, Integer> entry : result.entrySet()){

            Element result1 = document.createElement("result");

            Element day = document.createElement("day");

            Text dayText = document.createTextNode(entry.getKey());
            day.appendChild(dayText);

            Element countCyclists = document.createElement("count");

            Text countCyclistText = document.createTextNode(String.valueOf(entry.getValue()));
            countCyclists.appendChild(countCyclistText);

            result1.appendChild(day);
            result1.appendChild(countCyclists);


            statistics.appendChild(result1);

        }

        return statistics;
    }

    private Element addMaxCyclistOnDay(){
        Element statistics = document.createElement("statistics");

        Element name = document.createElement("name");
        Text description = document.createTextNode("Day with the maximum cyclist count");
        name.appendChild(description);

        Element result = document.createElement("result");

        Element day = document.createElement("day");

        Text dayText = document.createTextNode(stc.getMcd().getMaxDate());
        day.appendChild(dayText);

        Element countCyclists = document.createElement("count");

        Text countCyclistText = document.createTextNode(String.valueOf(stc.getMcd().getMaxCyclist()));
        countCyclists.appendChild(countCyclistText);


        result.appendChild(day);
        result.appendChild(countCyclists);

        statistics.appendChild(name);
        statistics.appendChild(result);

        return statistics;
    }

}
