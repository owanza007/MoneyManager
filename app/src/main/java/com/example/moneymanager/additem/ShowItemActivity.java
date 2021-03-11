package com.example.moneymanager.additem;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.main.MainActivity;
import com.example.moneymanager.models.App;
import com.example.moneymanager.models.HistoryChild;
import com.example.moneymanager.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ShowItemActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    //------------Variable Header----------//
    private RelativeLayout btnBack;
    private NiceSpinner spinner;
    //------------Variable Body----------//
    private RecyclerView recyclerItems;
    private ShowItemsAdapter mAdapter;
    //-----------Variable for Keyboard---------------//
    private LinearLayout keyboard, bgIcon;
    private LinearLayout numberKb;
    private TextView amount;
    private TextView tvName;
    private ImageView icon;
    private TextView num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    private TextView minus, plus, today, dot;
    private ImageButton backspace, result;
    //-------------Database---------------------//
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;
    //----------------Other--------------------//
    private Calendar now = Calendar.getInstance();
    private static String AMOUNT = "0";
    private static String DATE;
    private static String TIME;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        getSupportActionBar().hide();

        DATE = convertTimestampToDate(System.currentTimeMillis(), "dd-MM-yyyy");
        TIME = convertTimestampToDate(System.currentTimeMillis(), "hh:mm:ss");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        btnBack = findViewById(R.id.btnBack);
        recyclerItems = findViewById(R.id.recycleItems);
        spinner = findViewById(R.id.spinner);
        keyboard = findViewById(R.id.keyboard);
        bgIcon = findViewById(R.id.bgIcon);
        numberKb = findViewById(R.id.numberKb);

        icon = findViewById(R.id.icon);

        amount = findViewById(R.id.amount);
        amount.setOnClickListener(this);

        tvName = findViewById(R.id.tvName);
        tvName.setOnClickListener(this);

        setUpKeyboard();

        showItems(recyclerItems, "expense");

        //---------------Chọn mục thu hoặc chi----------------//
        List<String> dataset = new LinkedList<>(Arrays.asList(getString(R.string.expenses), getString(R.string.income)));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                if(position == 0){
                    showItems(recyclerItems, "expense");
                }else{
                    showItems(recyclerItems, "income");
                }
            }
        });

        //----------- Chỉnh sửa lịch sử ----------------//
        editHistory();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(keyboard.getVisibility() == View.VISIBLE){
            keyboard.setVisibility(View.INVISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }

    private void showItems(final RecyclerView recyclerItems, final String category){
        final ArrayList<Item>mListItem = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerItems.setLayoutManager(layoutManager);
        recyclerItems.setItemAnimator(new DefaultItemAnimator());
        mDatabase.child("categories").child(uID).child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListItem.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mListItem.add(new Item((String) snapshot.getValue(), snapshot.getKey()));
                }
                if(category.equals("expense")) {
                    mListItem.add(new Item("add", getString(R.string.add), true));
                }else{
                    mListItem.add(new Item("add", getString(R.string.add), false));
                }
                mAdapter = new ShowItemsAdapter(ShowItemActivity.this, mListItem, icon, keyboard, bgIcon);
                mAdapter.notifyDataSetChanged();
                recyclerItems.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    /**
     * Keyboard
     */
    private void setUpKeyboard(){

        //Todo: Number press
        num0 = findViewById(R.id.num0);
        num0.setOnClickListener(this);
        num1 = findViewById(R.id.num1);
        num1.setOnClickListener(this);
        num2 = findViewById(R.id.num2);
        num2.setOnClickListener(this);
        num3 = findViewById(R.id.num3);
        num3.setOnClickListener(this);
        num4 = findViewById(R.id.num4);
        num4.setOnClickListener(this);
        num5 = findViewById(R.id.num5);
        num5.setOnClickListener(this);
        num6 = findViewById(R.id.num6);
        num6.setOnClickListener(this);
        num7 = findViewById(R.id.num7);
        num7.setOnClickListener(this);
        num8 = findViewById(R.id.num8);
        num8.setOnClickListener(this);
        num9 = findViewById(R.id.num9);
        num9.setOnClickListener(this);

        //Todo:
        today = findViewById(R.id.today);
        today.setText(getString(R.string.today)+"\n"+convertTimestampToDate(System.currentTimeMillis(), "dd/MM"));
        today.setOnClickListener(this);
        minus = findViewById(R.id.minus);
        minus.setOnClickListener(this);
        plus  = findViewById(R.id.plus);
        plus.setOnClickListener(this);
        dot = findViewById(R.id.dot);
        dot.setOnClickListener(this);
        backspace = findViewById(R.id.backspace);
        backspace.setOnClickListener(this);
        result = findViewById(R.id.result);
        result.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvName:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_name, keyboard, false);
                final EditText dialog_edName = viewInflated.findViewById(R.id.dialog_edName);
                builder.setView(viewInflated);
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = dialog_edName.getText().toString().trim();
                        if(!name.isEmpty()){
                            tvName.setText(name);
                            dialog_edName.setText("");
                        }
                    }
                }).setNegativeButton(getString(R.string.no),null);
                builder.show();
            case R.id.num0:
                AMOUNT = amount.getText().toString();
                if(AMOUNT.charAt(0) != '0'){
                    AMOUNT += "0";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num1:
                AMOUNT = amount.getText().toString();
                if(AMOUNT.length() == 1 && AMOUNT.charAt(0) == '0'){
                    AMOUNT = "1";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "1";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num2:
                AMOUNT = amount.getText().toString();
                if(AMOUNT.length() == 1 && AMOUNT.charAt(0) == '0'){
                    AMOUNT = "2";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "2";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num3:
                AMOUNT = amount.getText().toString();
                if(AMOUNT.length() == 1 && AMOUNT.charAt(0) == '0'){
                    AMOUNT = "3";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "3";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num4:
                AMOUNT = amount.getText().toString();
                if(amount.length() == 1 && amount.getText().toString().charAt(0) == '0'){
                    AMOUNT = "4";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "4";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num5:
                AMOUNT = amount.getText().toString();
                if(amount.length() == 1 && amount.getText().toString().charAt(0) == '0'){
                    AMOUNT = "5";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "5";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num6:
                AMOUNT = amount.getText().toString();
                if(amount.length() == 1 && amount.getText().toString().charAt(0) == '0'){
                    AMOUNT = "6";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "6";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num7:
                AMOUNT = amount.getText().toString();
                if(amount.length() == 1 && amount.getText().toString().charAt(0) == '0'){
                    AMOUNT = "7";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "7";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num8:
                AMOUNT = amount.getText().toString();
                if(amount.length() == 1 && amount.getText().toString().charAt(0) == '0'){
                    AMOUNT = "8";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "8";
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.num9:
                AMOUNT = amount.getText().toString();
                if(amount.length() == 1 && amount.getText().toString().charAt(0) == '0'){
                    AMOUNT = "9";
                    amount.setText(AMOUNT);
                }
                else{
                    AMOUNT += "9";
                    amount.setText(AMOUNT);
                }
                break;

            case R.id.dot:
                AMOUNT = amount.getText().toString();
                AMOUNT += ".";
                amount.setText(AMOUNT);
                break;
            case R.id.plus:
                AMOUNT = amount.getText().toString();
                result.setImageResource(R.drawable.equal);
                try {
                    if(!AMOUNT.contains("+") && !AMOUNT.contains("-")) {
                        AMOUNT += "+";
                        amount.setText(AMOUNT);
                    }
                    else if(AMOUNT.contains("+")){
                        String number1 = AMOUNT.substring(0, AMOUNT.indexOf("+"));
                        String number2 = AMOUNT.substring(AMOUNT.indexOf("+")+1);
                        Double num1 = Double.parseDouble(number1);
                        Double num2 = Double.parseDouble(number2);
                        amount.setText((num1 + num2) +"+");
                    }else if(AMOUNT.contains("-")){
                        String number1 = AMOUNT.substring(0, AMOUNT.indexOf("-"));
                        String number2 = AMOUNT.substring(AMOUNT.indexOf("-")+1);
                        Double num1 = Double.parseDouble(number1);
                        Double num2 = Double.parseDouble(number2);
                        amount.setText((num1 - num2) +"+");
                    }
                }catch (Exception e){
                    System.out.println("Number Format");
                }

                break;
            case R.id.minus:
                AMOUNT = amount.getText().toString();
                result.setImageResource(R.drawable.equal);
                try {
                    if(!AMOUNT.contains("-") && !AMOUNT.contains("+")) {
                        AMOUNT += "-";
                        amount.setText(AMOUNT);
                    }
                    else if(AMOUNT.contains("-")){

                        String number1 = AMOUNT.substring(0, AMOUNT.indexOf("-"));
                        String number2 = AMOUNT.substring(AMOUNT.indexOf("-")+1);
                        Double num1 = Double.parseDouble(number1);
                        Double num2 = Double.parseDouble(number2);
                        amount.setText((num1 - num2) +"-");
                    } else if(AMOUNT.contains("+")){
                        String number1 = AMOUNT.substring(0, AMOUNT.indexOf("+"));
                        String number2 = AMOUNT.substring(AMOUNT.indexOf("+")+1);
                        Double num1 = Double.parseDouble(number1);
                        Double num2 = Double.parseDouble(number2);
                        amount.setText((num1 + num2) +"-");
                    }

                }catch (Exception e){
                    System.out.println("Number Format");
                }
                break;
            case R.id.today:
                AMOUNT = amount.getText().toString();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ShowItemActivity.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getSupportFragmentManager(), "DatePicker");
                break;
            case R.id.backspace:
                AMOUNT = amount.getText().toString();
                if(AMOUNT.length() == 1){
                amount.setText("0");
                }
                else if(AMOUNT.length()!=0) {
                    AMOUNT = AMOUNT.substring(0, AMOUNT.length() - 1);
                    amount.setText(AMOUNT);
                }
                break;
            case R.id.result:
                try {
                    if(amount.getText().toString().trim().contains("+") || amount.getText().toString().trim().contains("-")){
                        System.out.println(caculateNumber(amount.getText().toString().trim()));
                        amount.setText(caculateNumber(amount.getText().toString().trim()));
                        result.setImageResource(R.drawable.result);
                    }
                    else if(Long.parseLong(amount.getText().toString().trim()) != 0) {

                        long timestamp = convertDateToTimestamp(DATE + " " + TIME);
                        String month_year = convertTimestampToDate(timestamp, "MM-yyyy");
                        String type = (String) icon.getTag(R.string.key);
                        String category;
                        if (spinner.getText().toString().toLowerCase().equals("chi") || spinner.getText().toString().toLowerCase().equals("expenses")) {
                            category = "expense";
                        } else
                            category = "income";

                        if (type == null) {
                            Toasty.error(this, getString(R.string.select_category_error), Toasty.LENGTH_SHORT, false).show();
                            break;
                        }
                        String name = tvName.getText().toString().trim();
                        String capName = null;
                        if (!name.isEmpty()) {
                            capName = name.substring(0, 1).toUpperCase() + name.substring(1);
                        } else {
                            capName = type.substring(0, 1).toUpperCase() + type.substring(1);
                        }

                        long _amount = Long.parseLong(amount.getText().toString().trim());
                        HistoryChild his = new HistoryChild(category, type, capName, _amount);
                        mDatabase.child("histories").child(uID).child(month_year).child(String.valueOf(timestamp)).setValue(his);
                        mDatabase.child("histories").child(uID).child(month_year).child(String.valueOf(timestamp)).child("percent").setValue(null);
                        Toasty.success(this, getString(R.string.add_success), Toasty.LENGTH_SHORT, false).show();

                        Intent intent = new Intent(ShowItemActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toasty.error(this, getString(R.string.infor_error), Toasty.LENGTH_SHORT, false).show();
                        break;
                    }
                } catch (NumberFormatException e){
                    Toasty.error(this, getString(R.string.error_format), Toasty.LENGTH_SHORT, false).show();
                }
                break;
            default:
                break;
        }
    }

    //-------------------------------------------------//
    private void editHistory() {
        if (getIntent().getExtras() != null) {
            String timestamp = getIntent().getExtras().getString("timestamp");
            String category = getIntent().getExtras().getString("category");
            String type = getIntent().getExtras().getString("type");
            String name = getIntent().getExtras().getString("name");
            long _amount = getIntent().getExtras().getLong("amount");

            DATE = convertTimestampToDate(Long.parseLong(timestamp), "dd-MM-yyyy");
            TIME = convertTimestampToDate(Long.parseLong(timestamp), "hh:mm:ss");

            if (category.toLowerCase().equals("chi") || category.toLowerCase().equals("expense")) {
                spinner.setSelectedIndex(0);
            } else {
                spinner.setSelectedIndex(1);
            }
            keyboard.setVisibility(View.VISIBLE);
            today.setText("Today\n" + convertTimestampToDate(Long.parseLong(timestamp), "dd/MM"));
            amount.setText(String.valueOf(_amount));
            tvName.setText(name);
            icon.setTag(R.string.key, type);
            icon.setImageResource(new App().getICons(type).first);
            icon.setColorFilter(Color.parseColor("#ffffff"));
            bgIcon.setBackgroundResource(new App().getICons(type).second);
        }
    }

    //-----------------------------------------------//
    private String caculateNumber(String expression){
        if(expression.contains("+")){
            double num_1 = Double.parseDouble((expression.substring(0, expression.indexOf("+"))));
            double num_2 = Double.parseDouble((expression.substring(expression.indexOf("+")+1)));
            return String.valueOf((long)(num_1+num_2));
        } else{
            double num_1 = Double.parseDouble((expression.substring(0, expression.indexOf("-"))));
            double num_2 = Double.parseDouble((expression.substring(expression.indexOf("-")+1)));
            return String.valueOf((long)(num_1-num_2));
        }
    }

    private String convertTimestampToDate(long timestamp, String format) {
        Date date = new java.util.Date(timestamp);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    private long convertDateToTimestamp(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    //---------------------------------------------//
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        DATE = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        if(DATE.equals(convertTimestampToDate(System.currentTimeMillis(), "dd-MM-yyyy"))){
            today.setText(getString(R.string.today)+"\n"+dayOfMonth+"/"+(monthOfYear+1));
        } else if(DATE.equals(convertTimestampToDate(System.currentTimeMillis(), "d-MM-yyyy"))){
            today.setText(getString(R.string.today)+"\n"+dayOfMonth+"/"+(monthOfYear+1));
        } else if(DATE.equals(convertTimestampToDate(System.currentTimeMillis(), "dd-M-yyyy"))){
            today.setText(getString(R.string.today)+"\n"+dayOfMonth+"/"+(monthOfYear+1));
        }
        else{
            today.setText(dayOfMonth+"/"+(monthOfYear+1)+"\n"+year);
        }
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                ShowItemActivity.this,
                9, 0, true
        );
        tpd.show(getSupportFragmentManager(), "TimePicker");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        TIME = hourOfDay+":"+minute+":"+second;
    }

}
