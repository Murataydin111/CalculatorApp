package com.example.lab1helloworld;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RentalActivity extends AppCompatActivity {

    TextView display;

    double previousResult = 0;
    String currentOperator = "";
    String currentInput = "";
    boolean justPressedEquals = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        display = findViewById(R.id.display);

        // 🔥 SET4 TEST
        runSet4Test();

        // Calculator setup
        setupButtons();
    }

    // 🔥 BACKEND TEST
    private void runSet4Test() {

        Log.d("TEST", "APP STARTED");

        set4.Car car = new set4.Car("BMW", set4.Vehicle.PETROL);
        Log.d("SET4", car.toString());

        Log.d("SET4", "Refuel PETROL: " + car.refuel(set4.Vehicle.PETROL, 10));
        Log.d("SET4", "Refuel DIESEL: " + car.refuel(set4.Vehicle.DIESEL, 5));
        Log.d("SET4", car.toString());

        set4.Bicycle bike = new set4.Bicycle("Giant");
        Log.d("SET4", bike.toString());

        set4.Garage g1 = new set4.Garage(1);

        Log.d("SET4", "Park bike: " + bike.park(g1));
        Log.d("SET4", bike.toString());

        Log.d("SET4", "Unpark bike: " + bike.unpark());
        Log.d("SET4", bike.toString());

        set4.Motorboat boat = new set4.Motorboat("Yamaha", set4.Vehicle.DIESEL);
        Log.d("SET4", boat.toString());

        boat.refuel(set4.Vehicle.DIESEL, 20);
        Log.d("SET4", boat.toString());

        set4.Scooter scooter = new set4.Scooter("Xiaomi");
        Log.d("SET4", scooter.toString());

        // 🔥 RENTAL TEST
        set4.Rental rental = new set4.Rental(5);

        set4.Car rentalCar = new set4.Car("Honda Civic", set4.Vehicle.PETROL | set4.Vehicle.LPG);
        set4.Bicycle rentalBike = new set4.Bicycle("Giant");
        set4.Motorboat rentalBoat = new set4.Motorboat("Super motorboat", set4.Vehicle.DIESEL);
        set4.Scooter rentalScooter = new set4.Scooter("Cool scooter");

        rental.addVehicle(rentalCar);
        rental.addVehicle(rentalBike);
        rental.addVehicle(rentalBoat);
        rental.addVehicle(rentalScooter);

        rental.parkVehicle(rentalCar.getId(), 1);
        rental.parkVehicle(rentalBike.getId(), 2);

        rental.sortVehicles();
        rental.printAllVehicles();

        // 🔥 XML TEST
        set4.Rental xmlRental = new set4.Rental(5);
        xmlRental.loadFromXML(this);
        xmlRental.sortVehicles();
        xmlRental.printAllVehicles();
        xmlRental.saveToXML(this);
    }

    // 🔢 CALCULATOR PART
    private void setupButtons() {

        int[] buttons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv,
                R.id.btnEquals, R.id.btnClear, R.id.btnPow
        };

        for (int id : buttons) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> handleInput(btn.getText().toString()));
        }
    }

    private void handleInput(String value) {

        if (value.matches("[0-9]")) {
            if (justPressedEquals) reset();

            currentInput += value;
            display.setText(currentInput);
        }

        else if (value.equals("C")) {
            reset();
        }

        else if (value.equals("=")) {
            calculate();
            justPressedEquals = true;
        }

        else {
            if (currentInput.isEmpty()) return;

            calculate();
            currentOperator = value;
            justPressedEquals = false;
        }
    }

    private void calculate() {

        if (currentInput.isEmpty()) return;

        double number = Double.parseDouble(currentInput);

        switch (currentOperator) {
            case "+": previousResult += number; break;
            case "-": previousResult -= number; break;
            case "*": previousResult *= number; break;

            case "/":
                if (number == 0) {
                    display.setText("Error");
                    reset();
                    return;
                }
                previousResult /= number;
                break;

            case "num":
                previousResult = Math.pow(previousResult, number);
                break;

            default:
                previousResult = number;
        }

        display.setText(String.valueOf(previousResult));
        currentInput = "";
    }

    private void reset() {
        previousResult = 0;
        currentOperator = "";
        currentInput = "";
        display.setText("0");
        justPressedEquals = false;
    }
}