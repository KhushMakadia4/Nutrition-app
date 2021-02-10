package com.example.nutritiondatabaseapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphScreen} factory method to
 * create an instance of this fragment.
 */
public class GraphScreen extends AppCompatActivity {

    private Button calsGrBtn, fatGrBtn, protGrBtn, sugarGrBtn, carbsGrBtn;
    private Button backBtn;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    GoogleSignInAccount account;

    private PointsGraphSeries<DataPoint> xySeries;
    private ArrayList<XYValue> xyValueArray;
    private GraphView graph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_graph_screen);
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        collectData("fat");


        backBtn = findViewById(R.id.backGraphBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GraphScreen.this, Nootscreen.class));
            }
        });


        graph = (GraphView) findViewById(R.id.graph);
        xyValueArray = new ArrayList<>();

        init();
    }

    private void collectData(final String childName) {
        final ArrayList<String> yVal = new ArrayList<>();
        System.out.println("IM HERE HELOOOOOOOO");
        mDatabase.child("users").child(account.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("SDLKFJLKJSLDKJFLSKDJFLSKJDFlk");
                System.out.println(dataSnapshot.child(MainActivity.modifiedDate(MainActivity.date.toString())).child(childName).getValue());
                Iterator<DataSnapshot> dates = dataSnapshot.getChildren().iterator();
                while (dates.hasNext()) {
                    System.out.println(dates.next().hasChild(childName) + " , " + dates.next().hasChildren());
                    yVal.add(dates.next().child(childName).getValue().toString());
                }
                System.out.println(yVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        xySeries = new PointsGraphSeries<>();

        calsGrBtn = findViewById(R.id.calGraphBtn);
        calsGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fatGrBtn = findViewById(R.id.fatGraphBtn);
        fatGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        protGrBtn = findViewById(R.id.proteinGraphBtn);
        protGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sugarGrBtn = findViewById(R.id.sugarGraphBtn);
        sugarGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        carbsGrBtn = findViewById(R.id.carbsGraphBtn);
        carbsGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}