package reader;

import export.ExportGenerator;
import statistics.Row;
import statistics.StatisticCalculator;

import javax.xml.transform.TransformerException;
import java.io.*;
import java.text.ParseException;
import java.util.*;

public class CSVReader {
    private final String                path;
    private final List<String>          typesStatistic;
    private final List<String>          typesExports;
    private final StatisticCalculator   stc;
    private final ExportGenerator       eg;

    private List<Row> list = new ArrayList<>();

    public CSVReader(String path, List<String> typesStatistic, List<String> typesExports) {
        stc = new StatisticCalculator();
        eg = new ExportGenerator(stc, typesStatistic);

        this.path = path;
        this.typesStatistic = typesStatistic;
        this.typesExports = typesExports;
    }

    public void readCsvFile() throws IOException, ParseException, TransformerException {
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        String row = csvReader.readLine(); //skip first line

        while ((row = csvReader.readLine()) != null) {
            list.add(new Row(row));
        }

        stc.calculateStatistic(list, typesStatistic);
        eg.generateFile(typesExports, typesStatistic);

        csvReader.close();
    }
}
