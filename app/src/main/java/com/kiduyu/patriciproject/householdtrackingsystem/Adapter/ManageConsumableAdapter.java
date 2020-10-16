package com.kiduyu.patriciproject.householdtrackingsystem.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.kiduyu.patriciproject.householdtrackingsystem.Account.LoginActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.Activities.ScheduleActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.HistoryFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.Fragments.ManageConsumableFragment;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.Consumable;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageConsumableAdapter extends RecyclerView.Adapter<ManageConsumableAdapter.MyViewHolder> {
    private ArrayList<Consumable> consumableList;
    Context mcontext;
    AlertDialog alertDialog;
    Button btnSave;
    RelativeLayout layout1,layout2;
    EditText txtemail;

    AlertDialog.Builder dialogBuilder;
    @NonNull
    @Override
    public ManageConsumableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_c_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public ManageConsumableAdapter(Context context, ArrayList<Consumable> consumableList) {
        this.consumableList = consumableList;
        this.mcontext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageConsumableAdapter.MyViewHolder holder, int position) {
        final Consumable consumable = consumableList.get(position);


        holder.id.setText(consumable.getId());
        holder.main1.setText("Item name : " + consumable.getItemname());
        holder.main2.setText("remaining: " + consumable.getItemremaining()+" "+consumable.getItemmeasure());

        holder.delete.setOnClickListener(v -> {
            DeleteConsumable(consumable.getId());
        });
        holder.update.setOnClickListener(v -> {
            UpdateConsumable(consumable.getId(),consumable.getItemname());
        });

    }

    private void UpdateConsumable(String id, String itemname) {
        dialogBuilder = new AlertDialog.Builder(mcontext);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_update, null);
        dialogBuilder.setView(dialogView);

        txtemail = (EditText) dialogView.findViewById(R.id.update_cmble);
        btnSave = (Button) dialogView.findViewById(R.id.btn_save_update);

        TextView head = dialogView.findViewById(R.id.head_update);
        head.setText("Update "+itemname+"'s Quantity");

        layout1 = dialogView.findViewById(R.id.content_ly);
        layout2 = dialogView.findViewById(R.id.pb_ly);
        btnSave.setOnClickListener(view1 -> {
            String emqail = txtemail.getText().toString().trim();

            if (TextUtils.isEmpty(emqail)){
                txtemail.setError("Required!");
                txtemail.requestFocus();
            } else {

                Update(id,emqail,layout1,layout2);

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    private void Update(String id, String emqail, RelativeLayout layout1, RelativeLayout layout2) {
        ProgressDialog progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle("Loading ......");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.UPDATE_SCHEDULE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        alertDialog.dismiss();
                        AppCompatActivity activity = (AppCompatActivity) mcontext;
                        Fragment myFragment = new ManageConsumableFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.d("TAG", "onResponsejsoneror: " + error.getMessage());
                        FancyToast.makeText(mcontext, error.getMessage(), FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("data", emqail);
                return params;
            }
        };
        RequestHandler.getInstance(mcontext).addToRequestQueue(stringRequest);


    }


    private void DeleteConsumable(String id) {
        ProgressDialog progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle("Loading ......");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.DELETE_SCHEDULE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        AppCompatActivity activity = (AppCompatActivity) mcontext;
                        Fragment myFragment = new ManageConsumableFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.d("TAG", "onResponsejsoneror: " + error.getMessage());
                        FancyToast.makeText(mcontext, error.getMessage(), FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestHandler.getInstance(mcontext).addToRequestQueue(stringRequest);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView main1, main2,id;
        Button delete,update;

        public MyViewHolder(View view) {
            super(view);
            main1 = view.findViewById(R.id.consumable_manage_name);
            id = view.findViewById(R.id.consumable_manage_id);
            main2 = view.findViewById(R.id.consumable_manage_name2);
            delete = view.findViewById(R.id.consumable_manage_delete);
            update = view.findViewById(R.id.consumable_manage_update);


        }
    }

    @Override
    public int getItemCount() {

        return consumableList.size();
    }
    public void filterList(ArrayList<Consumable> filteredList) {
        consumableList = filteredList;
        notifyDataSetChanged();
    }
}
