import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configurator configurator = new Configurator();
        Simulation simulation = configurator.configure();
        simulation.init();
        simulation.turn();
        Thread thread = new Thread(simulation);
        thread.start();
        while (simulation.isRunning()) {
            switch (scanner.nextLine().trim()) {
                case "1" -> simulation.turn();
                case "2" -> simulation.start();
                case "3" -> {
                    System.out.println("Simulation paused!");
                    simulation.pause();
                }
                case "4" -> System.exit(0);
                default -> System.out.println("Invalid input!");
            }
        }
        System.out.println("Simulation over!");
    }
}
