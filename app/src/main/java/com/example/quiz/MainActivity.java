package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quiz.Fragments.ImageAnswer;
import com.example.quiz.Fragments.MediaQuestion;
import com.example.quiz.Fragments.NormalQuestion;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Fragment Manager
    private FragmentManager manager = getSupportFragmentManager();

    //Fragments
    private MediaQuestion mq_fragment = new MediaQuestion();
    private NormalQuestion nq_fragment = new NormalQuestion();
    private ImageAnswer ia_fragment = new ImageAnswer();

    //Question data
    private final int QUESTIONS_TOTAL = 20;
    private int questionNumber = 0;
    private Pregunta currentQuestion;
    private int score = 0;
    private ArrayList<Pregunta> preguntas;
    private ArrayList<Pregunta> randomQuestions;

    //Radio Group
    protected int answerNumber = 0;

    //MainActivity Views
    private TextView scoreView;
    private TextView questionView;

    //Previous parameters
    private Type previousType;
    private TextView oldView;
    private RadioGroup oldRadioGroup;
    private RadioButton[] oldRadiobuttons;
    private Button oldCheck;
    private ImageView oldIView;
    private ImageButton [] oldImagebuttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        parseXML();

        resetAll();
    }

    private void parseXML(){
        XmlPullParserFactory parserFactory;
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("preguntas.xml");

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParser(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processParser(XmlPullParser parser) throws XmlPullParserException, IOException {

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

    private ArrayList<Pregunta> selectRandom(int n){

        //preguntas se clona en aux
        ArrayList<Pregunta> aux = (ArrayList<Pregunta>) preguntas.clone();

        //Reordenamos aux aleatoriamente
        aux = shuffle(aux);

        //Recortamos aux al tamaño n, si no es superior a su tamaño
        if(n < preguntas.size())
            aux = new ArrayList<Pregunta>(aux.subList(0, n));

        return aux;
    }

    private void setupQuestion(){

            if(currentQuestion.getTipo() == previousType){
                setupText(oldView, oldRadioGroup, oldRadiobuttons, oldCheck, oldIView, oldImagebuttons);
            }
            else {
                FragmentTransaction transaction;
                switch (currentQuestion.getTipo()) {

                    case IMAGE:
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.container, ia_fragment);
                        transaction.commit();

                        break;
                    case MEDIA:
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.container, mq_fragment);
                        transaction.commit();

                        break;
                    case NORMAL:
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.container, nq_fragment);
                        transaction.commit();

                        break;
                }
            }
            previousType = currentQuestion.getTipo();
    }

    public void answer(boolean correcta){
        if(correcta) score += 3;
        else score -= 2;
        questionNumber++;

        scoreView.setText("Puntuación: "+score);
        questionView.setText("Pregunta: "+questionNumber);

        if(questionNumber < randomQuestions.size()){
            currentQuestion = randomQuestions.get(questionNumber);
            setupQuestion();
        }
        else changeActivity();
    }

    public void setupText(TextView view, RadioGroup radioGroup, RadioButton[] radiobuttons, Button check, ImageView iView, ImageButton [] imageButtons){

        view.setText(currentQuestion.getTexto());

        //Old parameters to reuse
        oldView = view;
        oldRadioGroup = radioGroup;
        oldRadiobuttons = radiobuttons;
        oldCheck = check;
        oldIView = iView;
        oldImagebuttons = imageButtons;

        if(currentQuestion.getTipo() == Type.MEDIA){
            int id = getResources().getIdentifier(currentQuestion.getRuta()[0], "drawable", getPackageName());
            iView.setImageResource(id);
            oldIView = iView;
        }


        if(currentQuestion.getTipo() == Type.IMAGE){
            int id;
            for(int i = 0; i < imageButtons.length; i++){
                id = getResources().getIdentifier(currentQuestion.getRuta()[i], "drawable", getPackageName());
                imageButtons[i].setImageResource(id);

                final int j = i+1;
                imageButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(currentQuestion.getCorrecta() == j){
                            answer(true);
                        }
                        else{
                            answer(false);
                        }
                    }
                });
            }
        }
        else {
            for(int i = 0; i < radiobuttons.length; i++){
                radiobuttons[i].setText(currentQuestion.getRespuesta()[i]);
            }

            answerNumber = 0;
            radioGroup.clearCheck();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                    switch (checkedId) {
                        case R.id.radioButton1:
                            answerNumber = 1;
                            break;
                        case R.id.radioButton2:
                            answerNumber = 2;
                            break;
                        case R.id.radioButton3:
                            answerNumber = 3;
                            break;
                        case R.id.radioButton4:
                            answerNumber = 4;
                            break;
                    }
                }
            });

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Si no se ha seleccionado ninguna respuesta no hacemos nada
                    if (answerNumber == 0)
                        return;
                    else if (answerNumber == currentQuestion.getCorrecta())
                        answer(true);
                    else
                        answer(false);

                }

            });
        }
    }

    private ArrayList<Pregunta> shuffle(ArrayList<Pregunta> list){

        Random rnd = new Random();
        for (int i = list.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Pregunta p = list.get(index);
            list.set(index, list.get(i));
            list.set(i, p);
        }
        return list;
    }

    private void changeActivity(){
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("POINTS", String.valueOf(score));
        startActivity(intent);
    }

    protected void resetAll(){
        //Set views
        scoreView = findViewById(R.id.score_title);
        questionView = findViewById(R.id.question);
        scoreView.setText("Puntuación: "+score);
        questionView.setText("Pregunta: "+questionNumber);

        //Reset answer, question and score numbers
        answerNumber = 0;
        questionNumber = 0;
        score = 0;

        //Random question list is selectect from pool (n size)
        randomQuestions = selectRandom(3);
        currentQuestion = randomQuestions.get(questionNumber);
        setupQuestion();
    }
}



