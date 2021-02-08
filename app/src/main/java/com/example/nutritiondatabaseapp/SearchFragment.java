package com.example.nutritiondatabaseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends AppCompatActivity {

    private TextView searchTV;
    private Button searchBtn;
    private Button backBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);


//search bar auto complete text view

        searchTV = findViewById(R.id.searchTextView);
        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xy = searchTV.getText().toString();
            }
        });

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchFragment.this, Nootscreen.class));
            }
        });

//        searchTV.setKeyListener(new KeyListener() {
//            @Override
//            public int getInputType() {
//                return 0;
//            }
//
//            @Override
//            public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) { return false; }
//
//            @Override
//            public boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
//                System.out.println(keyEvent.getKeyCode() + " , " + keyEvent.getAction() + " , " + keyEvent.getNumber());
//                return false;
//            }
//
//            @Override
//            public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) { return false; }
//
//            @Override
//            public void clearMetaKeyState(View view, Editable editable, int i) {}
//        });

    }



}