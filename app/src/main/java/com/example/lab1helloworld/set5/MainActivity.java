package com.example.lab1helloworld.set5;

/*
Name: Murat Aydın
Student ID: 56429
Lab: Set 5 - Maze Game
*/

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1helloworld.R;

public class MainActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(MainActivity.this,
                            GameActivity.class);

            startActivity(intent);

        });
    }
}