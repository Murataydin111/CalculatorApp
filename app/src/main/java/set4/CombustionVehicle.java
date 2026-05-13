package set4;

public interface CombustionVehicle {

    // 🔥 FUEL TYPES (bitmask)
    public static final int DIESEL = 1 << 0;  // 1
    public static final int PETROL = 1 << 1;  // 2
    public static final int LPG    = 1 << 2;  // 4
    public static final int CNG    = 1 << 3;  // 8

    boolean refuel(int fuelMask, double liters);

    int getSupportedFuelMask();

    double getFuelAmount();
}