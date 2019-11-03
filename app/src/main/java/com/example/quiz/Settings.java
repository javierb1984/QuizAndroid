package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    int nPreguntas = 5;

    Button button5;
    Button button10;
    Button button15;

    Parser parser = new Parser();

    private final String saveFile = "setttingsFile.xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        ImageButton back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });

        button5 = findViewById(R.id.button5);
        button10 = findViewById(R.id.button10);
        button15 = findViewById(R.id.button15);

        nPreguntas = parser.parseSettingsXML(getApplicationContext());

        switch(nPreguntas) {
            case 5:
                button5.setPressed(true);
                break;
            case 10:
                button10.setPressed(true);
                break;
            case 15:
                button15.setPressed(true);
                break;
        }

        button5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nPreguntas = 5;

                button5.setPressed(true);
                button10.setPressed(false);
                button15.setPressed(false);

                return true;
            }
        });

        button10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nPreguntas = 10;

                button5.setPressed(false);
                button10.setPressed(true);
                button15.setPressed(false);

                return true;
            }
        });

        button15.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nPreguntas = 15;

                button5.setPressed(false);
                button10.setPressed(false);
                button15.setPressed(true);

                return true;
            }
        });
    }

    protected void changeActivity(){
        parser.writeSettingsXML(getApplicationContext(), nPreguntas);

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
