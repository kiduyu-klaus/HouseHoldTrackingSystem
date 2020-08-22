package com.kiduyu.patriciproject.householdtrackingsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.StatusColor.StatusBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddConsumableActivity extends AppCompatActivity implements View.OnClickListener  {
    TextInputEditText name,remaining,cost,measured,lasting;
    private ProgressDialog progressDialog;
    Button add_consumable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_add_consumable);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add New Consumable");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name= findViewById(R.id.ed_consumable_name);
        remaining= findViewById(R.id.ed_consumable_total);
        cost= findViewById(R.id.ed_consumable_cost);
        measured= findViewById(R.id.ed_consumable_measurement);
        lasting= findViewById(R.id.ed_consumable_howlong);

        add_consumable= findViewById(R.id.add_consumer_items);

        add_consumable.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View v) {
        if (v == add_consumable)
            AddConsumerItem();

    }

    private void AddConsumerItem() {
        final String cons_name = name.getText().toString().trim();
        final String cons_remaider = remaining.getText().toString().trim();
        final String cons_cost = cost.getText().toString().trim();
        final String cons_measure = measured.getText().toString().trim();
        final String cons_lasting = lasting.getText().toString().trim();


        progressDialog.setMessage("Adding item...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD_CONSUMER,
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
                        progressDialog.hide();
                        Log.d("TAG", "onResponsejsoneror: "+error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("itemname", cons_name);
                params.put("remaining", cons_remaider);
                params.put("cost", cons_cost);
                params.put("item_measure", cons_measure);
                params.put("time_taken", cons_lasting);
                return params;
            }
        };
    }
}