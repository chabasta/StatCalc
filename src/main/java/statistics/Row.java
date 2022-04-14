package statistics;

public class Row {

    private final String actualCameraId;
    private final String targetCameraId;
    private final String startTime;
    private final String endTime;
    private final int    countCyclist;

    public Row(String row) {
        String[] columns = row.split(",");

        this.actualCameraId = columns[0];
        this.targetCameraId = columns[1];
        this.startTime = columns[2];
        this.endTime = columns[3];
        if (columns.length == 4){
            this.countCyclist = 0;
        }
        else this.countCyclist = Integer.parseInt(columns[4]);
    }

    public String getStartTime() {
        return startTime;
    }

    public int getCountCyclist() {
        return countCyclist;
    }
}
