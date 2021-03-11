package com.example.moneymanager.main.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;
import com.example.moneymanager.models.SmartReminder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    private RelativeLayout btnBack;
    private LinearLayout btnSmart, btnLang;
    private TextView tvRemind, tvLang;
    private SharedPreferences sp;

    private ArrayList<SmartReminder> mListSmart;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(e -> onBackPressed());

        btnSmart = findViewById(R.id.btnSmart);
        btnLang = findViewById(R.id.btnLang);

        tvRemind = findViewById(R.id.tvRemind);
        tvLang = findViewById(R.id.tvLang);


        mListSmart = new ArrayList<>();
        mDatabase.child("reminders").child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListSmart.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnSmart.setOnClickListener(e -> {
            Intent intent = new Intent(this, SmartReminderActivity.class);
            startActivity(intent);
        });
        btnLang.setOnClickListener(e -> {
            Intent intent = new Intent(this, LanguageActivity.class);
            startActivity(intent);
        });

        sp = getSharedPreferences("language", MODE_PRIVATE);
        if(sp.getString("lang", "en").equals("vi")){
            tvLang.setText(getString(R.string.vietnam));
        } else {
            tvLang.setText(getString(R.string.english));
        }
    }
}
