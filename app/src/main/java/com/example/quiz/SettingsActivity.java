package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    int nPreguntas = 5;

    Button button5;
    Button button10;
    Button button15;
    EditText username;

    Parser parser = new Parser();

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

        username = findViewById(R.id.username);
        button5 = findViewById(R.id.button5);
        button10 = findViewById(R.id.button10);
        button15 = findViewById(R.id.button15);

        Settings settings = parser.parseSettingsXML(getApplicationContext());
        nPreguntas = settings.getnPreguntas();
        username.setText(settings.getUsuario());

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

        //Get username without blank spaces
        String user = username.getText().toString().replaceAll("\\s+", "");

        //If username is empty we asign default username
        if(user.equals("")){
            user = "Anónimo";
        }

        parser.writeSettingsXML(getApplicationContext(), nPreguntas, user);

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
