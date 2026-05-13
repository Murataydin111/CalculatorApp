package set4;

public class TestMain {

    public static void main(String[] args) {

        // 🚗 Car test
        Car car = new Car("BMW", CombustionVehicle.PETROL);
        System.out.println(car);

        System.out.println("Refuel PETROL: " + car.refuel(CombustionVehicle.PETROL, 10));
        System.out.println("Refuel DIESEL: " + car.refuel(CombustionVehicle.DIESEL, 5));
        System.out.println(car);

        // 🚲 Bicycle test
        Bicycle bike = new Bicycle("Giant");
        System.out.println(bike);

        // 🏠 Garage test
        Garage g1 = new Garage(1);

        System.out.println("Park bike: " + bike.park(g1));
        System.out.println(bike);

        System.out.println("Unpark bike: " + bike.unpark());
        System.out.println(bike);

        // 🚤 Motorboat test
        Motorboat boat = new Motorboat("Yamaha", CombustionVehicle.DIESEL);
        System.out.println(boat);

        boat.refuel(CombustionVehicle.DIESEL, 20);
        System.out.println(boat);

        // 🛴 Scooter test
        Scooter scooter = new Scooter("Xiaomi");
        System.out.println(scooter);
    }
}

