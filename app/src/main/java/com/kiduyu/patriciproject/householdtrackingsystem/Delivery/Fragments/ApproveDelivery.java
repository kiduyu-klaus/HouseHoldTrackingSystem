package com.kiduyu.patriciproject.householdtrackingsystem.Delivery.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.DeliveryApproveAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.HistoryAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.History;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;
import com.kiduyu.patriciproject.householdtrackingsystem.SharedPref.Prevalent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kiduyu klaus
 * on 10/11/2020 04:08 2020
 */
public class ApproveDelivery extends Fragment {
    private ArrayList<History> consumableArrayList = new ArrayList<>();
    EditText search;
    TextView pending, completed, notfound;
    RecyclerView recyclerView;
    DeliveryApproveAdapter consumableAdapter;
    private static final String TAG = "ApproveDelivery";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_approve, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_deliveries_admin);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getPending();

        search = (EditText) view.findViewById(R.id.search_editText_deliveries_admin);


        notfound = view.findViewById(R.id.ly_no_deliveries_admin);
        notfound.setText("No Pending Deliveries");
        return view;
    }

    private void getPending() {
        consumableArrayList.clear();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.PENDING_SCHEDULE_API,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("Consumable");
                            if (jsonArray.length()!=0){

                                recyclerView.setVisibility(View.VISIBLE);
                                notfound.setVisibility(View.GONE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject author = jsonArray.getJSONObject(i);
                                    Log.d(TAG, "onResponse: "+Prevalent.currentOnlineUser.getName());

                                    String status = author.getString("status");
                                    String itemagent = author.getString("itemagent");
                                  if (itemagent.equals(Prevalent.currentOnlineUser.getName().trim()) && status.equals("Pending") ) {
                                      String id = author.getString("id");
                                      String itemname = author.getString("itemname");
                                      String itemorder = author.getString("itemorder");
                                      String itemurgency = author.getString("itemurgency");
                                      consumableArrayList.add(new History(id, itemname, itemorder, itemagent, itemurgency, status));
                                  }

                                    Log.d(TAG, "onResponse: "+itemagent);
                                        //mAuthorList.add(new AuthorHome(name, image));
                                        //Log.d(TAG, "onResponseApi: "+name+"\n");


                                    if (consumableArrayList.size()==0) {
                                        recyclerView.setVisibility(View.GONE);
                                        notfound.setVisibility(View.VISIBLE);
                                        notfound.setText("No Pending Deliveries");
                                    }

                                }
                                progressDialog.dismiss();
                                consumableAdapter = new DeliveryApproveAdapter(getActivity(), consumableArrayList);
                                recyclerView.setAdapter(consumableAdapter);

                            } else{

                                recyclerView.setVisibility(View.GONE);
                                notfound.setVisibility(View.VISIBLE);

                            }



                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                volleyError.printStackTrace();
            }
        });
        RequestHandler.getInstance(getActivity()).addToRequestQueue(request);
    }
}
