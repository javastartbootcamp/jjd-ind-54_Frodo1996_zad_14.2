package pl.javastart.task;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class VehicleInspectionStationSystem {

    private static final int EXIT = 0;
    private static final int ADD_NEW_VEHICLE_TO_QUEUE = 1;
    private static final int CONTROL_VEHICLE = 2;
    private static final String FILE_NAME = "vehiclesQueue.txt";

    Queue<Vehicle> vehicles = new PriorityQueue<>();

    void mainLoop() {
        readLastQueueSaveBeforeTurningOnApp();
        readOptionsFromUser();
        printInfoAboutSavedVehicles();
    }

    private void readVehiclesFromFile(Queue<Vehicle> vehicles) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(FILE_NAME));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(";");
            String type = split[0];
            String brand = split[1];
            String model = split[2];
            int productionYear = Integer.parseInt(split[3]);
            double mileage = Double.parseDouble(split[4]);
            String vin = split[5];
            vehicles.add(new Vehicle(type, brand, model, productionYear, mileage, vin));
            System.out.println(line);
        }
    }

    private void saveVehiclesQueueInFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (Vehicle vehicle : vehicles) {
            writer.write(vehicle.toString());
            writer.newLine();
        }
        writer.close();
    }

    private void printInfoAboutSavedVehicles() {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    private void controlNextVehicle() {
        if (!vehicles.isEmpty()) {
            System.out.println(vehicles.stream().findFirst());
            vehicles.poll();
        } else {
            System.err.println("Brak aut zarejestrowanych na przegląd," +
                    " rejestrując swój pojazd będziesz pierwszy w kolejce.");
        }
    }

    private Vehicle addVehicleToQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jeśli chcesz dodać pojazd do kolejki na przegląd to podaj pierw następujące informacje o nim:");
        System.out.println(" > Typ (np. auto, motor):");
        String type = scanner.nextLine();
        System.out.println(" > Marka:");
        String mark = scanner.nextLine();
        System.out.println(" > Model:");
        String model = scanner.nextLine();
        System.out.println(" > Rok produkcji:");
        int productionYear = scanner.nextInt();
        scanner.nextLine();
        System.out.println(" > Przebieg w km:");
        double mileage = scanner.nextDouble();
        scanner.nextLine();
        System.out.println(" > Nr VIN:");
        String vin = scanner.nextLine();
        return new Vehicle(type, mark, model, productionYear, mileage, vin);
    }

    private void saveFileBeforeClosingAppIfQueueIsNotEmpty(Scanner scanner) {
        if (vehicles.isEmpty()) {
            scanner.close();
        } else {
            try {
                saveVehiclesQueueInFile();
                scanner.close();
            } catch (IOException e) {
                System.err.println("Błąd! Nie udało się zapisać pliku.");
            }
        }
    }

    private void readOptionsFromUser() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            printOptions();
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case EXIT -> saveFileBeforeClosingAppIfQueueIsNotEmpty(scanner);
                case ADD_NEW_VEHICLE_TO_QUEUE -> vehicles.add(addVehicleToQueue());
                case CONTROL_VEHICLE -> controlNextVehicle();
                default -> System.err.println("Podałeś złą wartość, spróbuj ponownie.");
            }
        } while (option != EXIT);
    }

    private void readLastQueueSaveBeforeTurningOnApp() {
        if (vehicles.isEmpty()) {
            try {
                readVehiclesFromFile(vehicles);
            } catch (FileNotFoundException e) {
                System.err.println("Błąd podczas wczytywania danych z pliku, lub plik nie istnieje.");
            }
        }
    }

    private void printOptions() {
        System.out.println("Witaj w naszej stacji kontroli pojazdów!\n" +
                "Masz do wyboru trzy opcje, którą z nich jesteś zainteresowany?");
        System.out.println(" > Wybierz [" + EXIT +
                "] jeśli chcesz, aby program zakończył swe działanie");
        System.out.println(" > Wybierz [" + ADD_NEW_VEHICLE_TO_QUEUE +
                "] by zarejestrować swój pojazd i ustawić go w kolejce do kontroli");
        System.out.println(" > Wybierz [" + CONTROL_VEHICLE +
                "] jeżeli chciałbyś wyświetlić informacje o kolejnym pojeździe, który będzie poddany kontroli");
    }
}