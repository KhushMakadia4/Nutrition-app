package com.example.nutritiondatabaseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphScreen} factory method to
 * create an instance of this fragment.
 */
public class GraphScreen extends AppCompatActivity {

    private Button calsGrBtn, fatGrBtn, protGrBtn, sugarGrBtn, carbsGrBtn;
    private Button backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backBtn = findViewById(R.id.backGraphBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GraphScreen.this, Nootscreen.class));
            }
        });

    }

}