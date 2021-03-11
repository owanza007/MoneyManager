package com.example.moneymanager.main.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.SmartReminder;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;

public class SmartReminderActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private RecyclerView recycleSmart;
    private LinearLayout btnAddSmart;
    private ArrayList<SmartReminder> mListSmart;
    private SmartReminderAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_smart_reminder);

        btnBack = findViewById(R.id.btnBack);
        btnAddSmart = findViewById(R.id.btnAddSmart);


        recycleSmart = findViewById(R.id.recycleSmart);
        mListSmart = new ArrayList<>();
        mListSmart.add(new SmartReminder("20:00", true));
        mListSmart.add(new SmartReminder("20:00", true));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleSmart.setLayoutManager(layoutManager);
        recycleSmart.setNestedScrollingEnabled(false);
        recycleSmart.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new SmartReminderAdapter(this, mListSmart);
        recycleSmart.setAdapter(mAdapter);

        btnAddSmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String hour = String.valueOf(hourOfDay);
                                String minu = String.valueOf(minute);

                                if (hour.length() == 1) {
                                    hour = "0" + hour;
                                }
                                if (minu.length() == 1) {
                                    minu = "0" + minu;
                                }
                                mListSmart.add(new SmartReminder(hour + ":" + minu, true));
                                mAdapter.notifyDataSetChanged();
                            }
                        },
                        9, 0, true
                );
                tpd.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
