public class Main {

    public static void main(String[] args) {

        MyFileReader myFileReader = new MyFileReader();
        CaveData caveData = myFileReader.readFile("src/main/resources/CAV.IN");

    }
}
