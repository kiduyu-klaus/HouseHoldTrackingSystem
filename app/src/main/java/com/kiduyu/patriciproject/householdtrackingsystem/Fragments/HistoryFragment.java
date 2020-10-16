package com.kiduyu.patriciproject.householdtrackingsystem.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.ConsumableAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.HistoryAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.Consumable;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.History;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private ArrayList<History> consumableArrayList = new ArrayList<>();
    EditText search;
    TextView pending, completed, notfound;
    RecyclerView recyclerView;
    HistoryAdapter consumableAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_deliveries);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getPending();

        search = (EditText) view.findViewById(R.id.search_editText_deliveries);

        completed = view.findViewById(R.id.tv_completed_deliveries);
        pending = view.findViewById(R.id.tvpending_deliveries);
        notfound = view.findViewById(R.id.ly_no_deliveries);
        notfound.setText("No Pending Deliveries");

        pending.setOnClickListener(v -> {
            pending.setBackgroundResource(R.drawable.bg_leftswitch_select);
            completed.setBackgroundResource(R.drawable.bg_rightswitch);
            pending.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            completed.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            notfound.setText("No Pending Deliveries");

            getPending();
        });
        completed.setOnClickListener(v -> {
            pending.setBackgroundResource(R.drawable.bg_rightswitch);
            completed.setBackgroundResource(R.drawable.bg_leftswitch_select);
            completed.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            pending.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            notfound.setText("No Completed Deliveries");
            getCompleted();
        });

        return view;
    }

    private void getCompleted() {
        consumableArrayList.clear();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.COMPLETED_SCHEDULE_API,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("Consumable");
                            Log.d("TAG", "onResponse: "+jsonArray.length());
                            if (jsonArray.length()!=0){
                                recyclerView.setVisibility(View.VISIBLE);
                                notfound.setVisibility(View.GONE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject author = jsonArray.getJSONObject(i);
                                    String id = author.getString("id");
                                    String itemname = author.getString("itemname");
                                    String itemorder = author.getString("itemorder");
                                    String itemagent = author.getString("itemagent");
                                    String itemurgency = author.getString("itemurgency");
                                    String status = author.getString("status");

                                    //mAuthorList.add(new AuthorHome(name, image));
                                    //Log.d(TAG, "onResponseApi: "+name+"\n");

                                    consumableArrayList.add(new History(id, itemname, itemorder, itemagent, itemurgency, status));

                                }
                            } else{
                                recyclerView.setVisibility(View.GONE);
                                notfound.setVisibility(View.VISIBLE);

                            }

                            progressDialog.dismiss();
                            consumableAdapter = new HistoryAdapter(getActivity(), consumableArrayList);
                            recyclerView.setAdapter(consumableAdapter);

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
                                   String id = author.getString("id");
                                   String itemname = author.getString("itemname");
                                   String itemorder = author.getString("itemorder");
                                   String itemagent = author.getString("itemagent");
                                   String itemurgency = author.getString("itemurgency");
                                   String status = author.getString("status");

                                   //mAuthorList.add(new AuthorHome(name, image));
                                   //Log.d(TAG, "onResponseApi: "+name+"\n");

                                   consumableArrayList.add(new History(id, itemname, itemorder, itemagent, itemurgency, status));

                               }
                               progressDialog.dismiss();
                               consumableAdapter = new HistoryAdapter(getActivity(), consumableArrayList);
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
