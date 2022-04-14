import reader.CSVReader;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args){
        String path = args[0];
        List<String> typesStatistic =  Arrays.asList(args[1].split(","));
        List<String> typesExports = Arrays.asList(args[2].split(","));

        CSVReader er = new CSVReader(path, typesStatistic , typesExports);
        try {
            er.readCsvFile();
        } catch (ParseException | TransformerException | IOException e) {
            e.printStackTrace();
        }
    }

}
