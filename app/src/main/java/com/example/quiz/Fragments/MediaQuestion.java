package com.example.quiz.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quiz.MainActivity;
import com.example.quiz.Pregunta;
import com.example.quiz.R;

public class MediaQuestion extends Fragment{

    protected int answerNumber = 0;
    private Pregunta pregunta;
    AppCompatActivity main_activity;
    protected final int[] BUTTONS = {R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.radioButton4};

    public MediaQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_question, container, false);
    }

    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState){

        //Pass views to main Activity
        TextView tview = getView().findViewById(R.id.textView);
        RadioGroup group = getView().findViewById(R.id.radioGroup);

        RadioButton button[] = new RadioButton[4];
        Button check = getView().findViewById(R.id.button);

        for(int i = 0; i < 4; i++) {
            button[i] = getView().findViewById(BUTTONS[i]);
        }

        ImageView imageView = getView().findViewById(R.id.imageView);
        ((MainActivity)getActivity()).setupText(tview, group, button, check, imageView, null);
    }

}
