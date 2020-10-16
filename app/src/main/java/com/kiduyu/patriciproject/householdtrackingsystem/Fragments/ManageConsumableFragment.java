package com.kiduyu.patriciproject.householdtrackingsystem.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.ConsumableAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.ManageConsumableAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.Consumable;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageConsumableFragment extends Fragment {
    private ArrayList<Consumable> consumableArrayList = new ArrayList<>();
    RecyclerView rv_consumables;
    ProgressDialog progressDialog;
    ManageConsumableAdapter consumableAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView nohistory;
    private static final String TAG = "ConsumablesFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_consumable_fragment, container, false);
        rv_consumables = (RecyclerView)view.findViewById(R.id.recyclerview_manage_consumable);
        refreshLayout = view.findViewById(R.id.manage_c_refresh);
        nohistory = view.findViewById(R.id.no_history);
        rv_consumables.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_consumables.setLayoutManager(layoutManager);
        rv_consumables.setFocusable(false);
        refreshLayout.setOnRefreshListener(() -> {
            consumableArrayList.clear();
            FetchData();
            FancyToast.makeText(getActivity(),"Refreshed",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

        });
        EditText search = view.findViewById(R.id.search_editText_history);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        //Content content = new Content();
        //content.execute();
        FetchData();
        return view;
    }

    private void filter(String text) {
        ArrayList<Consumable> filteredList = new ArrayList<>();
        for (Consumable item : consumableArrayList) {
            if (item.getItemname().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
                rv_consumables.setVisibility(View.VISIBLE);
                nohistory.setVisibility(View.GONE);
            }
            else{
                rv_consumables.setVisibility(View.GONE);
                nohistory.setVisibility(View.VISIBLE);

            }
        }


        consumableAdapter = new ManageConsumableAdapter(getActivity(), filteredList);
        rv_consumables.setAdapter(consumableAdapter);
        consumableAdapter.notifyDataSetChanged();
    }

    private void FetchData() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.CONSUMERBLE_API,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("Consumable");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject author = jsonArray.getJSONObject(i);
                                String id = author.getString("id");
                                String name = author.getString("itemname");
                                String remaining = author.getString("itemremaining");
                                String cost = author.getString("itemcost");
                                String itemmeasure = author.getString("itemmeasure");
                                String time = author.getString("itemtime");

                                //mAuthorList.add(new AuthorHome(name, image));
                                //Log.d(TAG, "onResponseApi: "+name+"\n");

                                consumableArrayList.add(new Consumable(id,name,remaining,cost,itemmeasure,time));

                            }
                            progressDialog.dismiss();
                            consumableAdapter = new ManageConsumableAdapter(getActivity(), consumableArrayList);
                            rv_consumables.setAdapter(consumableAdapter);

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
        // requestQueue.add(jsonArrayRequest);
    }
}
