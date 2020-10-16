package com.kiduyu.patriciproject.householdtrackingsystem.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kiduyu.patriciproject.householdtrackingsystem.Adapter.ConsumableAdapter;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Models.Consumable;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;
import com.kiduyu.patriciproject.householdtrackingsystem.SharedPref.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {
    EditText edtFullName;
    EditText edtEmail;
    EditText edtPassword;
    Button edt_profile, save;
    EditText edtMobile;
    Toolbar toolbar;
    ProgressDialog pDialog;
    String maddress="";
    Menu menu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        toolbar = view.findViewById(R.id.toolbar_fragment);
        toolbar.setTitle("Edit Profile Details");
        pDialog = new ProgressDialog(getActivity());

        edtFullName = view.findViewById(R.id.edt_name);
        edtFullName.setEnabled(false);
        save = view.findViewById(R.id.edt_profile_save);
        edt_profile = view.findViewById(R.id.edt_profile);
        edtEmail = view.findViewById(R.id.edt_email);
        edtEmail.setEnabled(false);
        edtPassword = view.findViewById(R.id.edt_password);
        edtPassword.setEnabled(false);
        edtMobile = view.findViewById(R.id.edt_phone);
        edtMobile.setEnabled(false);

        edt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtFullName.setEnabled(true);
                edtFullName.setText("");
                edtFullName.setMaxLines(1);
                edtFullName.setHint("Input New Full Name");
                edtFullName.setHintTextColor(getResources().getColor(R.color.dialog_color));

                edtEmail.setEnabled(true);
                edtEmail.setText("");
                edtEmail.setMaxLines(1);
                edtEmail.setHint("Input New Email");
                edtEmail.setHintTextColor(getResources().getColor(R.color.dialog_color));

                edtPassword.setEnabled(true);
                edtPassword.setText("");
                edtPassword.setMaxLines(1);
                edtPassword.setHint("Input New password");
                edtPassword.setHintTextColor(getResources().getColor(R.color.dialog_color));


                edtMobile.setEnabled(true);
                edtMobile.setText("");
                edtMobile.setMaxLines(1);
                edtMobile.setHint("Input New mobile number");
                edtMobile.setHintTextColor(getResources().getColor(R.color.dialog_color));


                edt_profile.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);

            }
        });
        String user= SharedPrefManager.getInstance(getActivity()).getUsername();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtFullName.getText().toString();
                String phone = edtMobile.getText().toString();
                String memail = edtMobile.getText().toString();
                String password = edtPassword.getText().toString();



                if (TextUtils.isEmpty(name))
                {
                    edtFullName.setError("Name Is Required..");
                    return;
                }
                else if (TextUtils.isEmpty(phone))
                {
                    edtMobile.setError("Phone Number Is Required..");
                    return;
                }else if (TextUtils.isEmpty(memail))
                {
                    edtMobile.setError("Phone Number Is Required..");
                    return;
                }
                else if (TextUtils.isEmpty(password))
                {
                    edtPassword.setError("Password Is Required..");
                    return;
                }
                else
                {
                    String first3 ="";
                    first3=phone.substring(0 , 3);
                    if (!first3.equals("254")){
                        edtMobile.setError("Phone Number must start with 254..");
                        return;
                    }else {
                        pDialog.setTitle("Updating Account");
                        pDialog.setMessage("Please wait, while we are checking the credentials.");
                        pDialog.setCanceledOnTouchOutside(false);
                        pDialog.show();
                        //String uniqueid= UUID.randomUUID().toString();
                        String uniqueid= Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
                        Log.d("TAG", "onCreate: "+uniqueid);

                        ValidatephoneNumber(name, phone,memail,maddress, password);
                    }

                }
            }
        });
getUser(user);
        return view;
    }

    private void getUser(String user) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_PROFILE+user,
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

                            }
                            progressDialog.dismiss();

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

    private void ValidatephoneNumber(String name, String phone, String memail, String maddress, String password) {
    }


}
