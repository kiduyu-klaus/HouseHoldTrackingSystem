package com.kiduyu.patriciproject.householdtrackingsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Home.HomeActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.Person;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity {
    Bundle extras;
    private static final String TAG = "ScheduleActivity";
    TextInputEditText textname, textOrder;
    Spinner sItemsurgency,sItems;
    String cons_name,cons_cost,cons_measure="";
    Button schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Schedule Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.newd, null)));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.newd));
        getWindow().setStatusBarColor(
                ContextCompat.getColor(this, R.color.newd));
        textname = findViewById(R.id.schedule_consumable_name);
        textOrder = findViewById(R.id.schedule_consumable_total);
        extras = getIntent().getExtras();
        if (extras != null) {
            cons_name = extras.getString("name");
            textname.setText(cons_name);
        }
        schedule = findViewById(R.id.schedule_consumer_items);

        schedule.setOnClickListener(v -> {
            if (extras != null) {
                cons_name = extras.getString("name");
                cons_cost = extras.getString("cost");
                cons_measure = extras.getString("measure");
                Log.d(TAG, "onCreate: "+cons_cost);
                Toast.makeText(ScheduleActivity.this, cons_name, Toast.LENGTH_SHORT).show();
                textname.setEnabled(false);
                String cons_total=textOrder.getText().toString().trim();
                String spinner1 = sItems.getSelectedItem().toString();
                String spinner2 = sItemsurgency.getSelectedItem().toString();

                ScheduleDelivery(cons_name,cons_total,spinner1,spinner2);
            } else{
                Toast.makeText(ScheduleActivity.this, "null", Toast.LENGTH_SHORT).show();
                String cons_total=textOrder.getText().toString().trim();
                String cons_name=textname.getText().toString().trim();
                String spinner1 = sItems.getSelectedItem().toString();
                String spinner2 = sItemsurgency.getSelectedItem().toString();
                ScheduleDelivery(cons_name,cons_total,spinner1,spinner2);
            }
        });



        // you need to have a list of data that you want the spinner to display
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Choose a delivery Agent");
        /*spinnerArray.add("John Joe");
        spinnerArray.add("Michael Todd");
        spinnerArray.add("James Maina");
        spinnerArray.add("John Mwangi");
        spinnerArray.add("Pamella Muthoni");

         */
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child("Delivery");

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot productsSnapshot: dataSnapshot.getChildren()) {
                    Person productname = productsSnapshot.getValue(Person.class);
                    spinnerArray.add(productname.getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ScheduleActivity.this, String.valueOf(databaseError.getMessage()), Toast.LENGTH_SHORT).show();

            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.spinner1);
        sItems.setAdapter(adapter);

        // you need to have a list of data that you want the spinner to display
        List<String> spinnerArrayurgency = new ArrayList<String>();
        spinnerArrayurgency.add("Delivery Urgency");
        spinnerArrayurgency.add("5 hrs");
        spinnerArrayurgency.add("8 hrs");
        spinnerArrayurgency.add("less Than a day");
        spinnerArrayurgency.add("2 days");

        ArrayAdapter<String> adapterurgency = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayurgency);

        adapterurgency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sItemsurgency = (Spinner) findViewById(R.id.spinner_urgency);
        sItemsurgency.setAdapter(adapterurgency);






    }

    private void ScheduleDelivery(String cons_name, String cons_total, String spinner1, String spinner2) {
        if (TextUtils.isEmpty(cons_total)){
            textOrder.setError("Required");
            textOrder.requestFocus();
        } else  if(spinner1.equals("Choose a delivery Agent")){
            TextView errorText = (TextView)sItems
                    .getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("choose an Agent");

        } else if (spinner2.equals("Delivery Urgency")){
            TextView errorText = (TextView)sItemsurgency
                    .getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("choose an urgency level");

        } else if (TextUtils.isEmpty(cons_name)){
            textname.setError("Required");
            textname.requestFocus();
        } else {
            Context context;
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Loading ......");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            sendToDb(cons_name,cons_total,spinner1,spinner2, progressDialog);
        }
    }

    private void sendToDb(String cons_name, String cons_total, String spinner1, String spinner2, ProgressDialog progressDialog) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD_SCHEDULE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("TAG", "onResponsejson: "+jsonObject.getString("message"));
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.d("TAG", "onResponsejsoneror: " + error.getMessage());
                        FancyToast.makeText(getApplicationContext(), error.getMessage(), FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("itemname", cons_name);
                params.put("itemorder", cons_total);
                params.put("itemagent", spinner1);
                params.put("itemurgency", spinner2);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


}