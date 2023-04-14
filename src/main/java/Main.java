import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner airport = new Scanner(System.in);
        BufferedReader reader;
        while (true) {
            int numberOfAirports = 0;
            System.out.println("Enter filter:");
            String filter = airport.nextLine().replaceAll("'", "\"");

            System.out.println("Enter airport:");
            String name = airport.nextLine();
            if (name.contains("!exit")) {
                break;
            }
            try {
                reader = new BufferedReader(new FileReader("../airports.csv"));
                String line;
                long start = System.currentTimeMillis();
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    String[] items = line.split(",");
                    if (items[1].toLowerCase().startsWith('"' + name.toLowerCase())) {
                        String modifiedFilter = ExpressionBuilder.createExpression(filter, items);
                        String[] tokens = ReversePolishNotation.convertToPolish(modifiedFilter);
                        if (ReversePolishNotation.evaluatePolishNotation(tokens)) {
                            System.out.println(items[1] + Arrays.toString(items));
                            numberOfAirports += 1;
                        }
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
}