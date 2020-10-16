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
import com.kiduyu.patriciproject.householdtrackingsystem.Models.History;
import com.kiduyu.patriciproject.householdtrackingsystem.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<History> historyList;
    Context mcontext;
    private Range range1, range2, range3;

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public HistoryAdapter(Context context, List<History> consumableList) {
        this.historyList = consumableList;
        this.mcontext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        final History consumable = historyList.get(position);


        holder.name.setText("Name: "+consumable.getItemname());
        holder.order.setText("Order: "+consumable.getItemorder()+" kgs");
        holder.agent.setText("Agent: "+consumable.getItemagent());
        holder.urgency.setText("Urgency: "+consumable.getItemurgency());

    }
    public void filterList(List<History> filteredList) {
        historyList = filteredList;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView name,order,agent,urgency;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.history_name);
            order = view.findViewById(R.id.history_order);
            agent = view.findViewById(R.id.history_agent);
            urgency = view.findViewById(R.id.history_urgency);
        }
    }

    @Override
    public int getItemCount() {

        return historyList.size();
    }
}
