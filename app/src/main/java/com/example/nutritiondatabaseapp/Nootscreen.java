package com.example.nutritiondatabaseapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nootscreen} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class Nootscreen extends AppCompatActivity {

    private Button searchActBtn;
    private Button addDay, graphBtn;
    private Button prevDate, nextDate;
    private TextView dateTV, calTV, fatTV, carbTV, proteinTV, sugarTV, weightTV;
    Daily dailyUser = new Daily();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    GoogleSignInAccount account;

    private LocalDate viewDate = MainActivity.date;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        setTextviews("+");
        weightTV = findViewById(R.id.weightTV);
        weightTV.setText(Integer.toString(MainActivity.getUser().getWeight()));

        dateTV = findViewById(R.id.dateTV);
        dateTV.setText(MainActivity.modifiedDate(viewDate.toString()));

        calTV = findViewById(R.id.calTV);
        fatTV = findViewById(R.id.fatTV);
        carbTV = findViewById(R.id.carbsTV);
        proteinTV = findViewById(R.id.proteinTV);
        sugarTV = findViewById(R.id.sugarTV);

        addDay = findViewById(R.id.addDay);

        nextDate = findViewById(R.id.nextDateTV);
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    viewDate = viewDate.plusDays(1);
                    dateTV.setText(MainActivity.modifiedDate(viewDate.toString()));
//                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(viewDate.toString())).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            try {
//                                calTV.setText(dataSnapshot.child("calories").getValue().toString());
//                                fatTV.setText(dataSnapshot.child("fat").getValue().toString());
//                                carbTV.setText(dataSnapshot.child("carbs").getValue().toString());
//                                proteinTV.setText(dataSnapshot.child("protein").getValue().toString());
//                                sugarTV.setText(dataSnapshot.child("sugar").getValue().toString());
//                            }catch (Exception e) {
//                                viewDate = viewDate.minusDays(1);
//                                dateTV.setText(MainActivity.modifiedDate(viewDate.toString()));
//                                Toast.makeText(Nootscreen.this, "No future dates", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {}
//                    });
                    setTextviews("-");
                }catch (Exception e) {
                    Toast.makeText(Nootscreen.this, "No future dates", Toast.LENGTH_SHORT).show();
                }
            }
        });

        prevDate = findViewById(R.id.prevDateTV);
        prevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    viewDate = viewDate.minusDays(1);
                    dateTV.setText(MainActivity.modifiedDate(viewDate.toString()));
//                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(viewDate.toString())).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            try {
//                                calTV.setText(dataSnapshot.child("calories").getValue().toString());
//                                fatTV.setText(dataSnapshot.child("fat").getValue().toString());
//                                carbTV.setText(dataSnapshot.child("carbs").getValue().toString());
//                                proteinTV.setText(dataSnapshot.child("protein").getValue().toString());
//                                sugarTV.setText(dataSnapshot.child("sugar").getValue().toString());
//                            }catch (Exception e) {
//                                viewDate = viewDate.plusDays(1);
//                                dateTV.setText(MainActivity.modifiedDate(viewDate.toString()));
//                                Toast.makeText(Nootscreen.this, "No previous dates", Toast.LENGTH_SHORT).show();}
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {}
//                    });
                    setTextviews("+");
                }catch (Exception e) {
                    Toast.makeText(Nootscreen.this, "No previous dates", Toast.LENGTH_SHORT).show();
                }
            }
        });


        addDay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDate tempDate = MainActivity.date;
                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(tempDate.plusDays(1).toString())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (Double.parseDouble(dataSnapshot.child("calories").getValue().toString()) >= 0) {//populated
                                MainActivity.date = MainActivity.date.plusDays(1);
                                System.out.println("POPULATEDDDDD" + MainActivity.modifiedDate(MainActivity.date.toString()));
                                return;
                            }
                        }catch (Exception e) {
                            System.out.println("not populated");
                            MainActivity.date = MainActivity.date.plusDays(1);
                            Toast.makeText(Nootscreen.this, MainActivity.modifiedDate(MainActivity.date.toString()) + " already exists", Toast.LENGTH_SHORT).show();
                            mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("calories").setValue(0);
                            mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("carbs").setValue(0);
                            mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("fat").setValue(0);
                            mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("protein").setValue(0);
                            mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("sugar").setValue(0);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });







        //actual abstract normal code part
        graphBtn = findViewById(R.id.graphScrnBtn);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Nootscreen.this, GraphScreen.class));
            }
        });
    }

    private void setTextviews(final String plusMinus) {
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(viewDate.toString())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    calTV.setText(dataSnapshot.child("calories").getValue().toString());
                    fatTV.setText(dataSnapshot.child("fat").getValue().toString());
                    carbTV.setText(dataSnapshot.child("carbs").getValue().toString());
                    proteinTV.setText(dataSnapshot.child("protein").getValue().toString());
                    sugarTV.setText(dataSnapshot.child("sugar").getValue().toString());
                }catch (Exception e) {
                    if (plusMinus.equals("-")) {
                        viewDate = viewDate.minusDays(1);
                    }else {
                        viewDate = viewDate.plusDays(1);
                    }
                    dateTV.setText(MainActivity.modifiedDate(viewDate.toString()));
                    Toast.makeText(Nootscreen.this, "No future dates", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDateTV(String date) {
        dateTV.setText(MainActivity.modifiedDate(date));
    }

}