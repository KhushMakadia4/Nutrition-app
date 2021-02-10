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
public class Nootscreen extends AppCompatActivity {

    private Button searchActBtn;
    private Button addDay;
    private TextView dateTV, calTV, fatTV, carbTV, proteinTV, sugarTV;
    Daily dailyUser = new Daily();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    GoogleSignInAccount account;

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
        calTV = findViewById(R.id.calTV);
        fatTV = findViewById(R.id.fatTV);
        carbTV = findViewById(R.id.carbsTV);
        proteinTV = findViewById(R.id.proteinTV);
        sugarTV = findViewById(R.id.sugarTV);
        dateTV = findViewById(R.id.dateTV);
        addDay = findViewById(R.id.addDay);

        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("calories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    dailyUser.setCalories(Double.parseDouble(dataSnapshot.getValue().toString()));
                    System.out.println("dailyCals: " + dailyUser.getCalories());
                } catch (NullPointerException e){
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("carbs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    dailyUser.setCarbs(Double.parseDouble(dataSnapshot.getValue().toString()));
                } catch (NullPointerException e){
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                }                        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("fat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    dailyUser.setFat(Double.parseDouble(dataSnapshot.getValue().toString()));
                } catch (NullPointerException e){
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("protein").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    dailyUser.setProtein(Double.parseDouble(dataSnapshot.getValue().toString()));
                } catch (NullPointerException e){
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("sugar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    dailyUser.setSugar(Double.parseDouble(dataSnapshot.getValue().toString()));
                    calTV.setText(Double.toString(dailyUser.getCalories()));
                    carbTV.setText(Double.toString(dailyUser.getCarbs()));
                    fatTV.setText(Double.toString(dailyUser.getFat()));
                    proteinTV.setText(Double.toString(dailyUser.getProtein()));
                    sugarTV.setText(Double.toString(dailyUser.getSugar()));
                } catch (NullPointerException e){
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                }                        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        addDay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                MainActivity.date = MainActivity.date.plusDays(1);
                setDateTV(MainActivity.date.toString());
                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("calories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                dailyUser.setCalories(Double.parseDouble(dataSnapshot.getValue().toString()));
                                System.out.println("dailyCals: " + dailyUser.getCalories());
                            } catch (NullPointerException e){
                                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("carbs").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                dailyUser.setCarbs(Double.parseDouble(dataSnapshot.getValue().toString()));
                            } catch (NullPointerException e){
                                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                            }                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("fat").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                dailyUser.setFat(Double.parseDouble(dataSnapshot.getValue().toString()));
                            } catch (NullPointerException e){
                                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("protein").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                dailyUser.setProtein(Double.parseDouble(dataSnapshot.getValue().toString()));
                            } catch (NullPointerException e){
                                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).child("sugar").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                dailyUser.setSugar(Double.parseDouble(dataSnapshot.getValue().toString()));
                                calTV.setText(Double.toString(dailyUser.getCalories()));
                                carbTV.setText(Double.toString(dailyUser.getCarbs()));
                                fatTV.setText(Double.toString(dailyUser.getFat()));
                                proteinTV.setText(Double.toString(dailyUser.getProtein()));
                                sugarTV.setText(Double.toString(dailyUser.getSugar()));
                            } catch (NullPointerException e){
                                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
                            }                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
//                mDatabase.child("users").child(account.getDisplayName()).child(MainActivity.modifiedDate(MainActivity.date.toString())).setValue(dailyUser);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDateTV(String date) {
        dateTV.setText(MainActivity.modifiedDate(date));
    }

}