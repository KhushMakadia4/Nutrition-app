package com.example.nutritiondatabaseapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends AppCompatActivity {


    private AutoCompleteTextView searchTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);


        searchTV = findViewById(R.id.searchTextView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        searchTV.setAdapter(adapter);

            searchTV.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    System.out.println(i + " , " + (int)(keyEvent.getDisplayLabel()) + " , " + keyEvent.getAction() + " , " + keyEvent.getNumber());

                    if ((int)(keyEvent.getDisplayLabel())>=65 && (int)(keyEvent.getDisplayLabel())<=90) {
                        System.out.println("ALLAHU AKBAR");
                        ArrayList<String> foods = new ArrayList<>();
                        foods.add("Allahu");
                        foods.add("Akbar");
                        //call api here and populate foods
                        adapter.addAll(foods);
                        searchTV.setAdapter(adapter);
                    } else {
                        try {
                            searchTV.setText(searchTV.getText().toString().substring(0, searchTV.getText().length()));
                        }catch (Exception e) {}
                    }
                    return false;
                }
            });

//search bar auto complete text view



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