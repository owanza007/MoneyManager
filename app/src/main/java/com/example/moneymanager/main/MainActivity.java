package com.example.moneymanager.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moneymanager.R;
import com.example.moneymanager.additem.ShowItemActivity;
import com.example.moneymanager.chart.ChartActivity;
import com.example.moneymanager.main.setting.SettingActivity;
import com.example.moneymanager.models.History;
import com.example.moneymanager.models.HistoryChild;
import com.example.moneymanager.profile.ProfileActivity;
import com.example.moneymanager.setting.CategorySettingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private TextView name;
    private LinearLayout btnIncome, btnExpense;
    private FloatingActionButton btnAdd;
    private RelativeLayout btnMenu, btnReload;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView avatar;
    private TextView email;

    private LinearLayout btnMonthPicker;
    private TextView tvMonthPicker;

    private TextView month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12;
    private ImageButton btnBefore, btnAfter;
    private TextView tvYear;
    private RecyclerView recyclerView;
    private ArrayList<History>mListHistory;

    private HistoryAdapter mAdapter;
    private HistoryChildAdapter mAdapterChild;

    private ScrollView scrollView;
    private static String MONTH_YEAR = "";
    private static String YEAR = "2019";

    private LinearLayout monthPicker;
    private LinearLayout emptyLinear;

    private TextView sumIncome;
    private TextView sumExpense;
    private TextView balance;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        MONTH_YEAR = convertTimestampToDate(System.currentTimeMillis(), "MM-yyyy");

        monthPicker = findViewById(R.id.monthPicker);
        btnMonthPicker = findViewById(R.id.btnMonthPicker);
        tvMonthPicker = findViewById(R.id.tvMonthPicker);
        tvMonthPicker.setText(convertMonthEntoVi(convertTimestampToDate(System.currentTimeMillis(), "MMM")));
        emptyLinear = findViewById(R.id.emptyLinear);
        emptyLinear.setVisibility(View.VISIBLE);
        drawer = findViewById(R.id.drawer);

        sumIncome = findViewById(R.id.sumIncome);
        sumExpense = findViewById(R.id.sumExpense);
        balance = findViewById(R.id.balance);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        name = navigationView.getHeaderView(0).findViewById(R.id.name);
        name.setText(mAuth.getCurrentUser().getDisplayName());
        email = navigationView.getHeaderView(0).findViewById(R.id.email);
        email.setText(mAuth.getCurrentUser().getEmail());
        avatar = navigationView.getHeaderView(0).findViewById(R.id.avatar);
        Glide.with(avatar).load(mAuth.getCurrentUser().getPhotoUrl().toString()).into(avatar);

        avatar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        scrollView = findViewById(R.id.scrollView);

        recyclerView = findViewById(R.id.recycleView);
        mListHistory = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getData(MONTH_YEAR);
        initMonthPicker();

        btnMonthPicker.setOnClickListener(v -> {
            if (monthPicker.getVisibility() == View.VISIBLE)
                monthPicker.setVisibility(View.INVISIBLE);
            else
                monthPicker.setVisibility(View.VISIBLE);
        });

        btnAdd= findViewById(R.id.btnAdd);
        btnExpense = findViewById(R.id.btnExpense);
        btnIncome = findViewById(R.id.btnIncome);
        btnMenu = findViewById(R.id.btnMenu);
        btnReload = findViewById(R.id.btnReload);


        btnMenu.setOnClickListener(v -> drawer.openDrawer(Gravity.LEFT));
        btnExpense.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ChartActivity.class);
            intent.putExtra("category", "expense");
            intent.putExtra("month_year", MONTH_YEAR);
            intent.putExtra("month_picker", tvMonthPicker.getText().toString().trim());
            intent.putExtra("content", getString(R.string.expenses)+"\n"+sumExpense.getText());
            startActivity(intent);
        });
        btnIncome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChartActivity.class);
            intent.putExtra("category", "income");
            intent.putExtra("month_year", MONTH_YEAR);
            intent.putExtra("month_picker", tvMonthPicker.getText().toString().trim());
            intent.putExtra("content", getString(R.string.income)+ "\n"+sumIncome.getText());
            startActivity(intent);
        });
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowItemActivity.class);
            startActivity(intent);
        });
        btnReload.setOnClickListener(v -> {
            Toasty.normal(MainActivity.this, getString(R.string.reload_success), Toasty.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            Intent intent;
            switch (id){
                case R.id.chart:
                    intent = new Intent(MainActivity.this, ChartActivity.class);
                    intent.putExtra("category", "expense");
                    intent.putExtra("month_year", MONTH_YEAR);
                    intent.putExtra("month_picker", tvMonthPicker.getText().toString().trim());
                    intent.putExtra("content", getString(R.string.expenses)+"\n"+sumExpense.getText());
                    startActivity(intent);
                    break;

                case R.id.categories:
                    intent = new Intent(MainActivity.this, CategorySettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.export:
                    drawer.closeDrawer(Gravity.LEFT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View viewInflated = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_export, navigationView, false);
                    TextView tvStart = viewInflated.findViewById(R.id.tvStart);
                    Calendar now = Calendar.getInstance();
                    tvStart.setOnClickListener( v-> {
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                        tvStart.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                                    }
                                },
                                now.get(Calendar.YEAR), // Initial year selection
                                now.get(Calendar.MONTH), // Initial month selection
                                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                        );
                        dpd.show(getSupportFragmentManager(), "DateStart");

                    });
                    TextView tvEnd = viewInflated.findViewById(R.id.tvEnd);
                    tvEnd.setOnClickListener( v -> {
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                        tvEnd.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                                    }
                                },
                                now.get(Calendar.YEAR), // Initial year selection
                                now.get(Calendar.MONTH), // Initial month selection
                                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                        );
                        dpd.show(getSupportFragmentManager(), "DateEnd");
                    });

                    Spinner spinnerFormat = viewInflated.findViewById(R.id.spinnerFormat);
                    List<String> dataset = new LinkedList<>(Arrays.asList("CSV", "Excel"));
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, R.id.textview, dataset);
                    spinnerFormat.setAdapter(dataAdapter);
                    builder.setView(viewInflated);

                    builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
                    builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
                    builder.show();
                    break;
                case R.id.setting:
                    intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.rateus:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                    View viewInflated2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_rating, navigationView, false);
                    builder2.setView(viewInflated2);

                    builder2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toasty.success(getApplication(), getString(R.string.thanks_feedback), Toasty.LENGTH_SHORT, false).show();
                        }
                    });
                    builder2.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder2.show();
                    break;
                case R.id.about:
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Money Manager")
                            .setMessage(getString(R.string.version)+": 1.1.3\n"+getString(R.string.version_code)+": 10")
                            .setPositiveButton("OK", null)
                            .show();
                    break;
            }
            return false;
        });
    }

    private void getData(String month_year){
        mDatabase.child("histories").child(uID).child(month_year).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListHistory.clear();

                ArrayList<String>times = new ArrayList<>();
                ArrayList<HistoryChild>mListHistoryChild = new ArrayList<>();
                long sumIn = 0, sumEx = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    times.add(snapshot.getKey());

                    HistoryChild child = snapshot.getValue(HistoryChild.class);
                    child.setTimestamp(snapshot.getKey());

                    if(child.getCategory().trim().toLowerCase().equals("income")){
                        sumIn += child.getAmount();
                    }else if(child.getCategory().trim().toLowerCase().equals("expense")){
                        sumEx += child.getAmount();
                    }
                    mListHistoryChild.add(child);
                }

                sumIncome.setText(""+sumIn);
                sumExpense.setText(""+sumEx);
                balance.setText(""+(sumIn-sumEx));

                for (int i = 0; i < times.size(); i++){
                    String date = convertTimestampToDate(Long.parseLong(times.get(i)), "dd-MM");
                    ArrayList<HistoryChild>list = new ArrayList<>();
                    list.add(mListHistoryChild.get(i));
                    for(int j = i+1; j < times.size(); j++){
                        if(convertTimestampToDate(Long.parseLong(times.get(j)), "dd-MM").equals(date)){
                            list.add(mListHistoryChild.get(j));
                            if(j == times.size()-1){
                                i = j;
                                break;
                            }
                            continue;
                        }else if(!convertTimestampToDate(Long.parseLong(times.get(j)), "dd-MM").equals(date)){
                            i = j-1;
                            break;
                        }
                    }
                    mListHistory.add(new History(convertTimestampToDate(Long.parseLong(times.get(i)), "dd-MM EEE"), list));
                }

                if(mListHistory.size() == 0){
                    emptyLinear.setVisibility(View.VISIBLE);
                }else {
                    emptyLinear.setVisibility(View.INVISIBLE);
                }
                mAdapter = new HistoryAdapter(MainActivity.this, mListHistory);
