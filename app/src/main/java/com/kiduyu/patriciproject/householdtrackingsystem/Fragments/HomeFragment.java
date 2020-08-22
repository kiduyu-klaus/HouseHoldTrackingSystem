package com.kiduyu.patriciproject.householdtrackingsystem.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kiduyu.patriciproject.householdtrackingsystem.Activities.AddConsumableActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.Activities.ScheduleActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.StatusColor.StatusBar;

public class HomeFragment extends Fragment {
    private Range range1, range2, range3;
    private HalfGauge halfGauge;
    BottomNavigationView bottomNavigationView;
    CardView levels, schedule, reviews, delivery_history, add_consumable, manage_consumable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        bottomNavigationView= (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        levels = view.findViewById(R.id.home_levels);
        schedule = view.findViewById(R.id.home_schedule);
        reviews = view.findViewById(R.id.home_reviews);
        delivery_history = view.findViewById(R.id.home_history);
        add_consumable = view.findViewById(R.id.home_consumable_add);
        manage_consumable = view.findViewById(R.id.home_manage_consumable);

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

        halfGauge = view.findViewById(R.id.halfGauge);
        //set min max and current value
        //set min max and current value
        // halfGauge.minValue = 0.0
        //halfGauge.maxValue = (-150).toDouble()
        // halfGauge.value = (-80).toDouble()
        halfGauge.addRange(range1);
        halfGauge.addRange(range2);
        halfGauge.addRange(range3);
        halfGauge.setMaxValue(20);
        halfGauge.setMinValue(0);
        halfGauge.setValue(11);

        levels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.navigationconsumable);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                        new ConsumablesFragment()).commit();
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                        new ReviewsFragment()).commit();
            }
        });
        delivery_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                        new HistoryFragment()).commit();
            }
        });
        add_consumable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddConsumableActivity.class));
            }
        });
        manage_consumable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                        new ConsumablesFragment()).commit();
            }
        });



        return view;
    }
}
