package com.example.moneymanager.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.History;
import com.example.moneymanager.models.HistoryChild;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<History> mListHistory;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public HistoryAdapter(Context mContext, ArrayList<History>mListHistory){
        this.mContext = mContext;
        this.mListHistory = mListHistory;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History his = mListHistory.get(position);
        holder.date.setText(his.getDate());
        long income = 0, expense = 0;
        for(HistoryChild child : his.getmListChild()){
            if(child.getCategory().equals("income")){
                income += child.getAmount();
            }else if(child.getCategory().equals("expense")){
                expense += child.getAmount();
            }
        }
        holder.analyze.setText(mContext.getString(R.string.income)+ ": "+income+"  "+ mContext.getString(R.string.expenses)+": "+ expense);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, true);
        holder.recyclerView.setLayoutManager(layoutManager1);
        holder.recyclerView.setNestedScrollingEnabled(true);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());

        HistoryChildAdapter mAdapter = new HistoryChildAdapter(mContext, his.getmListChild());
        holder.recyclerView.setAdapter(mAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return mListHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView analyze;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            analyze = itemView.findViewById(R.id.analyze);
            recyclerView = itemView.findViewById(R.id.recycleView);
        }
    }
}
