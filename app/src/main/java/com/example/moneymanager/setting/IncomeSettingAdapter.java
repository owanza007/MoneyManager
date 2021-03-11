package com.example.moneymanager.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.App;
import com.example.moneymanager.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class IncomeSettingAdapter extends RecyclerView.Adapter<IncomeSettingAdapter.ViewHolder>{

    Context mContext;
    private ArrayList<Item> mListItem;
    private App app;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static String uID;
    private SharedPreferences sp;

    public IncomeSettingAdapter (Context mContext, ArrayList<Item>mListItem){
        this.mContext = mContext;
        this.mListItem = mListItem;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_income_setting, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();
        app = new App();
        Item incomeSetting = mListItem.get(position);
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getString(R.string.delete_title))
                        .setMessage(mContext.getString(R.string.delete_mes))
                        .setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListItem.remove(position);
                                mDatabase.child("categories").child(uID).child("expense").child(incomeSetting.getName()).setValue(null);
                                notifyItemChanged(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(mContext.getString(R.string.no), null)
                        .show();

            }
        });
        holder.icon.setImageResource(app.getICons(incomeSetting.getType()).first);
        sp = mContext.getSharedPreferences("language", Context.MODE_PRIVATE);

        if(sp.getString("lang", "en").equals("vi")){
            holder.name.setText(app.convertVI(incomeSetting.getName()));
        } else
            holder.name.setText(incomeSetting.getName());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView btnDel;
        private ImageView icon;
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDel = itemView.findViewById(R.id.btnDel);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }



}