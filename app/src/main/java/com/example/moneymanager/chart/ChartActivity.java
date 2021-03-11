package com.example.moneymanager.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.HistoryChild;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView list;
    private RecyclerView recyclerView;
    private DataAdapter mSendAdapter;
    private ArrayList<HistoryChild>mListData;

    private RelativeLayout btnBack;
    private LinearLayout btnMonthPicker;
    private TextView tvMonthPicker;

    private ImageView icon1, icon2, icon3, icon4, icon5;
    private TextView name1, name2, name3, name4, name5;
    private TextView percent1, percent2, percent3, percent4, percent5;

    private String CATEGORY = "";
    private String MONTH_YEAR = "";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.btnBack);
        btnMonthPicker = findViewById(R.id.btnMonthPicker);
        tvMonthPicker = findViewById(R.id.tvMonthPicker);
        setUpButton();

        pieChart = findViewById(R.id.pieChart);
        list = findViewById(R.id.list);
        recyclerView = findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        mListData = new ArrayList<>();


        CATEGORY = getIntent().getExtras().getString("category", "expense");
        MONTH_YEAR = getIntent().getExtras().getString("month_year", "11-2019");
        tvMonthPicker.setText(getIntent().getExtras().getString("month_picker", "Nov"));
        getData(CATEGORY, MONTH_YEAR);


        if ((CATEGORY.equals("expense"))) {
            list.setText(R.string.expenses_list);
        } else {
            list.setText(R.string.income_list);
        }
        mSendAdapter = new DataAdapter(this, mListData);
        recyclerView.setAdapter(mSendAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpButton(){

        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        icon4 = findViewById(R.id.icon4);
        icon5 = findViewById(R.id.icon5);

        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);
        name5 = findViewById(R.id.name5);

        percent1 = findViewById(R.id.percent1);
        percent2 = findViewById(R.id.percent2);
        percent3 = findViewById(R.id.percent3);
        percent4 = findViewById(R.id.percent4);
        percent5 = findViewById(R.id.percent5);


    }

    //------------------Lấy dữ liệu về---------------------//
    private void getData(final String category, String month) {
        final ArrayList<HistoryChild> mListChild = new ArrayList<>();
        mDatabase.child("histories").child(uID).child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListChild.clear();
                long sum = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (category.equals(snapshot.child("category").getValue())) {
                        sum += (long) snapshot.child("amount").getValue();
                        mListChild.add(snapshot.getValue(HistoryChild.class));
                    }
                }
                //Todo: Caculate percent
                for (HistoryChild child : mListChild) {
                    child.setPercent(1f * (child.getAmount() * 100) / sum);
                }

                //Todo: Sort and Analyze
                Collections.sort(mListChild, new Comparator<HistoryChild>() {
                    @Override
                    public int compare(HistoryChild o1, HistoryChild o2) {
                        return (int) (o2.getAmount() - o1.getAmount());
                    }
                });

                //Todo: Draw the PieChart
                System.out.println(mListChild.size());
                setUpPieChart(pieChart, mListChild);

                mSendAdapter = new DataAdapter(ChartActivity.this, mListChild);
                recyclerView.setAdapter(mSendAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //--------------------Vẽ biểu đồ từ dữ liệu trên-----------//
    private void setUpPieChart(final PieChart pieChart, ArrayList<HistoryChild>mListData){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);

        pieChart.setCenterText(getIntent().getExtras().getString("content", "Ngu"));

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setExtraOffsets(10f, 10f, 10f, 10f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(80f);
        pieChart.setTransparentCircleRadius(80f);//Set độ dày của đường tròn bên trong
        final ArrayList<PieEntry> yValues = new ArrayList<>();


        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        for (int i = 0; i < mListData.size(); i++){
            if(i == 5){
                break;
            }
            String name = mListData.get(i).getName();
            if(name.isEmpty()) name = mListData.get(i).getType();
            int per = (int) Math.round(mListData.get(i).getPercent());
            System.out.println(per);
            if(i == 0){
                name1.setText(name);
                icon1.setVisibility(View.VISIBLE);
                percent1.setText(String.format("%.2f", mListData.get(0).getPercent())+"%");
            } else if(i == 1){
                name2.setText(name);
                icon2.setVisibility(View.VISIBLE);
                percent2.setText(String.format("%.2f", mListData.get(1).getPercent())+"%");
            } else if(i == 2){
                name3.setText(name);
                icon3.setVisibility(View.VISIBLE);
                percent3.setText(String.format("%.2f", mListData.get(2).getPercent())+"%");
            } else if(i == 3){
                name4.setText(name);
                icon4.setVisibility(View.VISIBLE);
                percent4.setText(String.format("%.2f", mListData.get(3).getPercent())+"%");
            } else if(i == 4){
                name5.setText("Other");
                icon5.setVisibility(View.VISIBLE);
                percent5.setText(String.format("%.2f", mListData.get(4).getPercent())+"%");
                double percent = 100 - (Double.parseDouble(percent1.getText().toString().replace("%", ""))
                        +Double.parseDouble(percent2.getText().toString().replace("%", ""))
                        +Double.parseDouble(percent3.getText().toString().replace("%", ""))
                        +Double.parseDouble(percent4.getText().toString().replace("%", "")));
                percent5.setText(String.format("%.2f", percent)+"%");
                per = (int) Math.round(percent);
            }
            yValues.add(new PieEntry(1f*per, name));
        }


        //Todo: Chú thích
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(3f);
        final int[] MY_COLORS = {
                Color.parseColor("#FFEB3B"),
                Color.parseColor("#48c0e3"),
                Color.parseColor("#53c5ab"),
                Color.parseColor("#f7756d"),
                Color.parseColor("#feb948")
        };

        ArrayList<Integer> colors = new ArrayList<>();
        for(int c: MY_COLORS) colors.add(c);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.RED);
        data.setDrawValues(false);

        pieChart.setData(data);
    }
}
