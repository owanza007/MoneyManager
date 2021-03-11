package com.example.moneymanager.setting;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.App;
import com.example.moneymanager.models.Item;

import java.util.ArrayList;

public class AddItemsAdapter extends RecyclerView.Adapter<AddItemsAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<Item> mListItem;
    private App app;
    private ImageView icons;
    private LinearLayout bgIcons;
    private ImageView preIcons;
    private LinearLayout preBgIcons;

    public AddItemsAdapter(Context mContext, ArrayList<Item>mListItem, ImageView icons, LinearLayout bgIcons){
        this.mContext = mContext;
        this.mListItem = mListItem;
        this.icons = icons;
        this.bgIcons = bgIcons;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_add_items, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        app = new App();
        final Item an_item = mListItem.get(position);

        final int bg_select = app.getICons(an_item.getType()).second;

        holder.icon.setImageResource(app.getICons(an_item.getType()).first);
        holder.icon.setTag(R.string.key, an_item.getType());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icons.setTag(R.string.key, an_item.getType());
                if(preBgIcons!=null && preIcons!=null && !preIcons.equals(holder.icon) && !preBgIcons.equals(holder.bgIcon)){
                    preIcons.setColorFilter(Color.parseColor("#000000"));
                    preBgIcons.setBackgroundResource(R.drawable.circle_image_unselect);
                }
                bgIcons.setBackgroundResource(bg_select);
                icons.setColorFilter(Color.parseColor("#ffffff"));
                icons.setImageResource(app.getICons(an_item.getType()).first);

                holder.bgIcon.setBackgroundResource(bg_select);
                holder.icon.setColorFilter(Color.parseColor("#ffffff"));
                preBgIcons = holder.bgIcon;
                preIcons = holder.icon;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private LinearLayout header, item, bgIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            item = itemView.findViewById(R.id.item);
            bgIcon = itemView.findViewById(R.id.bgIcon);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
