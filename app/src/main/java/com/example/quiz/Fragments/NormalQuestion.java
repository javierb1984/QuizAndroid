package com.example.quiz.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quiz.MainActivity;
import com.example.quiz.R;


public class NormalQuestion extends Fragment {

    protected final int[] BUTTONS = {R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.radioButton4};
    public NormalQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_normal_question, container, false);
    }

    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState){

        //Pass views to main Activity
        TextView tview = getView().findViewById(R.id.textView);
        RadioGroup group = getView().findViewById(R.id.radioGroup);

        RadioButton button[] = new RadioButton[4];
        Button check = getView().findViewById(R.id.button);

        for(int i = 0; i < 4; i++){
            button[i] = getView().findViewById(BUTTONS[i]);
        }

        ((MainActivity)getActivity()).setupText(tview, group, button, check, null, null, null);
    }

}
