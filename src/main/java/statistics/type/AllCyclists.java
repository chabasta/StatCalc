package statistics.type;

import statistics.Row;
import java.util.List;

public class AllCyclists {
    private int          value;
    private final String shortName;

    public AllCyclists() {
        this.value = 0;
        this.shortName = "AC";
    }

    public void calculateAllCyclistsCount(List<Row> list){
        for (Row row: list){
            this.value += row.getCountCyclist();
        }
    }

    public int getValue() {
        return value;
    }
}
