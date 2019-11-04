package com.example.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        //We read the name from settings to notify the player if they hasnt set one
        Parser parser = new Parser();
        final Settings data = parser.parseSettingsXML(this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No ha definido un nombre de usuario, ¿Desea continuar?")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeActivity(MainActivity.class);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Volver al menú", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();

        Button play = findViewById(R.id.buttonPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getUsuario().equals("Anónimo"))
                    alert.show();
                else
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
