package com.example.moneymanager.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;
import com.example.moneymanager.main.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import java.util.Locale;




public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    @VisibleForTesting
    public ProgressDialog mProgressDialog;
    private RelativeLayout btnGoogle;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static String uID;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sp = getSharedPreferences("language", MODE_PRIVATE);
        String lang = sp.getString("lang", "en");
        loadLocal(lang);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        btnGoogle = findViewById(R.id.btnGoogle);
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//        mAuth.signOut();
        // [END initialize_auth]
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    // [START on_start_check_user]

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uID = user.getUid();

                            mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    boolean check = false;
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        if (snapshot.getKey().equals(uID)) {
                                            check = true;
                                            break;
                                        }
                                    }
                                    if(!check){
                                        mDatabase.child("users").child(uID).setValue(true);
                                        setCategories();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading ...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }


    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    
    private void setCategories(){
        //--------------------------Expenses--------------------//
        mDatabase.child("categories").child(uID).child("expense").child("Food").setValue("food");
        mDatabase.child("categories").child(uID).child("expense").child("Bills").setValue("bills");
        mDatabase.child("categories").child(uID).child("expense").child("Transportation").setValue("transportation");
        mDatabase.child("categories").child(uID).child("expense").child("Home").setValue("home");
        mDatabase.child("categories").child(uID).child("expense").child("Car").setValue("car");
        mDatabase.child("categories").child(uID).child("expense").child("Entertainment").setValue("entertainment");
        mDatabase.child("categories").child(uID).child("expense").child("Shopping").setValue("shopping");
        mDatabase.child("categories").child(uID).child("expense").child("Clothing").setValue("clothing");
        mDatabase.child("categories").child(uID).child("expense").child("Insurance").setValue("insurance");
        mDatabase.child("categories").child(uID).child("expense").child("Tax").setValue("tax");
        mDatabase.child("categories").child(uID).child("expense").child("Telephone").setValue("telephone");
        mDatabase.child("categories").child(uID).child("expense").child("Cigarette").setValue("cigarette");
        mDatabase.child("categories").child(uID).child("expense").child("Health").setValue("health");
        mDatabase.child("categories").child(uID).child("expense").child("Sport").setValue("sport");
        mDatabase.child("categories").child(uID).child("expense").child("Baby").setValue("baby");
        mDatabase.child("categories").child(uID).child("expense").child("Pet").setValue("pet");
        mDatabase.child("categories").child(uID).child("expense").child("Beauty").setValue("beauty");
        mDatabase.child("categories").child(uID).child("expense").child("Hamburger").setValue("hamburger");
        mDatabase.child("categories").child(uID).child("expense").child("Vegetables").setValue("vegetables");
        mDatabase.child("categories").child(uID).child("expense").child("Snack").setValue("snacks");
        mDatabase.child("categories").child(uID).child("expense").child("Gift").setValue("gift");
        mDatabase.child("categories").child(uID).child("expense").child("Social").setValue("social");
        mDatabase.child("categories").child(uID).child("expense").child("Travel").setValue("travel");
        mDatabase.child("categories").child(uID).child("expense").child("Education").setValue("education");

        //--------------------------Income--------------------//
        mDatabase.child("categories").child(uID).child("income").child("Salary").setValue("salary");
        mDatabase.child("categories").child(uID).child("income").child("Awards").setValue("awards");
        mDatabase.child("categories").child(uID).child("income").child("Grants").setValue("grants");
        mDatabase.child("categories").child(uID).child("income").child("Sale").setValue("sale");
        mDatabase.child("categories").child(uID).child("income").child("Sale").setValue("sale");
        mDatabase.child("categories").child(uID).child("income").child("Rental").setValue("rental");
        mDatabase.child("categories").child(uID).child("income").child("Refunds").setValue("refunds");
        mDatabase.child("categories").child(uID).child("income").child("Coupons").setValue("coupons");
        mDatabase.child("categories").child(uID).child("income").child("Lottery").setValue("lottery");
        mDatabase.child("categories").child(uID).child("income").child("Dividends").setValue("dividends");
        mDatabase.child("categories").child(uID).child("income").child("Investments").setValue("investments");
    }

    public void loadLocal(String lang){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(lang)); // API 17+ only.
        }
        res.updateConfiguration(conf, dm);}
}
