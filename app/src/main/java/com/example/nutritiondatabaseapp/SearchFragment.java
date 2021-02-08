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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends AppCompatActivity {

    private TextView searchTV,calories,fat,cholesterol,carbs,protein,sugar;
    private Button searchBtn;
    private Button backBtn;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();




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
        calories = findViewById(R.id.cals);
        fat = findViewById(R.id.fat);
        cholesterol = findViewById(R.id.cholesterol);

        sugar = findViewById(R.id.sugar);
        protein = findViewById(R.id.protein);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchFragment.this, Nootscreen.class));
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = searchTV.getText().toString();
                String url1 = "https://api.nutritionix.com/v1_1/search/" + food + "?results=0:20&fields=nf_calories,nf_total_fat,nf_cholesterol,nf_protein,nf_sugars,nf_total_carbohydrates&appId=d7be3755&appKey=181927e63a4766687741bbe69f3c6ed6";//api URL MUST HAVE HTTPS:// THAT IS NOT OPTIONAL

                new AsyncHttpClient().get(url1, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        String str = new String(responseBody);//this will show all the raw data which can be substringed for individual data
                        final double cals  = Double.parseDouble(str.substring(str.indexOf("nf_calories")+13,str.indexOf("nf_total_fat")-2));
                        final double fats  = Double.parseDouble(str.substring(str.indexOf("nf_total_fat")+14,str.indexOf("nf_cholesterol")-2));
                        final double cholesterols  = Double.parseDouble(str.substring(str.indexOf("nf_cholesterol")+17,str.indexOf("nf_sugars")-2));
                        final double sugars  = Double.parseDouble(str.substring(str.indexOf("nf_sugar")+11,str.indexOf("nf_protein")-2));
                        final double proteins  = Double.parseDouble(str.substring(str.indexOf("nf_protein")+12,str.indexOf("nf_serving")-2));

                        calories.setText("Calories: " + cals);
                        fat.setText("Fat: " + fats);
                        cholesterol.setText("Cholesterol: " + cholesterols);
                        sugar.setText("Sugar: " + sugars);
                        protein.setText("Protein:  " + proteins);



                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        error.printStackTrace();//wil print error message
                    }

                });
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