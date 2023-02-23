package pl.javastart.task;

public class Vehicle implements Comparable<Vehicle> {
    private final String type;
    private final String brand;
    private final String model;
    private final int productionYear;
    private final double mileage;
    private final String vin;

    public Vehicle(String type, String brand, String model, int productionYear, double mileage, String vin) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.vin = vin;
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        return 0;
    }

    @Override
    public String toString() {
        return type + ";" + brand + ";" + model + ";" + productionYear + ";" + mileage + ";" + vin;
    }
}