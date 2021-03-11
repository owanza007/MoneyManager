package com.example.moneymanager.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moneymanager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategorySettingActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private ArrayList<Fragment>mListFragment;

    private RelativeLayout btnBack;
    private LinearLayout footer;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_setting);
        getSupportActionBar().hide();

        mViewPager = findViewById(R.id.mViewPager);
        mTabLayout = findViewById(R.id.mTabLayout);

        btnBack = findViewById(R.id.btnBack);
        footer = findViewById(R.id.footer);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        mListFragment = new ArrayList<>();
        ExpenseFragment expenseFragment = new ExpenseFragment();
        IncomeFragment incomeFragment = new IncomeFragment();
        mListFragment.add(expenseFragment);
        mListFragment.add(incomeFragment);

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, mListFragment);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem() == 0){
                    Intent intent = new Intent(CategorySettingActivity.this, AddExpenseActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(CategorySettingActivity.this, AddIncomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
