package com.example.nutritiondatabaseapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nootscreen} factory method to
 * create an instance of this fragment.
 */
public class Nootscreen extends AppCompatActivity {

    private Button searchActBtn;
    private Button addDayBtn;
    private TextView dateTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nootscreen);
        dateTV = findViewById(R.id.dateTV);

        searchActBtn = findViewById(R.id.searchActBtn);
        searchActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Nootscreen.this, SearchFragment.class));
            }
        });


        addDayBtn = findViewById(R.id.addDayBtn);
        addDayBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                MainActivity.date = MainActivity.date.plusDays(1);
                setDateTV(MainActivity.date.toString());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDateTV(String date) {
        dateTV.setText(MainActivity.modifiedDate(date));
    }

}