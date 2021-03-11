package com.example.moneymanager.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.App;
import com.example.moneymanager.models.HistoryChild;

import java.util.ArrayList;

public class HistoryChildAdapter extends RecyclerView.Adapter<HistoryChildAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<HistoryChild> mListHistory;
    private App app;
    private SharedPreferences sp;
    public HistoryChildAdapter(Context mContext, ArrayList<HistoryChild>mListHistory){
        this.mContext = mContext;
        this.mListHistory = mListHistory;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_history_child, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        app = new App();
        HistoryChild history = mListHistory.get(position);
        int bgIcon = app.getICons(history.getType()).second;
        holder.icon.setImageResource(app.getICons(history.getType()).first);
        holder.icon.setColorFilter(Color.parseColor("#ffffff"));
        holder.bgIcon.setBackgroundResource(bgIcon);
        if(history.getName()== null || history.getName().isEmpty() ){
            holder.name.setText(history.getType());
        }
        else {
            sp = mContext.getSharedPreferences("language", Context.MODE_PRIVATE);
            if (sp.getString("lang", "en").equals("vi")) {
                holder.name.setText(app.convertVI(history.getName()));
            } else
                holder.name.setText(history.getName());
        }
        if(history.getCategory().equals("expense")){
            holder.amount.setText("-"+history.getAmount());
        }else{
            holder.amount.setText(""+history.getAmount());
        }
        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailItemActivity.class);
            intent.putExtra("timestamp", history.getTimestamp());
            intent.putExtra("category", history.getCategory());
            intent.putExtra("type", history.getType());
            if(history.getName()== null || history.getName().isEmpty()){
                intent.putExtra("name", history.getType());
            } else{
                intent.putExtra("name", history.getName());
            }
            intent.putExtra("amount", history.getAmount());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mListHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout container;
        private LinearLayout bgIcon;
        private ImageView icon;
        private TextView name, amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            bgIcon = itemView.findViewById(R.id.bgIcon);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
