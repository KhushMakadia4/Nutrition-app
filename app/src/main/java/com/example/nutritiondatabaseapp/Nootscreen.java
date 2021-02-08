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
 * Use the {@link Nootscreen} factory method to
 * create an instance of this fragment.
 */
public class Nootscreen extends AppCompatActivity {

    private Button searchActBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nootscreen);


        searchActBtn = findViewById(R.id.searchActBtn);
        searchActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Nootscreen.this, SearchFragment.class));
            }
        });

    }

}