package com.kiduyu.patriciproject.householdtrackingsystem.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.kiduyu.patriciproject.householdtrackingsystem.R;

public class DeliveryFragment extends Fragment {
    private Range range1,range2,range3;
    private HalfGauge halfGauge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        range1=new Range();
        range1.setColor(Color.parseColor("#ce0000"));
        range1.setFrom(0.0);
        range1.setTo(3.0);

        range2=new Range();
        range2.setColor(Color.parseColor("#E3E500"));
        range2.setFrom(3.0);
        range2.setTo(6.0);

        range3=new Range();
        range3.setColor(Color.parseColor("#00b20b"));
        range3.setFrom(6.0);
        range3.setTo(20.0);

        halfGauge=view.findViewById(R.id.halfGauge);
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

        return view;
    }
}
