package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);
        Intent intent = getIntent();

        TextView score = findViewById(R.id.score);
        score.setText("Su puntuación es de: \n          "+ intent.getStringExtra("POINTS") +" puntos");

        Button replay = findViewById(R.id.replay);
        replay.setText("Volver a Jugar");
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }

    protected void changeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
