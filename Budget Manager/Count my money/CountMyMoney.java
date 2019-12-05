import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class CountMyMoney {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        double count;
        double total = 0;
        StringBuilder storeString = new StringBuilder();

        while (keyboard.hasNextLine()){
           // System.out.println("Reading....");
            StringBuilder inputLine = new StringBuilder(keyboard.nextLine());
            count = getNumbersFromLine(inputLine);
            total += count;

            storeString.append("\n" + inputLine);
           // System.out.println("Outputting....");
        }
        System.out.println(storeString);
        System.out.println("\nTotal: $"+total);

    }

    public static double getNumbersFromLine(StringBuilder inputLine) {
        double count = 0.0;

        String regex = "\\$+[0-9]+.+[0-9]";
        Pattern pattern = Pattern.compile(regex);
        String stringToBeMatched = String.valueOf(inputLine);
        Matcher matcher = pattern.matcher(stringToBeMatched);
        while (matcher.find()) {
            String reqString = matcher.group();
            count = Double.parseDouble(reqString.substring(1));
        }

        return count;
    }
}
