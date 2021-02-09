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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nootscreen} factory method to
 * create an instance of this fragment.
 */
public class Nootscreen extends AppCompatActivity {

    private Button searchActBtn;
    private Button addDay;
    private TextView dateTV;
    Daily dailyUser = new Daily();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    GoogleSignInAccount account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nootscreen);
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        searchActBtn = findViewById(R.id.searchActBtn);
        searchActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Nootscreen.this, SearchFragment.class));
            }
        });
        dateTV = findViewById(R.id.dateTV);
        addDay = findViewById(R.id.addDay);
        addDay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                MainActivity.date = MainActivity.date.plusDays(1);
                setDateTV(MainActivity.date.toString());
                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDateTV(String date) {
        dateTV.setText(MainActivity.modifiedDate(date));
    }

}