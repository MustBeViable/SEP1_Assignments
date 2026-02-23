import java.util.Scanner;

public class SpeedConv {
    public static double milesToKilometers(double km) {
        return km * 1.61;
    }

    public static double kilometresToMiles(double ml) {
        return ml / 1.61;
    }

    static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give speed");
        double speed = Double.parseDouble(scanner.nextLine());
        System.out.println("from speed (k/m): ");
        String unit = scanner.nextLine();

        switch (unit) {
            case ("k") -> System.out.printf("speed is: %f miles/h", SpeedConv.kilometresToMiles(speed));
            case ("m") -> System.out.printf("Speed is: %f km/h", SpeedConv.milesToKilometers(speed));
        }
    }

}
