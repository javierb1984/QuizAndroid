package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        Button play = findViewById(R.id.buttonPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(MainActivity.class);
            }
        });

        Button settings = findViewById(R.id.buttonSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(SettingsActivity.class);
            }
        });

        Button score = findViewById(R.id.buttonLeaderboard);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ScoreActivity.class);
            }
        });
    }

    protected void changeActivity(Class gotoClass){
        Intent intent = new Intent(this, gotoClass);
        startActivity(intent);
    }
}
