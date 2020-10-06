package com.kiduyu.patriciproject.householdtrackingsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.kiduyu.patriciproject.householdtrackingsystem.Activities.ScheduleActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.Consumable;
import com.kiduyu.patriciproject.householdtrackingsystem.R;

import java.util.List;

public class ManageConsumableAdapter extends RecyclerView.Adapter<ManageConsumableAdapter.MyViewHolder> {
    private List<Consumable> consumableList;
    Context mcontext;

    @NonNull
    @Override
    public ManageConsumableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_c_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public ManageConsumableAdapter(Context context, List<Consumable> consumableList) {
        this.consumableList = consumableList;
        this.mcontext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageConsumableAdapter.MyViewHolder holder, int position) {
        final Consumable consumable = consumableList.get(position);


        holder.id.setText(consumable.getId());
        holder.main1.setText("Item name : " + consumable.getItemname());
        holder.main2.setText("remaining: " + consumable.getItemremaining()+" "+consumable.getItemmeasure());

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView main1, main2,id;
        Button delete;

        public MyViewHolder(View view) {
            super(view);
            main1 = view.findViewById(R.id.consumable_manage_name);
            id = view.findViewById(R.id.consumable_manage_id);
            main2 = view.findViewById(R.id.consumable_manage_name2);
            delete = view.findViewById(R.id.consumable_manage_delete);
        }
    }

    @Override
    public int getItemCount() {

        return consumableList.size();
    }
}
