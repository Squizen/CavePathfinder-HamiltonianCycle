import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyFileReader {

    public CaveData readFile(String fileAddress){
        try{
            File file = new File(fileAddress);
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file));

            String passagewaysNumbers = bufferedReader.readLine();

            if(passagewaysNumbers == null){
                throw new RuntimeException("Wrong Cave input data");
            }

            String[] caveDataArr = passagewaysNumbers.split(" ");
            int numberOfPassageways = Integer.valueOf(caveDataArr[0]);
            int numberOfOuterPassageways = Integer.valueOf(caveDataArr[1]);
            int numberOfInnerPassageways = numberOfPassageways - numberOfOuterPassageways;

            List<Passageway> listOfPassageways = new ArrayList<>();
            Set<Chamber> setOfChambers = new HashSet<>();

            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] passagewayData = line.split(" ");
                Chamber startingChamber = new Chamber(Integer.valueOf(passagewayData[0]), false);
                setOfChambers.add(startingChamber);
                Chamber endingChamber = new Chamber(Integer.valueOf(passagewayData[1]), false);
                setOfChambers.add(endingChamber);
                Passageway passageway = new Passageway(startingChamber, endingChamber, Integer.valueOf(passagewayData[2]));
                listOfPassageways.add(passageway);
                passageway = new Passageway(endingChamber, startingChamber, Integer.valueOf(passagewayData[2]));
                listOfPassageways.add(passageway);
            }
            CaveData caveData = new CaveData(numberOfPassageways, numberOfInnerPassageways, numberOfOuterPassageways, listOfPassageways, setOfChambers);
            return caveData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("File not found exception");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
