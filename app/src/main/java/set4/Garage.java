package set4;

public class Garage {

    private final int number;
    private Parkable parkedVehicle; // null = empty

    public Garage(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isEmpty() {
        return parkedVehicle == null;
    }

    public Parkable getParkedVehicle() {
        return parkedVehicle;
    }

    // 👉 Bu methodu Parkable implementasyonları çağıracak
    public void setParkedVehicle(Parkable vehicle) {
        this.parkedVehicle = vehicle;
    }
}