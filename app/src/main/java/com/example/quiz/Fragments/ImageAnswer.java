package com.example.quiz.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quiz.MainActivity;
import com.example.quiz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageAnswer extends Fragment {

    private final int[] IMAGE_BUTTONS = {R.id.imageButton1,R.id.imageButton2,R.id.imageButton3,R.id.imageButton4};

    public ImageAnswer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_answer, container, false);
    }

    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState) {

        //Pass views to main Activity
        TextView tview = getView().findViewById(R.id.textView);
        RadioGroup group = getView().findViewById(R.id.radioGroup);

        ImageButton button[] = new ImageButton[4];
        for (int i = 0; i < 4; i++) {
            button[i] = getView().findViewById(IMAGE_BUTTONS[i]);
        }
            ((MainActivity) getActivity()).setupText(tview, null, null, null, null, null, button);

    }

}
