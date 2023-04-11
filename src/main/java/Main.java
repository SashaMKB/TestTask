import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner airport = new Scanner(System.in);
        BufferedReader reader;
        while (true) {
            int numberOfAirports = 0;
            System.out.println("Enter filter:");
            String filter = airport.nextLine();
            enterFilters(filter);
            System.out.println("Enter airport:");
            String name = airport.nextLine();
            if (name.contains("!exit")) {
                break;
            }
            try {
                reader = new BufferedReader(new FileReader("airports.csv"));
                String line;
                long start = System.currentTimeMillis();
                while ((line = reader.readLine()) != null) {
                    if (findAirport(line,name)) {
                        numberOfAirports += 1;
                    }
                }
                long finish = System.currentTimeMillis();
                long result = finish - start;
                System.out.println("Количество найденных строк: " + numberOfAirports + " Время, затраченное на поиcк: " + result + "мс");
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean findAirport(String line, String name) {
        boolean isFinded = false;
        String[] items = line.split(",");
        if (items[1].toLowerCase().startsWith('"' + name.toLowerCase()) && items[1].toUpperCase().startsWith('"' + name.toUpperCase())) {
            System.out.println(items[1] + Arrays.toString(items));
            isFinded = true;
        }
        return isFinded;
    }

    private static boolean enterFilters(String filter) {
        boolean result = false;
        String[] filters = filter.split("&");
        for (String x: filters) {
            if (x.contains("column")) {
                if(x.indexOf('>') != -1 || x.indexOf('<') != - 1){
                    System.out.println("Ok");
                }
            }
        }
        return result;
    }
}