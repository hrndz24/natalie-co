package natalie;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class DatabaseReader {

    private static String path = "natalie&co.txt";
    private static ArrayList<Automobile> automobiles;
    private static File database = new File(path);

    public static ArrayList<Automobile> readDatabase() throws IOException {
        automobiles = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(database));
        String line;
        String[] words;
        int n = 1;
        while ((line = bufferedReader.readLine()) != null) {
            words = line.split(", ");
            automobiles.add(new Automobile(words[0], words[1], words[2], words[3], words[4], n));
            n++;
        }
        return automobiles;
    }

    public static void addToDatabase(Automobile automobile) throws IOException{
        automobiles.add(automobile);
        updateDatabase();
    }

    public static void deleteFromDatabase(Automobile automobile) throws IOException {
        automobiles.remove(automobile.getSerialNumber()-1);
        updateDatabase();
    }

    public static void editInDatabase(Automobile automobile) throws IOException {
        automobiles.set(automobile.getSerialNumber()-1, automobile);
        updateDatabase();
    }

    private static void updateDatabase() throws IOException{
        automobiles.sort(new AutomobileComparator());
        //new FileWriter(database, false).close(); // clears file
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        raf.setLength(0);
        for (Automobile a : automobiles) {
            raf.writeBytes(a.toString() + "\r\n");
        }
    }

    private static class AutomobileComparator implements Comparator<Automobile>{
        // alphabetically
        @Override
        public int compare(Automobile o1, Automobile o2) {
            if (o1.getManufacturer().equals(o2.getManufacturer())) {
                return o1.getModel().compareTo(o2.getModel());
            } else {
                return o1.getManufacturer().compareTo(o2.getManufacturer());
            }
        }
    }
}
