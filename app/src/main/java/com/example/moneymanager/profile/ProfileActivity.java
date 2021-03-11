package com.example.moneymanager.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moneymanager.R;
import com.example.moneymanager.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;

    private CircleImageView avatar;
    private TextView email, name, lastSignIn;
    private RelativeLayout btnBack, btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        avatar = findViewById(R.id.avatar);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        lastSignIn = findViewById(R.id.lastSignIn);
        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);


        getData();
        logout();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        Glide.with(avatar).load(mAuth.getCurrentUser().getPhotoUrl().toString()).into(avatar);
        email.setText(mAuth.getCurrentUser().getEmail());
        name.setText(mAuth.getCurrentUser().getDisplayName());
        lastSignIn.setText(convertTimes(mAuth.getCurrentUser().getMetadata().getLastSignInTimestamp()));

    }

    private void logout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle(getString(R.string.logout_title))
                        .setMessage(getString(R.string.logout_mes))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
            }
        });
    }
    private String convertTimes (long timestamp){
        Date date = new java.util.Date(timestamp);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm:ss dd.MM.yyyy EEE");
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);

        return formattedDate;
    }
}
