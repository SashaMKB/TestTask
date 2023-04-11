import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner airport = new Scanner(System.in);
        List<Airport> airports = ParseAirportCsv("airports.csv");
        while (true) {
            System.out.println("Enter airport:");
            String name = airport.nextLine();
            if (name.contains("!exit")){
                break;
            }
            int count_airports = 0;
            long start = System.currentTimeMillis();
            for (Airport names : airports) {
                if (names.name.toLowerCase().contains(name.toLowerCase()) && names.name.toUpperCase().contains(name.toUpperCase())) {
                    System.out.println(names.name);
                    count_airports += 1;
                }
            }
            long finish = System.currentTimeMillis();
            long result = finish - start;
            System.out.println("Количество найденных строк: " + count_airports + " Время, затраченное на поиcк: " + result + "мс");
        }
    }

    private static List<Airport> ParseAirportCsv(String filePath) throws IOException {
        //Загружаем строки из файла
        List<Airport> Airports = new ArrayList<Airport>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));
        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.split(",");
            ArrayList<String> columnList = new ArrayList<String>();
            for (int i = 0; i < splitedText.length; i++) {
                //Если колонка начинается на кавычки или заканчиваеться на кавычки
                if (IsColumnPart(splitedText[i])) {
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + ","+ splitedText[i]);
                } else {
                    columnList.add(splitedText[i]);
                }
            }
            Airport Airport = new Airport();
            Airport.number = columnList.get(0);
            Airport.name = columnList.get(1);
            Airport.column_3 = columnList.get(2);
            Airport.column_4 = columnList.get(3);
            Airport.column_5 = columnList.get(4);
            Airport.column_6 = columnList.get(5);
            Airport.column_7 = columnList.get(6);
            Airport.column_8 = columnList.get(7);
            Airport.column_9 = columnList.get(8);
            Airport.column_10 = columnList.get(9);
            Airport.column_11 = columnList.get(10);
            Airport.column_12 = columnList.get(11);
            Airport.column_13 = columnList.get(12);
            Airport.column_14 = columnList.get(13);
            Airports.add(Airport);
        }
        return Airports;
    }

    //Проверка является ли колонка частью предыдущей колонки
    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }
}