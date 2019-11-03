package com.example.quiz;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

public class Parser {

    //Score
    private final String scoreFile = "scoresFile.xml";
    private ArrayList<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();;

    //Preguntas
    private final String preguntasFile = "preguntas.xml";
    private ArrayList<Pregunta> preguntas;

    //Settings
    private final String settingsFile = "settingsFile.xml";
    private int nPreguntas = 0;

    public Parser() {
    }

    //Score
    public ArrayList<Puntuacion> parseScoreXML(Context context){

        XmlPullParserFactory parserFactory;
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            FileInputStream fin = context.openFileInput(scoreFile);
            DataInputStream dis = new DataInputStream(fin);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(dis, null);

            processParserScore(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return puntuaciones;
        }
    }

    private void processParserScore(XmlPullParser parser) throws XmlPullParserException, IOException {
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

    public void writeScoreXML(Context context, ArrayList<Puntuacion> puntuaciones2){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(scoreFile, context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "puntuaciones");

            setScores(xmlSerializer, puntuaciones2);

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

    private void setScores(XmlSerializer xmlSerializer, ArrayList<Puntuacion> puntuaciones2) throws IOException {
        for(int i = 0; i < puntuaciones2.size(); i++){
            xmlSerializer.startTag(null, "puntuacion");

            xmlSerializer.startTag(null, "usuario");
            xmlSerializer.text(puntuaciones2.get(i).getJugador());
            xmlSerializer.endTag(null, "usuario");

            xmlSerializer.startTag(null, "puntos");
            xmlSerializer.text(String.valueOf(puntuaciones2.get(i).getPuntos()));
            xmlSerializer.endTag(null, "puntos");

            xmlSerializer.endTag(null, "puntuacion");
        }
    }

    //Preguntas
    public ArrayList<Pregunta> parsePreguntasXML(Context context){
        XmlPullParserFactory parserFactory;
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = context.getAssets().open(preguntasFile);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParserPreguntas(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return preguntas;
        }
    }

    private void processParserPreguntas(XmlPullParser parser) throws XmlPullParserException, IOException {

        preguntas = new ArrayList<Pregunta>();
        int eventType = parser.getEventType();
        Pregunta pregunta = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String nameTag = null;

            switch (eventType){
                case XmlPullParser.START_TAG:
                    nameTag = parser.getName();

                    if(nameTag.equals("pregunta")) {
                        pregunta = new Pregunta();
                        preguntas.add(pregunta);
                    }
                    else if(pregunta != null){
                        if(nameTag.equals("texto"))
                            pregunta.setTexto(parser.nextText());
                        else if(nameTag.equals("respuestas"))
                            pregunta.setRespuesta(parser.nextText().split(","));
                        else if(nameTag.equals("correcta"))
                            pregunta.setCorrecta(Integer.parseInt(parser.nextText()));
                        else if(nameTag.equals("tipo"))
                            pregunta.setTipo(Enum.valueOf(Type.class, parser.nextText()));
                        else if(nameTag.equals("ruta"))
                            pregunta.setRuta(parser.nextText().split(","));
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    //Settings
    public int parseSettingsXML(Context context){

        XmlPullParserFactory parserFactory;

        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            FileInputStream fin = context.openFileInput(settingsFile);
            DataInputStream dis = new DataInputStream(fin);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(dis, null);

            processParserSettings(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return nPreguntas;
        }
    }

    private void processParserSettings(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT){
            String nameTag = null;

            switch (eventType){
                case XmlPullParser.START_TAG:
                    nameTag = parser.getName();

                    if(nameTag.equals("nPreguntas")) {
                        nPreguntas = Integer.parseInt(parser.nextText());
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    public void writeSettingsXML(Context context, int nPreguntas){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(settingsFile, context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "settings");

            //Set
            xmlSerializer.startTag(null, "nPreguntas");
            xmlSerializer.text(String.valueOf(nPreguntas));
            xmlSerializer.endTag(null, "nPreguntas");
            //*/

            xmlSerializer.endTag(null, "settings");
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
}
