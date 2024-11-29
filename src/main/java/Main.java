import controllers.ConfigurationController;
import controllers.SimulationController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConfigurationController configurationController = new ConfigurationController();
        SimulationController simulationController = configurationController.configure();
        Thread thread = new Thread(simulationController);
        thread.start();
        simulationController.turn();

        while (simulationController.isRunning()) {
            switch (scanner.nextLine().trim()) {
                case "1" -> simulationController.turn();
                case "2" -> simulationController.start();
                case "3" -> {
                    System.out.println("Simulation paused!");
                    simulationController.pause();
                }
                case "4" -> System.exit(0);
                default -> System.out.println("Invalid input!");
            }
        }
        System.out.println("Simulation over!");
    }
}
