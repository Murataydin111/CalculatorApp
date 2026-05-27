package com.example.lab1helloworld.set5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1helloworld.R;

public class ResultActivity extends AppCompatActivity {

    Button restartButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        restartButton =
                findViewById(R.id.restartButton);

        backButton =
                findViewById(R.id.backButton);

        restartButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(ResultActivity.this,
                            GameActivity.class);

            startActivity(intent);

            finish();

        });

        backButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(ResultActivity.this,
                            MainActivity.class);

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);

            finish();

        });
    }
}