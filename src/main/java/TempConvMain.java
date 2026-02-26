import java.util.Scanner;

public class TempConvMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TemperatureConverter converter = new TemperatureConverter();
        System.out.println("Muutos3.0");
        System.out.println("moi sakke");

        while (true) {
            System.out.println("\nChoose source unit:");
            System.out.println("1 = Celsius");
            System.out.println("2 = Fahrenheit");
            System.out.println("3 = Kelvin");
            System.out.println("0 = Exit");

            int fromUnit = scanner.nextInt();
            if (fromUnit == 0) {
                System.out.println("Bye!");
                break;
            }

            System.out.print("Enter temperature value: ");
            double value = scanner.nextDouble();

            System.out.println("Choose target unit:");
            System.out.println("1 = Celsius");
            System.out.println("2 = Fahrenheit");
            System.out.println("3 = Kelvin");

            int toUnit = scanner.nextInt();

            double result;

            // Conversion logic
            if (fromUnit == 1 && toUnit == 2) {
                result = converter.celsiusToFahrenheit(value);
            } else if (fromUnit == 2 && toUnit == 1) {
                result = converter.fahrenheitToCelsius(value);
            } else if (fromUnit == 3 && toUnit == 1) {
                result = converter.kelvinToCelsius(value);
            } else if (fromUnit == toUnit) {
                result = value;
            } else {
                System.out.println("Conversion not supported.");
                continue;
            }

            System.out.println("Result: " + result);

            // Extreme temperature check (only meaningful in Celsius)
            double celsiusValue = (toUnit == 1)
                    ? result
                    : (fromUnit == 1 ? value : Double.NaN);

            if (!Double.isNaN(celsiusValue) && converter.isExtremeTemperature(celsiusValue)) {
                System.out.println("⚠ Extreme temperature detected!");
            }

            System.out.println("\nConvert another?");
            System.out.println("1 = Yes (new value)");
            System.out.println("2 = Yes (change units)");
            System.out.println("0 = Exit");

            int choice = scanner.nextInt();
            if (choice == 0) break;
        }

        scanner.close();
    }
}