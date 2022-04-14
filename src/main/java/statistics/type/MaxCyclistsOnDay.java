package statistics.type;

import statistics.Row;
import java.util.List;

public class MaxCyclistsOnDay {
    private int value;
    private int countDays = 0;
    private int maxCyclist;
    private String maxDate;
    private String lastDayReaded;
    private final String shortName;

    public MaxCyclistsOnDay() {
        this.value = 0;
        this.shortName = "MCD";
    }

    public void calculateMaxCyclistsOnDay(List<Row> list){

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
                if (maxCyclist < value){
                    maxDate = lastDayReaded;
                    maxCyclist = value;
                }
                lastDayReaded = date;
                value = 0;
                value += row.getCountCyclist();
                countDays++;
            }
            if (row.equals(list.get(list.size()-1))){
                if (maxCyclist < value){
                    maxDate = lastDayReaded;
                    maxCyclist = value;
                }
            }
        }
    }


    public String getMaxDate() {
        return maxDate;
    }

    public int getMaxCyclist() {
        return maxCyclist;
    }
}
