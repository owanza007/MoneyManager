package com.example.moneymanager.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.App;
import com.example.moneymanager.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AddExpenseActivity extends AppCompatActivity {
    private RelativeLayout btnBack, btnSubmit;
    private TextView title1;
    private RecyclerView mRecyclerView;

    private LinearLayout bgIcon;
    private ImageView icon;
    private EditText edName;

    private App app;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        getSupportActionBar().hide();

        app = new App();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        bgIcon = findViewById(R.id.bgIcon);
        icon = findViewById(R.id.icon);
        icon.setColorFilter(Color.parseColor("#ffffff"));
        edName = findViewById(R.id.edName);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        title1 = findViewById(R.id.title1);

        mRecyclerView = findViewById(R.id.mRecycleView);

        showItemsByTopic(R.string.categories, title1, mRecyclerView, getListExpense());
        setUpSubmitButton();
    }

    private void setUpSubmitButton() {
        btnSubmit.setOnClickListener(v -> {
            String name = edName.getText().toString().trim();
            if(!name.isEmpty()){
                String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
                mDatabase.child("categories").child(uID).child("expense").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        edName.setText("");
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            if(snapshot.getKey().equals(capName)){
                                Toasty.warning(AddExpenseActivity.this, getString(R.string.category_exist), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        mDatabase.child("categories").child(uID).child("expense").child(capName).setValue(icon.getTag(R.string.key));
                        Toasty.success(AddExpenseActivity.this, getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                Toasty.error(AddExpenseActivity.this, getString(R.string.please_enter), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showItemsByTopic(int topic, TextView title, RecyclerView recyclerView, ArrayList<Item> mListItem){
        title.setText(topic);
        showItems(recyclerView, mListItem);
    }
    private ArrayList<Item> getListExpense(){
        ArrayList<Item>mListItem = new ArrayList<>();
        List<String> fullType = Arrays.asList(app.getArrType());
        for (int i = 0; i < fullType.size(); i++){
            mListItem.add(new Item(fullType.get(i)));
        }
        return mListItem;
    }
    private void showItems(RecyclerView recyclerItems, ArrayList<Item> mListItem){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerItems.setLayoutManager(layoutManager);
        recyclerItems.setNestedScrollingEnabled(false);
        recyclerItems.setItemAnimator(new DefaultItemAnimator());
        AddItemsAdapter adapter = new AddItemsAdapter(AddExpenseActivity.this, mListItem, icon, bgIcon);
        recyclerItems.setAdapter(adapter);
    }
}
