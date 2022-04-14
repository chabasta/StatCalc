package export.type;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import statistics.StatisticCalculator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExportDatJSON {

    private final StatisticCalculator stc;

    public ExportDatJSON(StatisticCalculator stc) {
        this.stc = stc;
    }

    public void createJson(List<String> typesStatistic){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        JSONArray statistics = new JSONArray();

        for (String type: typesStatistic){
            switch (type){
                case "AC": statistics.add(addAllCyclistStatistic());
                    break;
                case "ACT": statistics.add(addAvarageCyclistTravels());
                    break;
                case "ACF": statistics.add(addAllCyclistsFromDay());
                    break;
                case "MCD": statistics.add(addMaxCyclistOnDay());
                    break;
                default:
                    System.out.println("Neznama statistica");
                    break;
            }
        }

        //Creating a JSONObject object
        JSONObject report = new JSONObject();
        report.put("created", dtf.format(now));
        report.put("statistics", statistics);

        try {
            FileWriter file = new FileWriter("src/main/resources/exporFiles/json_export.json");
            file.write(report.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object addAllCyclistStatistic() {
        JSONObject allCyclistStatistic = new JSONObject();

        allCyclistStatistic.put("name", "All cyclists from all days");
        allCyclistStatistic.put("result", String.valueOf(stc.getAc().getValue()));

        return allCyclistStatistic;
    }

    private Object addAvarageCyclistTravels() {
        JSONObject avarageCyclistTravels = new JSONObject();
        avarageCyclistTravels.put("name", "Arithmetic mean of cyclists' journeys for all days");
        avarageCyclistTravels.put("result", String.valueOf(stc.getAct().getValue()));
        return avarageCyclistTravels;
    }

    private Object addAllCyclistsFromDay() {
        JSONArray allCyclistsFromDay = new JSONArray();

        JSONObject obj = new JSONObject();

        Map<String, Integer> result = stc.getAcf().getMap().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            obj.put("day", entry.getKey());
            obj.put("result", String.valueOf(entry.getValue()));
            allCyclistsFromDay.add(obj);
        }

        JSONObject nameObj = new JSONObject();
        nameObj.put("name", "Count cyclists travels from every day");
        nameObj.put("results", allCyclistsFromDay);

        return nameObj;
    }

    private Object addMaxCyclistOnDay() {
        JSONArray maxCyclistOnDay = new JSONArray();

        JSONObject obj = new JSONObject();

        obj.put("name", "Day with the maximum cyclist count");

        obj.put("day", stc.getMcd().getMaxDate());
        obj.put("count", String.valueOf(stc.getMcd().getMaxCyclist()));

        maxCyclistOnDay.add(obj);

        return maxCyclistOnDay;

    }

}
