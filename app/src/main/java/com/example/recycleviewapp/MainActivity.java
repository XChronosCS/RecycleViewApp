package com.example.recycleviewapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Vocab> words;
    String wordName;
    String wordDef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        words = new ArrayList<>();
        setContentView(R.layout.activity_main);
        RecyclerView vocabDisplay = (RecyclerView) findViewById(R.id.vocab_display);
        ItemAdapter adapter = new ItemAdapter(words);
        vocabDisplay.setAdapter(adapter);
        vocabDisplay.setLayoutManager(new LinearLayoutManager(this));
        vocabDisplay.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        Button enter_button = (Button) findViewById(R.id.enter);
        Button two_enter_button = (Button) findViewById(R.id.enter2);
        Button add_button = (Button) findViewById(R.id.add_vocab_word);
        EditText user_input = (EditText) findViewById(R.id.user_input);
        TextView user_instruction = (TextView) findViewById(R.id.user_instruction);
        user_input.setVisibility(View.GONE);
        user_instruction.setVisibility(View.GONE);
        enter_button.setVisibility(View.GONE);
        two_enter_button.setVisibility(View.GONE);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocabDisplay.setVisibility(View.GONE);
                add_button.setVisibility(View.GONE);
                user_input.setVisibility(View.VISIBLE);
                enter_button.setVisibility(View.VISIBLE);
                user_instruction.setVisibility(View.VISIBLE);
                user_instruction.setText("Please Enter the Vocab Word");
            }
        });
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enter_button.getVisibility() == View.VISIBLE) {
                    wordName = user_input.getText().toString();
                    user_input.getText().clear();
                    enter_button.setVisibility(View.GONE);
                    two_enter_button.setVisibility(View.VISIBLE);
                    user_instruction.setText("Please Enter the Definition");
                }
            }
        });
        two_enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (two_enter_button.getVisibility() == View.VISIBLE) {
                    wordDef = user_input.getText().toString();
                    user_input.getText().clear();
                    user_input.setVisibility(View.GONE);
                    user_instruction.setVisibility(View.GONE);
                    two_enter_button.setVisibility(View.GONE);
                    vocabDisplay.setVisibility(View.VISIBLE);
                    add_button.setVisibility(View.VISIBLE);
                    words.add(new Vocab(wordName, wordDef));
                    adapter.notifyItemChanged(0);
                }
            }
        });
    }


}