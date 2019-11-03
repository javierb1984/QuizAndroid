package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity {

    private ArrayList<Puntuacion> puntuaciones;

    private String saveFile = "scores.xml";
    private Puntuacion puntuacion;
    private String usuario = "usuario";

    protected final int[] SCORE_VIEWS = {R.id.score1, R.id.score2, R.id.score3, R.id.score4, R.id.score5, R.id.score6, R.id.score7, R.id.score8, R.id.score9, R.id.score10};
    protected final int[] PLAYER_VIEWS = {R.id.name1, R.id.name2, R.id.name3, R.id.name4, R.id.name5, R.id.name6, R.id.name7, R.id.name8, R.id.name9, R.id.name10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);
        Intent intent = getIntent();

        TextView score_title = findViewById(R.id.score_title);

        Button replay = findViewById(R.id.replay);
        replay.setText("Volver a Jugar");
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });

        //Read previous stored scores
        puntuaciones = new ArrayList<Puntuacion>();
        parseXML();

        //Add new player score
        puntuacion = new Puntuacion();
        puntuacion.setJugador(usuario+((int)Math.random()*10));
        puntuacion.setPuntos(Integer.parseInt(intent.getStringExtra("POINTS")));
        puntuaciones.add(puntuacion);

        //Sort scores by highest
        Collections.sort(puntuaciones);

        //Setup 10 best scores
        if(puntuaciones.size() > 10){
            puntuaciones = new ArrayList<>(puntuaciones.subList(0, 10));
        }

        //Store new scores
        writeXML();

        TextView tv;
        for(int i = 0; i < puntuaciones.size(); i++){
            //Crear cuadros e insertar las puntuaciones en ellos
            tv = findViewById(PLAYER_VIEWS[i]);
            tv.setText((i+1)+"º   "+puntuaciones.get(i).getJugador());
            tv = findViewById(SCORE_VIEWS[i]);
            tv.setText(puntuaciones.get(i).getPuntos() + "  puntos");
        }


    }

    private void parseXML(){
        XmlPullParserFactory parserFactory;
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            FileInputStream fin = openFileInput(saveFile);
            DataInputStream dis = new DataInputStream(fin);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(dis, null);

            processParser(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processParser(XmlPullParser parser) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
        Puntuacion puntuacion = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String nameTag = null;

            switch (eventType){
                case XmlPullParser.START_TAG:
                    nameTag = parser.getName();

                    if(nameTag.equals("puntuacion")) {
                        puntuacion = new Puntuacion();
                        puntuaciones.add(puntuacion);
                    }
                    else if(puntuacion != null){
                        if(nameTag.equals("usuario"))
                            puntuacion.setJugador(parser.nextText());
                        else if(nameTag.equals("puntos"))
                            puntuacion.setPuntos(Integer.parseInt(parser.nextText()));
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void writeXML(){
            try {
                FileOutputStream fileOutputStream = openFileOutput(saveFile, Context.MODE_PRIVATE);
                XmlSerializer xmlSerializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();

                xmlSerializer.setOutput(writer);
                xmlSerializer.startDocument("UTF-8", true);
                xmlSerializer.startTag(null, "puntuaciones");

                setScores(xmlSerializer);

                xmlSerializer.endTag(null, "puntuaciones");
                xmlSerializer.endDocument();
                xmlSerializer.flush();

                fileOutputStream.write(writer.toString().getBytes());
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IllegalArgumentException e) {
                e.printStackTrace();

            } catch (IllegalStateException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    private void setScores(XmlSerializer xmlSerializer) throws IOException {
        for(int i = 0; i < puntuaciones.size(); i++){
            xmlSerializer.startTag(null, "puntuacion");

                xmlSerializer.startTag(null, "usuario");
                    xmlSerializer.text(puntuaciones.get(i).getJugador());
                xmlSerializer.endTag(null, "usuario");

                xmlSerializer.startTag(null, "puntos");
                    xmlSerializer.text(String.valueOf(puntuaciones.get(i).getPuntos()));
                xmlSerializer.endTag(null, "puntos");

            xmlSerializer.endTag(null, "puntuacion");
        }
    }

    protected void changeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
