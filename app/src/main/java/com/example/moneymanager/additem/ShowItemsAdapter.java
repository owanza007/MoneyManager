package com.example.moneymanager.additem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.App;
import com.example.moneymanager.models.Item;
import com.example.moneymanager.setting.AddExpenseActivity;
import com.example.moneymanager.setting.AddIncomeActivity;

import java.util.ArrayList;

public class ShowItemsAdapter extends RecyclerView.Adapter<ShowItemsAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<Item> mListItem;
    private App app;
    private ImageView icons;
    private LinearLayout keyboard;
    private LinearLayout bgIcons;

    private ImageView preIcon;
    private LinearLayout preBgIcon;
    private SharedPreferences sp;

    public ShowItemsAdapter(Context mContext, ArrayList<Item>mListItem, ImageView icons, LinearLayout keyboard, LinearLayout bgIcons){
        this.mContext = mContext;
        this.mListItem = mListItem;
        this.icons = icons;
        this.keyboard = keyboard;
        this.bgIcons = bgIcons;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_show_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        app = new App();
        final Item an_item = mListItem.get(position);
        final int bg_select = app.getICons(an_item.getType()).second;
        holder.icon.setTag(R.string.key, an_item.getType());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icons.setTag(R.string.key, an_item.getType());
                if(position == mListItem.size()-1 && an_item.isExpense()){
                    Intent intent = new Intent(mContext, AddExpenseActivity.class);
                    mContext.startActivity(intent);
                    return;
                } else if(position == mListItem.size()-1 && !an_item.isExpense()){
                    Intent intent = new Intent(mContext, AddIncomeActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                if(preIcon != null && preBgIcon!=null && !preBgIcon.equals(holder.bgIcon) && !preIcon.equals(holder.icon)){
                    preBgIcon.setBackgroundResource(R.drawable.circle_image_unselect);
                    preIcon.setColorFilter(Color.parseColor("#000000"));
                }
                if(true) {
                    keyboard.setVisibility(View.VISIBLE);
                    icons.setImageResource(app.getICons(an_item.getType()).first);
                    bgIcons.setBackgroundResource(bg_select);
                    icons.setColorFilter(Color.parseColor("#ffffff"));

                    holder.bgIcon.setBackgroundResource(bg_select);
                    holder.icon.setColorFilter(Color.parseColor("#ffffff"));

                    preBgIcon = holder.bgIcon;
                    preIcon = holder.icon;
                }
            }
        });
        holder.icon.setImageResource(app.getICons(an_item.getType()).first);
        sp = mContext.getSharedPreferences("language", Context.MODE_PRIVATE);
        if(sp.getString("lang", "en").equals("vi")){
            holder.name.setText(app.convertVI(an_item.getName()));
        } else
            holder.name.setText(an_item.getName());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item, bgIcon;
        private ImageView icon;
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            bgIcon = itemView.findViewById(R.id.bgIcon);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }
}