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
import java.io.StringWriter;
import java.util.ArrayList;

public class ScoreParser {

    private final String saveFile = "scoresFile.xml";
    private ArrayList<Puntuacion> puntuaciones;

    public ScoreParser() {
    }

    public ArrayList<Puntuacion> parseXML(Context context){
;
        XmlPullParserFactory parserFactory;
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            FileInputStream fin = context.openFileInput(saveFile);
            DataInputStream dis = new DataInputStream(fin);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(dis, null);

            puntuaciones = new ArrayList<Puntuacion>();
            processParser(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return puntuaciones;
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

    public void writeXML(Context context, ArrayList<Puntuacion> puntuaciones2){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(saveFile, context.MODE_PRIVATE);
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
}
