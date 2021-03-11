package com.example.moneymanager.chart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.App;
import com.example.moneymanager.models.HistoryChild;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<HistoryChild>mListData;
    private App app;

    public DataAdapter(Context mContext, ArrayList<HistoryChild>mListData){
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder holder, int position) {
        app = new App();
        final HistoryChild data = mListData.get(position);
        if(!data.getName().isEmpty()) {
            holder.name.setText(data.getName());
        }else {
            holder.name.setText(data.getType());
        }
        holder.percent.setText(String.format("%.2f", data.getPercent())+"%");
        holder.amount.setText(data.getAmount()+"");
        holder.bgIcon.setBackgroundResource(app.getICons(data.getType()).second);
        holder.icon.setImageResource(app.getICons(data.getType()).first);
        holder.icon.setColorFilter(Color.parseColor("#ffffff"));

        final ViewGroup.LayoutParams params = holder.progress_bar.getLayoutParams();

        ViewTreeObserver vto = holder.progress_bar.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                holder.progress_bar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width  = holder.progress_bar.getMeasuredWidth();

                params.width = (int) (data.getPercent()/100*width);
                holder.progress_bar.setLayoutParams(params);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, percent, amount;
        private ImageView icon;
        private LinearLayout bgIcon, progress_bar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name =itemView.findViewById(R.id.name);
            percent = itemView.findViewById(R.id.percent);
            amount = itemView.findViewById(R.id.amount);
            bgIcon = itemView.findViewById(R.id.bgIcon);
            progress_bar = itemView.findViewById(R.id.progress_bar);


        }
    }
}
