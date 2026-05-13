package set4;

public class Bicycle extends Vehicle implements Parkable {

    private Garage garage;

    public Bicycle(String name) {
        super(name);
        this.garage = null;
    }

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

    @Override
    public String toString() {
        return "[" + getId() + "] Bicycle: " + getName()
                + " Parked=" + (isParked() ? "Yes (Garage " + garage.getNumber() + ")" : "No");
    }
}