package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataReader {

    public static Object[][] readCSV(String filePath)
    {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            br.readLine();

            while((line=br.readLine())!=null){
                String[] values=line.split(",",-1);

                data.add(new Object[]{
                        values[0].trim(),
                        values[1].trim()
                });
            }
        } catch (IOException e){
            throw new RuntimeException(
                    "Unable to read CSV file:" + filePath, e
            );
        }
        return data.toArray(new Object[0][]);
    }
}
