package com.example.nutritiondatabaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private SignInButton signInButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private Button signOutButton;
    private int RC_SIGN_IN = 1;

    private Button enterBtn;
    private EditText weightBar;
    private EditText heightBar;
    private User user = new User();
    GoogleSignInAccount account;


    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.sign_in_button);
        signOutButton = (Button) findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        try {
            mGoogleSignInClient.signOut();
        }catch (Exception e) {}
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                signOutButton.setVisibility(View.INVISIBLE);
            }
        });

        weightBar = (EditText) findViewById(R.id.weightInput);
        heightBar = (EditText) findViewById(R.id.heightInput);
        enterBtn = (Button) findViewById(R.id.proceedBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setWeight((int) Integer.parseInt(String.valueOf(weightBar.getText())));
                user.setHeight((int) Integer.parseInt(String.valueOf(heightBar.getText())));
                mDatabase.child("users").child(account.getDisplayName()).setValue(user);

                //fragment work
                loadSearchFrag();
//                setContentView(R.layout.fragment_search);
            }
        });

    }
    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    final FirebaseUser copyUser = mAuth.getCurrentUser();

                    account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    if (account != null){
                        String personName = account.getDisplayName();
                        String personGivenName = account.getGivenName();
                        String personFamilyName = account.getFamilyName();
                        String personEmail = account.getEmail();
                        String personId = account.getId();
                        Uri personPhoto = account.getPhotoUrl();

                                            }


                    //loop through to find if user is duplicate
                    mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                            boolean hasUser = false;
                            while (items.hasNext()) {
                                DataSnapshot item = items.next();
                                System.out.println(item);
                                if (item.getKey().equals(account.getDisplayName())) {
                                    System.out.println("ALLAHU AKBAR");
                                    hasUser = true;

                                    Toast.makeText(MainActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                                    //copy the user data
                                    setUserValues(item);
                                    //go to new fragment
                                    loadSearchFrag();
                                    break;
                                }
                            }
                            if (!hasUser) {
                                updateUI(copyUser);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void loadSearchFrag() {
        startActivity(new Intent(MainActivity.this, SearchFragment.class));


//        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
//        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        SearchFragment searchFrag = new SearchFragment();
//        fragmentTransaction.replace(R.id.fragment_container, searchFrag).commit();

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, new SearchFragment());
//                .commit();
    }

    private void updateUI(FirebaseUser user){
        signOutButton.setVisibility(View.VISIBLE);

        enterBtn.setVisibility(View.VISIBLE);
        weightBar.setVisibility(View.VISIBLE);
        heightBar.setVisibility(View.VISIBLE);

    }


    private void setUserValues(DataSnapshot item) {
        System.out.println(item.child("weight").getValue().toString());
        System.out.println(item.child("height").getValue().toString());
        user.setWeight(Integer.parseInt(item.child("weight").getValue().toString()));
        user.setHeight(Integer.parseInt(item.child("height").getValue().toString()));

    }
}