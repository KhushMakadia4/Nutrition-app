package com.example.nutritiondatabaseapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends AppCompatActivity {

    private TextView searchTV, calories, fat, cholesterol, carb, protein, sugar;
    private Button searchBtn;
    private Button backBtn;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private Button btn2;
    GoogleSignInAccount account;
    private TextView multiplier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);


//search bar auto complete text view
        multiplier = findViewById(R.id.multiplier);
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
        carb = findViewById(R.id.carbohydrate);
        btn2 = findViewById(R.id.button2);

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
                String url1 = "https://api.nutritionix.com/v1_1/search/" + food + "?results=0:20&fields=nf_calories,nf_total_fat,nf_cholesterol,nf_protein,nf_sugars,nf_total_carbohydrate&appId=d7be3755&appKey=181927e63a4766687741bbe69f3c6ed6";//api URL MUST HAVE HTTPS:// THAT IS NOT OPTIONAL

                new AsyncHttpClient().get(url1, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        String str = new String(responseBody);//this will show all the raw data which can be substringed for individual data
                        final double cals = Double.parseDouble(str.substring(str.indexOf("nf_calories") + 13, str.indexOf("nf_total_fat") - 2));
                        final double fats = Double.parseDouble(str.substring(str.indexOf("nf_total_fat") + 14, str.indexOf("nf_cholesterol") - 2));
                        final double carbs = Double.parseDouble(str.substring(str.indexOf("nf_total_carbohydrate") + 24, str.indexOf("nf_sugar") - 2));
                        final double sugars = Double.parseDouble(str.substring(str.indexOf("nf_sugar") + 11, str.indexOf("nf_protein") - 2));
                        final double proteins = Double.parseDouble(str.substring(str.indexOf("nf_protein") + 12, str.indexOf("nf_serving") - 2));

                        calories.setText("Calories: " + cals);
                        fat.setText("Fat: " + fats);
                        carb.setText("Carbohydrates: " + carbs);
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
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()) {
                            DataSnapshot item = items.next();
                            System.out.println(item);
                            if (item.getKey().equals(account.getDisplayName())) {
                                //copy the user data
                                setUserValues(item);
                                //go to new fragment
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUserValues(DataSnapshot item) {

        double cals2 = Double.parseDouble(item.child(MainActivity.modifiedDate(MainActivity.date.toString())).child("calories").getValue().toString());
        double carbs2 = Double.parseDouble(item.child(MainActivity.modifiedDate(MainActivity.date.toString())).child("carbs").getValue().toString());
        double fats2 = Double.parseDouble(item.child(MainActivity.modifiedDate(MainActivity.date.toString())).child("fat").getValue().toString());
        double protein2 = Double.parseDouble(item.child(MainActivity.modifiedDate(MainActivity.date.toString())).child("protein").getValue().toString());
        double sugars2 = Double.parseDouble(item.child(MainActivity.modifiedDate(MainActivity.date.toString())).child("calories").getValue().toString());
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("calories").setValue(cals2 + Double.parseDouble(multiplier.getText().toString())*Double.parseDouble(calories.getText().toString().substring(calories.getText().toString().indexOf(":") + 1)));
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("carbs").setValue(carbs2 + Double.parseDouble(multiplier.getText().toString())*Double.parseDouble(carb.getText().toString().substring(carb.getText().toString().indexOf(":") + 1)));
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("fat").setValue(fats2 + Double.parseDouble(multiplier.getText().toString())*Double.parseDouble(fat.getText().toString().substring(fat.getText().toString().indexOf(":") + 1)));
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("protein").setValue(protein2 + Double.parseDouble(multiplier.getText().toString())*Double.parseDouble(protein.getText().toString().substring(protein.getText().toString().indexOf(":") + 1)));
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("sugar").setValue(sugars2 + Double.parseDouble(multiplier.getText().toString())*Double.parseDouble(sugar.getText().toString().substring(sugar.getText().toString().indexOf(":") + 1)));

        //        double carbs2 = Double.parseDouble(item.child("carbs").getValue().toString());
//        double fats2 = Double.parseDouble(item.child("fat").getValue().toString());
//        double proteins2 = Double.parseDouble(item.child("protein").getValue().toString());
//        double sugars2 = Double.parseDouble(item.child("sugar").getValue().toString());
//        mDatabase.child("users").child(account.getDisplayName()).child("calories").setValue(cals+Double.parseDouble(calories.getText().toString().substring(calories.getText().toString().indexOf(":")+1)));
//        mDatabase.child("users").child(account.getDisplayName()).child("carbs").setValue(carbs+Double.parseDouble(carb.getText().toString().substring(carb.getText().toString().indexOf(":")+1)));
//        mDatabase.child("users").child(account.getDisplayName()).child("fat").setValue(fats+Double.parseDouble(fat.getText().toString().substring(fat.getText().toString().indexOf(":")+1)));
//        mDatabase.child("users").child(account.getDisplayName()).child("protein").setValue(proteins+Double.parseDouble(protein.getText().toString().substring(protein.getText().toString().indexOf(":")+1)));
//        mDatabase.child("users").child(account.getDisplayName()).child("sugar").setValue(sugars+Double.parseDouble(sugar.getText().toString().substring(sugar.getText().toString().indexOf(":")+1)));


    }


}