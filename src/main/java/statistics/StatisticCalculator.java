package statistics;

import statistics.type.AllCyclists;
import statistics.type.AllCyclistsFromDay;
import statistics.type.AvarageCyclistTravels;
import statistics.type.MaxCyclistsOnDay;
import java.util.List;

public class StatisticCalculator {

    private final AllCyclists               ac;
    private final AvarageCyclistTravels     act;
    private final AllCyclistsFromDay        acf;
    private final MaxCyclistsOnDay          mcd;

    public StatisticCalculator(){
        ac = new AllCyclists();
        act = new AvarageCyclistTravels();
        acf = new AllCyclistsFromDay();
        mcd = new MaxCyclistsOnDay();
    }

    public void calculateStatistic(List<Row> list, List<String> typesStatistic) {
        for (String type : typesStatistic) {
            switch (type) {
                case "AC":
                    ac.calculateAllCyclistsCount(list);
                    break;
                case "ACT":
                    act.calculateAvarageCyclistTravels(list);
                    break;
                case "ACF":
                    acf.calculateAllCyclistsFromDay(list);
                    break;
                case "MCD":
                    mcd.calculateMaxCyclistsOnDay(list);
                    break;
                default:break;
            }
        }
    }

    public AllCyclists getAc() {
        return ac;
    }

    public AvarageCyclistTravels getAct() {
        return act;
    }

    public AllCyclistsFromDay getAcf() {
        return acf;
    }

    public MaxCyclistsOnDay getMcd() {
        return mcd;
    }
}
