package com.example.lab1helloworld.set5;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1helloworld.R;

public class GameActivity extends AppCompatActivity {

    Button btnLeft, btnRight, btnUp, btnDown;
    TextView coordText;

    int row = 0;
    int col = 0;

    private final int[][] maze = {
            {18, 10, 9, 8},
            {4, 2, 0, 4},
            {6, 10, 9, 4},
            {2, 1, 2, 1}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);

        coordText = findViewById(R.id.coordText);

        updateRoom();

        btnLeft.setOnClickListener(v -> {
            col--;
            updateRoom();
        });

        btnRight.setOnClickListener(v -> {
            col++;
            updateRoom();
        });

        btnUp.setOnClickListener(v -> {
            row--;
            updateRoom();
        });

        btnDown.setOnClickListener(v -> {
            row++;
            updateRoom();
        });
    }

    private void updateRoom() {

        coordText.setText(
                "Row: " + row +
                        " Col: " + col
        );

        int room = maze[row][col];

        if(room == 0){

            Intent intent =
                    new Intent(GameActivity.this,
                            ResultActivity.class);

            startActivity(intent);

            finish();

            return;
        }

        int movementBits = room & 15;

        boolean canLeft =
                (movementBits & 1) != 0 &&
                        col > 0;

        boolean canRight =
                (movementBits & 2) != 0 &&
                        col < maze[0].length - 1;

        boolean canUp =
                (movementBits & 4) != 0 &&
                        row > 0;

        boolean canDown =
                (movementBits & 8) != 0 &&
                        row < maze.length - 1;

        updateButton(btnLeft, canLeft);
        updateButton(btnRight, canRight);
        updateButton(btnUp, canUp);
        updateButton(btnDown, canDown);
    }

    private void updateButton(Button button,
                              boolean enabled){

        button.setEnabled(enabled);

        if(enabled){
            button.setBackgroundColor(Color.GREEN);
        }
        else{
            button.setBackgroundColor(Color.GRAY);
        }
    }
}