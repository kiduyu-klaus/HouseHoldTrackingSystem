package com.kiduyu.patriciproject.householdtrackingsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class ConsumableAdapter extends RecyclerView.Adapter<ConsumableAdapter.MyViewHolder> {
    private List<Consumable> consumableList;
    Context mcontext;
    private Range range1, range2, range3;

    @NonNull
    @Override
    public ConsumableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.consumable_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public ConsumableAdapter(Context context, List<Consumable> consumableList) {
        this.consumableList = consumableList;
        this.mcontext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumableAdapter.MyViewHolder holder, int position) {
        final Consumable consumable = consumableList.get(position);
        range1 = new Range();
        range1.setColor(Color.parseColor("#ce0000"));
        range1.setFrom(0.0);
        range1.setTo(3.0);

        range2 = new Range();
        range2.setColor(Color.parseColor("#E3E500"));
        range2.setFrom(3.0);
        range2.setTo(6.0);

        range3 = new Range();
        range3.setColor(Color.parseColor("#00b20b"));
        range3.setFrom(6.0);
        range3.setTo(20.0);
        //set min max and current value
        //set min max and current value
        // halfGauge.minValue = 0.0
        //halfGauge.maxValue = (-150).toDouble()
        // halfGauge.value = (-80).toDouble()
        holder.halfGauge.addRange(range1);
        holder.halfGauge.addRange(range2);
        holder.halfGauge.addRange(range3);
        holder.halfGauge.setMaxValue(20);
        holder.halfGauge.setMinValue(0);

        holder.remaining.setText("Remaining : " + consumable.getItemremaining());
        holder.title.setText(consumable.getItemname());
        holder.when.setText("Per : " + consumable.getItemmeasure());
        holder.total_purchased.setText("Cost : " + consumable.getItemcost());
        holder.halfGauge.setValue(Double.parseDouble(consumable.getItemremaining()));
        if (Integer.parseInt(consumable.getItemremaining())<11) {
            holder.itemmenu.setVisibility(View.VISIBLE);
            holder.itemmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mcontext, v);
                    popupMenu.getMenuInflater().inflate(R.menu.main_drawer, popupMenu.getMenu());
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.schedule_delivery:
                                    Intent intent = new Intent(mcontext, ScheduleActivity.class);
                                    intent.putExtra("name", consumable.getItemname());
                                    intent.putExtra("cost", consumable.getItemcost());
                                    intent.putExtra("measure", consumable.getItemmeasure());

                                    mcontext.startActivity(intent);
                                    break;

                            }
                            return true;
                        }
                    });
                }
            });
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, total_purchased, when, remaining;
        HalfGauge halfGauge;
        ImageView itemmenu;

        public MyViewHolder(View view) {
            super(view);
            halfGauge = view.findViewById(R.id.halfGauge_item);
            total_purchased = view.findViewById(R.id.consumable_item_total_delivered);
            title = view.findViewById(R.id.consumable_item_title);
            when = view.findViewById(R.id.consumable_item_date_delivered);
            remaining = view.findViewById(R.id.consumable_item_remaining);
            itemmenu = view.findViewById(R.id.item_menu);
        }
    }

    @Override
    public int getItemCount() {

        return consumableList.size();
    }
}