//                recyclerView = findViewById(R.id.recycleView);

                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void initMonthPicker(){
        month1 = findViewById(R.id.month1);
        setUpButton(month1);
        month2 = findViewById(R.id.month2);
        setUpButton(month2);
        month3 = findViewById(R.id.month3);
        setUpButton(month3);
        month4 = findViewById(R.id.month4);
        setUpButton(month4);
        month5 = findViewById(R.id.month5);
        setUpButton(month5);
        month6 = findViewById(R.id.month6);
        setUpButton(month6);
        month7 = findViewById(R.id.month7);
        setUpButton(month7);
        month8 = findViewById(R.id.month8);
        setUpButton(month8);
        month9 = findViewById(R.id.month9);
        setUpButton(month9);
        month10 = findViewById(R.id.month10);
        setUpButton(month10);
        month11 = findViewById(R.id.month11);
        setUpButton(month11);
        month12 = findViewById(R.id.month12);
        setUpButton(month12);

        btnBefore = findViewById(R.id.btnBefore);
        btnAfter = findViewById(R.id.btnAfter);
        tvYear = findViewById(R.id.tvYear);

        btnBefore.setOnClickListener(v -> tvYear.setText(String.valueOf(Integer.parseInt(tvYear.getText().toString()) - 1)));

        btnAfter.setOnClickListener(v -> tvYear.setText(String.valueOf(Integer.parseInt(tvYear.getText().toString()) + 1)));

    }
    private void setUpButton(TextView tv){
        tv.setOnClickListener(v -> {
            if(!tvYear.getText().toString().equals(YEAR)){
                tvMonthPicker.setText(tv.getText() +"-"+ tvYear.getText());
            } else {
                tvMonthPicker.setText(tv.getText());
            }
            MONTH_YEAR = convertMonthtoNumber(tv.getText().toString())+"-"+tvYear.getText().toString();
            getData(MONTH_YEAR);
            monthPicker.setVisibility(View.INVISIBLE);
        });
    }
    private String convertMonthtoNumber(String month){
        if(month.equals(getString(R.string.jan))){
            return "01";
        } else if(month.equals(getString(R.string.feb))){
            return "02";
        } else if(month.equals(getString(R.string.mar))){
            return "03";
        } else if(month.equals(getString(R.string.apr))){
            return "04";
        } else if(month.equals(getString(R.string.may))){
            return "05";
        } else if(month.equals(getString(R.string.jun))){
            return "06";
        } else if(month.equals(getString(R.string.july))){
            return "07";
        } else if(month.equals(getString(R.string.aug))){
            return "08";
        } else if(month.equals(getString(R.string.sep))){
            return "09";
        } else if(month.equals(getString(R.string.oct))){
            return "10";
        } else if(month.equals(getString(R.string.nov))){
            return "11";
        } else if(month.equals(getString(R.string.dec))){
            return "12";
        } else
            return "10";
    }
    private int convertMonthEntoVi(String month){
        if(month.equals("Jan")){
            return R.string.jan;
        } else if(month.equals("Feb")){
            return R.string.feb;
        } else if(month.equals("Mar")){
            return R.string.mar;
        } else if(month.equals("Apr")){
            return R.string.apr;
        } else if(month.equals("May")){
            return R.string.may;
        } else if(month.equals("Jun")){
            return R.string.jun;
        } else if(month.equals("July")){
            return R.string.july;
        } else if(month.equals("Aug")){
            return R.string.aug;
        } else if(month.equals("Sep")){
            return R.string.sep;
        } else if(month.equals("Oct")){
            return R.string.oct;
        } else if(month.equals("Nov")){
            return R.string.nov;
        } else if(month.equals("Dec")){
            return R.string.dec;
        } else
            return R.string.dec;
    }
    private String convertTimestampToDate(long timestamp, String format){
        Date date = new java.util.Date(timestamp);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

}
