package Libraries;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TSVParser {
    protected Map<String, String> itemList;


    public TSVParser(String tsvFile) {
        itemList = new HashMap<>();
        tsvParser(tsvFile);
    }

    protected void tsvParser(String tsvFile) {
        try (CSVReader reader = new CSVReader(new FileReader(tsvFile))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String line : nextLine) {
                    itemList.put(line.split("\t")[0], line.split("\t")[1]);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCategory(String title) {
        String category = itemList.get(title);
        if (category != null) {
            return itemList.get(title);
        }
        return "другое";
    }

}
