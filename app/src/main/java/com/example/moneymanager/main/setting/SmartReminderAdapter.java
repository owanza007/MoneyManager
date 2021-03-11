package com.example.moneymanager.main.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.SmartReminder;

import java.util.ArrayList;

public class SmartReminderAdapter extends RecyclerView.Adapter<SmartReminderAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SmartReminder> mListSmart;

    public SmartReminderAdapter(Context mContext, ArrayList<SmartReminder> mListSmart) {
        this.mContext = mContext;
        this.mListSmart = mListSmart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_smart_reminder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SmartReminder smartReminder = mListSmart.get(position);
        System.out.println(smartReminder.getTime());
        System.out.println(smartReminder.isTurn_on());

        holder.tvTime.setText(smartReminder.getTime());
        if (smartReminder.isTurn_on()) {
            holder.switchTime.setChecked(true);
        } else {
            holder.switchTime.setChecked(false);
        }
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListSmart.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListSmart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Switch switchTime;
        private TextView tvTime;
        private ImageView imgDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            switchTime = itemView.findViewById(R.id.switchTime);
            tvTime = itemView.findViewById(R.id.tvTime);
            imgDel = itemView.findViewById(R.id.imgDel);
        }
    }
}
