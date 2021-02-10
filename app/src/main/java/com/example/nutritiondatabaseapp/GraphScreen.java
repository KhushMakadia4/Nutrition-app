package com.example.nutritiondatabaseapp;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphScreen} factory method to
 * create an instance of this fragment.
 */
public class GraphScreen extends AppCompatActivity {
//    private PointsGraphSeries<DataPoint> xySeries;
//    private GraphView graph;

    private Button calsGrBtn, fatGrBtn, protGrBtn, sugarGrBtn, carbsGrBtn;
    private Button backBtn;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    GoogleSignInAccount account;

    private ArrayList<XYValue> xyValueArray;
    private LineChartView lineChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_graph_screen);
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        collectData("calories");


        backBtn = findViewById(R.id.backGraphBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GraphScreen.this, Nootscreen.class));
            }
        });

//
//        graph = (GraphView) findViewById(R.id.graph);
        lineChart = findViewById(R.id.lineChart);
//        xyValueArray = new ArrayList<>();


        calsGrBtn = findViewById(R.id.calGraphBtn);
        calsGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectData("calories");
            }
        });

        fatGrBtn = findViewById(R.id.fatGraphBtn);
        fatGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectData("fat");
            }
        });

        protGrBtn = findViewById(R.id.proteinGraphBtn);
        protGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectData("protein");
            }
        });

        sugarGrBtn = findViewById(R.id.sugarGraphBtn);
        sugarGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectData("sugar");
            }
        });

        carbsGrBtn = findViewById(R.id.carbsGraphBtn);
        carbsGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectData("carbs");
            }
        });
    }

    private void collectData(final String childName) {
        xyValueArray = new ArrayList<>();

        mDatabase.child("users").child(account.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> datesTime = dataSnapshot.getChildren().iterator();
                while (datesTime.hasNext()) {
                    DataSnapshot date = datesTime.next();
                    System.out.println(date.getKey());
                    System.out.println(date.hasChild(childName));
                    System.out.println(date.hasChildren());
                    if (date.hasChildren()) {
                        String xVal = reformatDate(date.getKey());
                        double yVal = Double.parseDouble(date.child(childName).getValue().toString());
                        System.out.println(xVal + " , " + childName + ": " + yVal);
                        xyValueArray.add(new XYValue(xVal, yVal));
                    }
                }
                graphPts(childName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private String reformatDate(String date) {
        String month = date.substring(0,2);
        String day = date.substring(3,5);
        if (month.substring(0,1).equals("0")) {
            month = date.substring(1,2);
        }
        if (day.substring(0,1).equals("0")) {
            day = date.substring(4,5);
        }
        return month+"/"+day;
    }

    private void graphPts(String yName) {
        //split xyarray
        String[] xDates = new String[xyValueArray.size()];
        double[] yDates = new double[xyValueArray.size()];

        for (int i = 0;i<xyValueArray.size();i++) {
//            xDates.add(pt.getX());
//            yDates.add(pt.getY());
            xDates[i] = xyValueArray.get(i).getX();
            yDates[i] = xyValueArray.get(i).getY();
        }
        //set up axises
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        for (int i = 0;i<xDates.length;i++) {
            axisValues.add(i, new AxisValue(i).setLabel(xDates[i]));
        }
        for (int i = 0;i<yDates.length;i++) {
            yAxisValues.add(new PointValue(i, (float)(yDates[i])));
        }
        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName(yName);
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChart.setLineChartData(data);
//        Viewport viewport = new Viewport(lineChart.getMaximumViewport());
//        viewport.top = 110;
//        lineChart.setMaximumViewport(viewport);
//        lineChart.setCurrentViewport(viewport);
    }

}