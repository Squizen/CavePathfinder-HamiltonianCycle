import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileReader {

    public CaveData readFile(String fileAddress){
        try{
            File file = new File(fileAddress);
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file));
            List<String> fileReadData = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine()) != null){
                fileReadData.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("File not found exception");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return new CaveData();
    }
}
