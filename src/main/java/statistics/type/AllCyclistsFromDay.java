package statistics.type;

import statistics.Row;
import java.util.*;

public class AllCyclistsFromDay {
    private int                 value;
    private int                 countDays = 0;
    private String              lastDayReaded;
    private final String        shortName;
    private Map<String,Integer> map = new HashMap<String, Integer>();

    public AllCyclistsFromDay() {
        this.value = 0;
        this.shortName = "ACF";
    }

    public void calculateAllCyclistsFromDay(List<Row> list){

        for (Row row: list){
            String date = row.getStartTime().split("T")[0];

            if (countDays == 0){
                lastDayReaded = date;
                countDays++;
            }
            if (date.equals(lastDayReaded)){
                value += row.getCountCyclist();
            }
            else{
                map.put(lastDayReaded, value);
                lastDayReaded = date;
                value = 0;
                value += row.getCountCyclist();
                countDays++;
            }
            if (row.equals(list.get(list.size()-1))){
                map.put(lastDayReaded, value);
            }
        }
    }

    public Map<String, Integer> getMap() {
        return map;
    }
}
