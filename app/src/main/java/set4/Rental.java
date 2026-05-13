package set4;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Rental {

    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Garage> garages = new ArrayList<>();

    public Rental(int garageCount) {
        for (int i = 1; i <= garageCount; i++) {
            garages.add(new Garage(i));
        }
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println("Vehicle added: " + vehicle);
    }

    public boolean removeVehicleById(int id) {
        Vehicle vehicle = findVehicleById(id);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return false;
        }

        if (vehicle instanceof Parkable) {
            Parkable parkable = (Parkable) vehicle;
            if (parkable.isParked()) {
                parkable.unpark();
                System.out.println("Vehicle was parked and has been automatically unparked.");
            }
        }

        vehicles.remove(vehicle);
        System.out.println("Vehicle removed.");
        return true;
    }

    public boolean parkVehicle(int vehicleId, int garageNumber) {
        Vehicle vehicle = findVehicleById(vehicleId);
        Garage garage = findGarageByNumber(garageNumber);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return false;
        }

        if (garage == null) {
            System.out.println("Garage not found.");
            return false;
        }

        if (!(vehicle instanceof Parkable)) {
            System.out.println("Vehicle is not parkable.");
            return false;
        }

        Parkable parkable = (Parkable) vehicle;

        if (parkable.isParked()) {
            System.out.println("Vehicle is already parked.");
            return false;
        }

        if (!garage.isEmpty()) {
            System.out.println("Garage is occupied.");
            return false;
        }

        boolean result = parkable.park(garage);

        if (result) {
            System.out.println("Vehicle parked successfully.");
        } else {
            System.out.println("Parking failed.");
        }

        return result;
    }

    public void printAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
            return;
        }

        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    public void sortVehicles() {
        Collections.sort(vehicles, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {

                boolean p1 = isParked(v1);
                boolean p2 = isParked(v2);

                if (p1 != p2) {
                    return p1 ? -1 : 1;
                }

                int typeCompare = Integer.compare(getTypeOrder(v1), getTypeOrder(v2));
                if (typeCompare != 0) return typeCompare;

                int nameCompare = v1.getName().compareToIgnoreCase(v2.getName());
                if (nameCompare != 0) return nameCompare;

                int fuelCompare = Integer.compare(getFuelMask(v1), getFuelMask(v2));
                if (fuelCompare != 0) return fuelCompare;

                return Double.compare(getFuelAmount(v1), getFuelAmount(v2));
            }
        });

        System.out.println("Vehicles sorted.");
    }

    public void loadFromXML(Context context) {
        try {
            XmlResourceParser parser = context.getResources().getXml(
                    context.getResources().getIdentifier("vehicles", "xml", context.getPackageName())
            );

            int eventType = parser.getEventType();
            String currentType = "";
            String name = "";
            int fuelType = 0;

            while (eventType != XmlResourceParser.END_DOCUMENT) {

                if (eventType == XmlResourceParser.START_TAG) {
                    String tag = parser.getName();

                    if (tag.equals("car") || tag.equals("motorboat") ||
                            tag.equals("bicycle") || tag.equals("scooter")) {
                        currentType = tag;
                        name = "";
                        fuelType = 0;
                    }

                    else if (tag.equals("name")) {
                        name = parser.nextText();
                    }

                    else if (tag.equals("fuelType")) {
                        fuelType = Integer.parseInt(parser.nextText());
                    }
                }

                else if (eventType == XmlResourceParser.END_TAG) {
                    String tag = parser.getName();

                    if (tag.equals("car")) {
                        addVehicle(new Car(name, fuelType));
                    }

                    else if (tag.equals("motorboat")) {
                        addVehicle(new Motorboat(name, fuelType));
                    }

                    else if (tag.equals("bicycle")) {
                        addVehicle(new Bicycle(name));
                    }

                    else if (tag.equals("scooter")) {
                        addVehicle(new Scooter(name));
                    }
                }

                eventType = parser.next();
            }

            parser.close();
            System.out.println("XML loaded successfully.");

        } catch (Exception e) {
            System.out.println("XML load error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveToXML(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("vehicles_saved.xml", Context.MODE_PRIVATE);

            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "UTF-8");

            serializer.startDocument("UTF-8", true);
            serializer.startTag(null, "vehicles");

            for (Vehicle vehicle : vehicles) {

                if (vehicle instanceof Car) {
                    serializer.startTag(null, "car");

                    serializer.startTag(null, "name");
                    serializer.text(vehicle.getName());
                    serializer.endTag(null, "name");

                    serializer.startTag(null, "fuelType");
                    serializer.text(String.valueOf(((CombustionVehicle) vehicle).getSupportedFuelMask()));
                    serializer.endTag(null, "fuelType");

                    serializer.endTag(null, "car");
                }

                else if (vehicle instanceof Motorboat) {
                    serializer.startTag(null, "motorboat");

                    serializer.startTag(null, "name");
                    serializer.text(vehicle.getName());
                    serializer.endTag(null, "name");

                    serializer.startTag(null, "fuelType");
                    serializer.text(String.valueOf(((CombustionVehicle) vehicle).getSupportedFuelMask()));
                    serializer.endTag(null, "fuelType");

                    serializer.endTag(null, "motorboat");
                }

                else if (vehicle instanceof Bicycle) {
                    serializer.startTag(null, "bicycle");

                    serializer.startTag(null, "name");
                    serializer.text(vehicle.getName());
                    serializer.endTag(null, "name");

                    serializer.endTag(null, "bicycle");
                }

                else if (vehicle instanceof Scooter) {
                    serializer.startTag(null, "scooter");

                    serializer.startTag(null, "name");
                    serializer.text(vehicle.getName());
                    serializer.endTag(null, "name");

                    serializer.endTag(null, "scooter");
                }
            }

            serializer.endTag(null, "vehicles");
            serializer.endDocument();

            fos.close();

            System.out.println("XML saved successfully as vehicles_saved.xml");

        } catch (Exception e) {
            System.out.println("XML save error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isParked(Vehicle vehicle) {
        if (vehicle instanceof Parkable) {
            return ((Parkable) vehicle).isParked();
        }
        return false;
    }

    private int getTypeOrder(Vehicle vehicle) {
        if (vehicle instanceof Car) return 1;
        if (vehicle instanceof Motorboat) return 2;
        if (vehicle instanceof Bicycle) return 3;
        if (vehicle instanceof Scooter) return 4;
        return 5;
    }

    private int getFuelMask(Vehicle vehicle) {
        if (vehicle instanceof CombustionVehicle) {
            return ((CombustionVehicle) vehicle).getSupportedFuelMask();
        }
        return 0;
    }

    private double getFuelAmount(Vehicle vehicle) {
        if (vehicle instanceof CombustionVehicle) {
            return ((CombustionVehicle) vehicle).getFuelAmount();
        }
        return 0.0;
    }

    private Vehicle findVehicleById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;
    }

    private Garage findGarageByNumber(int number) {
        for (Garage garage : garages) {
            if (garage.getNumber() == number) {
                return garage;
            }
        }
        return null;
    }
}