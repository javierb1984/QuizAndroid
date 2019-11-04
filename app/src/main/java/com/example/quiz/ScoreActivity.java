package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private ArrayList<Puntuacion> puntuaciones;

    protected final int[] SCORE_VIEWS = {R.id.score1, R.id.score2, R.id.score3, R.id.score4, R.id.score5, R.id.score6, R.id.score7, R.id.score8, R.id.score9, R.id.score10};
    protected final int[] PLAYER_VIEWS = {R.id.name1, R.id.name2, R.id.name3, R.id.name4, R.id.name5, R.id.name6, R.id.name7, R.id.name8, R.id.name9, R.id.name10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);

        Button replay = findViewById(R.id.replay);
        replay.setText("Volver al Menú");
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });

        //Create a new parser
        Parser parser = new Parser();

        //Read previous stored scores
        puntuaciones = new ArrayList<Puntuacion>();
        puntuaciones = parser.parseScoreXML(getApplicationContext());

        TextView tv;
        for(int i = 0; i < puntuaciones.size(); i++){
            //To use params
            Puntuacion p = puntuaciones.get(i);
            String jugador = (i+1)+"º "+p.getJugador();
            String tiempo = p.getTiempo();
            int puntos = p.getPuntos();

            //Set everything to the same distance
            int addJugador = (18 - jugador.length());
            int addScore = (3 - String.valueOf(puntos).length());

            //Crear cuadros e insertar las puntuaciones en ellos
            tv = findViewById(PLAYER_VIEWS[i]);
            tv.setText(jugador);
            tv = findViewById(SCORE_VIEWS[i]);
            tv.setText(tiempo + "   "+ puntos + setSpaces(addScore) + " puntos");
        }
    }

    private String setSpaces(int n){
        String string = "";
        for(int i = 0; i < n; i++){
            string += " ";
        }
        return string;
    }

    protected void changeActivity(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}
