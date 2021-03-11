package com.example.moneymanager.main.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;
import com.example.moneymanager.main.MainActivity;

import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class LanguageActivity  extends AppCompatActivity {

    private RelativeLayout btnBack, btnVi, btnEn;
    private ImageView checkVi, checkEn;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_language);


        btnVi = findViewById(R.id.btnVi);
        btnEn = findViewById(R.id.btnEn);

        btnBack = findViewById(R.id.btnBack);
        checkVi = findViewById(R.id.checkVi);
        checkEn = findViewById(R.id.checkEn);

        sp = getSharedPreferences("language", MODE_PRIVATE);
        editor = sp.edit();
        if(sp.getString("lang", "en").equals("vi")){
            checkVi.setVisibility(View.VISIBLE);
            checkEn.setVisibility(View.INVISIBLE);
        } else {
            checkEn.setVisibility(View.VISIBLE);
            checkVi.setVisibility(View.INVISIBLE);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnVi.setOnClickListener(e -> {
            if (checkVi.getVisibility() == View.VISIBLE) {
                Toasty.normal(this, getString(R.string.language_selected), Toasty.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.language_title))
                    .setMessage(getString(R.string.language_mes))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkVi.setVisibility(View.VISIBLE);
                            checkEn.setVisibility(View.INVISIBLE);
                            editor.putString("lang", "vi");
                            editor.commit();
                            loadLocal("vi");

                            Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(this.getString(R.string.no), null)
                    .show();

        });

        btnEn.setOnClickListener( e -> {
            if (checkEn.getVisibility() == View.VISIBLE) {
                Toasty.normal(this, getString(R.string.language_selected), Toasty.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.language_title))
                    .setMessage(getString(R.string.language_mes))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkEn.setVisibility(View.VISIBLE);
                            checkVi.setVisibility(View.INVISIBLE);
                            editor.putString("lang", "en");
                            editor.commit();
                            loadLocal("en");

                            Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(this.getString(R.string.no), null)
                    .show();

        });
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
