package utils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class CsvUtil {
    public static List<String[]> read(InputStream inputStream, String code) throws IOException {
        List<String[]> ret = new ArrayList<>();
        CsvReader reader = new CsvReader(inputStream, ',', Charset.forName(code));
        //reader.readHeaders();
        while (reader.readRecord()) {
            ret.add(reader.getValues());
        }
        reader.close();
        return ret;
    }

    public static void write(List<String[]> list, String filePath) throws Exception {
        CsvWriter wr = new CsvWriter(filePath, ',', StandardCharsets.UTF_8);
        for (String[] strings : list) {
            wr.writeRecord(strings);
        }
        wr.close();
    }
}