package set4;

public class Car extends Vehicle implements CombustionVehicle, Parkable {

    private int supportedFuelMask;
    private double fuelAmount;
    private Garage garage;

    public Car(String name, int fuelMask) {
        super(name);
        this.supportedFuelMask = fuelMask;
        this.fuelAmount = 0.0;
        this.garage = null;
    }

    // 🔥 CombustionVehicle

    @Override
    public boolean refuel(int fuelMask, double liters) {
        if (liters <= 0) return false;

        if ((supportedFuelMask & fuelMask) == 0) {
            return false;
        }

        fuelAmount += liters;
        return true;
    }

    @Override
    public int getSupportedFuelMask() {
        return supportedFuelMask;
    }

    @Override
    public double getFuelAmount() {
        return fuelAmount;
    }

    // 🔥 Parkable

    @Override
    public boolean park(Garage g) {
        if (garage != null) return false;
        if (!g.isEmpty()) return false;

        this.garage = g;
        g.setParkedVehicle(this);
        return true;
    }

    @Override
    public boolean unpark() {
        if (garage == null) return false;

        garage.setParkedVehicle(null);
        garage = null;
        return true;
    }

    @Override
    public boolean isParked() {
        return garage != null;
    }

    @Override
    public Garage getGarage() {
        return garage;
    }

    // 🔥 toString

    @Override
    public String toString() {
        return "[" + getId() + "] Car: " + getName()
                + " FuelMask=" + supportedFuelMask
                + " Fuel=" + fuelAmount
                + " Parked=" + (isParked() ? "Yes (Garage " + garage.getNumber() + ")" : "No");
    }
}