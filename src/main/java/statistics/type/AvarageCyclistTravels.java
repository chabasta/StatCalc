package statistics.type;

import statistics.Row;
import java.util.*;

public class AvarageCyclistTravels {

    private double       value;
    private int          countDays = 0;
    private int          lastDayReaded = 0;
    private final String shortName;

    public AvarageCyclistTravels() {
        this.value = 0;
        this.shortName = "ACT";
    }

    public void calculateAvarageCyclistTravels(List<Row> list){

        for (Row row: list){
            int day = Integer.parseInt(row.getStartTime().split("T")[0].split("-")[2]);

            if (countDays == 0 || lastDayReaded != day){
                lastDayReaded = day;
                value += row.getCountCyclist();
                countDays++;
            }else {
                value += row.getCountCyclist();
            }
        }
    }

    public double getValue() {
        return value/countDays;
    }
}

