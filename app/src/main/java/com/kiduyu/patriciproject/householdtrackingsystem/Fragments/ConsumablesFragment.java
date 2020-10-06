package com.kiduyu.patriciproject.householdtrackingsystem.Fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.ConsumableAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.Consumable;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ConsumablesFragment extends Fragment {
    private ArrayList<Consumable> consumableArrayList = new ArrayList<>();
    RecyclerView rv_consumables;
    ProgressDialog progressDialog;
    ConsumableAdapter consumableAdapter;
    SwipeRefreshLayout refreshLayout;
    private static final String TAG = "ConsumablesFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumables_fragment, container, false);
        rv_consumables = (RecyclerView)view.findViewById(R.id.consumables_rv);
        refreshLayout = view.findViewById(R.id.consumable_reload);
        rv_consumables.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_consumables.setLayoutManager(layoutManager);
        rv_consumables.setFocusable(false);
        refreshLayout.setOnRefreshListener(() -> {
            consumableArrayList.clear();
            FetchData();
            FancyToast.makeText(getActivity(),"Refreshed",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

        });
        //Content content = new Content();
        //content.execute();
        FetchData();
        return view;
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
                                String name = author.getString("itemname");
                                String remaining = author.getString("itemremaining");
                                String cost = author.getString("itemcost");
                                String itemmeasure = author.getString("itemmeasure");
                                String time = author.getString("itemtime");

                                //mAuthorList.add(new AuthorHome(name, image));
                                //Log.d(TAG, "onResponseApi: "+name+"\n");

                                consumableArrayList.add(new Consumable("",name,remaining,cost,itemmeasure,time));

                            }
                            progressDialog.dismiss();
                            consumableAdapter = new ConsumableAdapter(getActivity(), consumableArrayList);
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


    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            //bindAudioService();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            consumableAdapter.notifyDataSetChanged();

        }

       @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {

            int size = 10;
            String jet="";
            Log.d("items", "complete" + size);
            for(int l=0; l<=10; l++){

                Random ran = new Random();
                // Assumes max and min are non-negative.
                int randomInt = 5 + ran.nextInt(20 - 5 + 1);
                int remaining = 2 + ran.nextInt(5 - 2 + 1);

                int balance=randomInt - remaining;

                String[] arr={"Maize Flour", "Wheat Flour", "Milk", "Cabbages", "Tomatoes",
                        "Rice", "Salt", "Potatoes", "Beans", "Eggs"};
                int randomNumber=ran.nextInt(arr.length);
                LocalDate startDate = LocalDate.of(2020, 6, 1); //start date
                long start = startDate.toEpochDay();
                //System.out.println(start);

                LocalDate endDate = LocalDate.now(); //end date
                long end = endDate.toEpochDay();
               // System.out.println(start);

                long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
               //System.out.println();

               // consumableArrayList.add(new Consumable(,String.valueOf(LocalDate.ofEpochDay(randomEpochDay)),String.valueOf(balance)));
               // consumableArrayList.add(new Consumable(arr[randomNumber],String.valueOf(randomInt),"30","kgs","5"));

                //Log.d("TAG", "doInBackground: " +t.toString());
            }





            return null;
        }
    }
}

