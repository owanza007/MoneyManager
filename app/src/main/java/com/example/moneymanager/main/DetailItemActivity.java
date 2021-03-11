package com.example.moneymanager.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;
import com.example.moneymanager.additem.ShowItemActivity;
import com.example.moneymanager.models.App;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailItemActivity extends AppCompatActivity {

    private RelativeLayout btnBack, btnDel;
    private FloatingActionButton btnEdit;

    private LinearLayout bgIcon;
    private ImageView icon;
    private TextView nameDetail, tvCategory, tvTimes, tvMoney, tvMemo;

    private App app;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;
    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        app = new App();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        sp = getSharedPreferences("language", MODE_PRIVATE);

        btnBack = findViewById(R.id.btnBack);
        btnDel = findViewById(R.id.btnDel);
        btnEdit = findViewById(R.id.btnEdit);

        bgIcon = findViewById(R.id.bgIcon);
        icon = findViewById(R.id.icon);
        nameDetail = findViewById(R.id.nameDetail);
        tvCategory = findViewById(R.id.category);
        tvMoney = findViewById(R.id.money);
        tvTimes = findViewById(R.id.times);
        tvMemo = findViewById(R.id.memo);


        String timestamp = getIntent().getExtras().getString("timestamp");
        String category = getIntent().getExtras().getString("category");
        String type = getIntent().getExtras().getString("type");
        String name = getIntent().getExtras().getString("name");
        long amount = getIntent().getExtras().getLong("amount");

        icon.setImageResource(app.getICons(type).first);
        icon.setColorFilter(Color.parseColor("#ffffff"));
        bgIcon.setBackgroundResource(app.getICons(type).second);

        if(sp.getString("lang", "en").equals("vi")){
            nameDetail.setText(app.convertVI(name));
            tvMemo.setText(app.convertVI(type.substring(0,1).toUpperCase()+ type.substring(1)));
        } else {
            nameDetail.setText(name);
            tvMemo.setText(type.substring(0, 1).toUpperCase() + type.substring(1));
        }

//        nameDetail.setText(name);

        if(category.equals("expense")){
            tvCategory.setText(getString(R.string.expenses));
        } else
            tvCategory.setText(getString(R.string.income));

//        tvCategory.setText(category);
        tvMoney.setText(String.valueOf(amount));
        tvTimes.setText(convertTimestampToDate(Long.parseLong(timestamp), "dd.MM.yyyy EEE"));
//        tvMemo.setText(type);

        btnBack.setOnClickListener(v -> onBackPressed());

        btnEdit.setOnClickListener( v -> {
            Intent intent = new Intent(DetailItemActivity.this, ShowItemActivity.class);
            intent.putExtra("timestamp", timestamp);
            intent.putExtra("name", name);
            intent.putExtra("type", type);
            intent.putExtra("category", category);
            intent.putExtra("amount", amount);
            startActivity(intent);
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailItemActivity.this)
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_mes))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String month_year = convertTimestampToDate(Long.parseLong(timestamp), "MM-yyyy");
                                mDatabase.child("histories").child(uID).child(month_year).child(timestamp).setValue(null);
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        });
    }
    private String convertTimestampToDate(long timestamp, String format){
        Date date = new java.util.Date(timestamp);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

}
