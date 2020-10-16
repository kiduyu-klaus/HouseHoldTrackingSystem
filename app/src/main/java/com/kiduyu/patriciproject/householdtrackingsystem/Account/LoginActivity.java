package com.kiduyu.patriciproject.householdtrackingsystem.Account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kiduyu.patriciproject.householdtrackingsystem.Constants.Constants;
import com.kiduyu.patriciproject.householdtrackingsystem.Home.HomeActivity;
import com.kiduyu.patriciproject.householdtrackingsystem.Mail.SendMail;
import com.kiduyu.patriciproject.householdtrackingsystem.R;
import com.kiduyu.patriciproject.householdtrackingsystem.RequestHandler.RequestHandler;
import com.kiduyu.patriciproject.householdtrackingsystem.SharedPref.SharedPrefManager;
import com.kiduyu.patriciproject.householdtrackingsystem.StatusColor.StatusBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    AlertDialog.Builder dialogBuilder;
    RelativeLayout layout1,layout2;
    AlertDialog alertDialog;
    Button btnSave;
    EditText txtemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_login);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        findViewById(R.id.txt_forgot_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_forgotpass, null);
                dialogBuilder.setView(dialogView);



                txtemail = (EditText) dialogView.findViewById(R.id.forgot_email);
                btnSave = (Button) dialogView.findViewById(R.id.btn_save_s);

                layout1 = dialogView.findViewById(R.id.content_ly);
                layout2 = dialogView.findViewById(R.id.pb_ly);

     /*   EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
        editText.setText("test label");

      */
                btnSave.setOnClickListener(view1 -> {
                    String emqail = txtemail.getText().toString().trim();

                    if (TextUtils.isEmpty(emqail)){
                        txtemail.setError("Required!");
                        txtemail.requestFocus();
                    } else {
                        layout2.setVisibility(View.VISIBLE);
                        layout1.setVisibility(View.GONE);
                        sendEmail(emqail);
                    }
                });
                alertDialog = dialogBuilder.create();
                alertDialog.show();

            }
        });
    }

    private void userLogin() {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        StatusBar.showProgressDialog(this,true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StatusBar.showProgressDialog(LoginActivity.this,false);
                try {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogin(
                                        obj.getInt("id"),
                                        obj.getString("username"),
                                        obj.getString("email")
                                );

                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(
                                getApplicationContext(),
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                StatusBar.showProgressDialog(LoginActivity.this,false);

                Toast.makeText(
                        getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

        @Override
    public void onClick(View v) {
            if(v == buttonLogin){
                userLogin();
            }

    }

    public void signup(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void sendEmail(String emqail) {
        Random r = new Random();
        String randomNumber = String.format("%04d", r.nextInt(1001));
        System.out.println(randomNumber);
        String email = emqail;

        String subject = "Password reset";
        String message = "Password reset for the HouseItems application\n\nYour Reset code is\n "
                +randomNumber+"\nInput that to reset your password";
        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
        
        ResetPassword(randomNumber);
        
    }

    private void ResetPassword(String randomNumber) {
        alertDialog.dismiss();
        dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_forgotpass1, null);
        dialogBuilder.setView(dialogView);



        txtemail = (EditText) dialogView.findViewById(R.id.forgot_email);
        btnSave = (Button) dialogView.findViewById(R.id.btn_save_s);

        layout1 = dialogView.findViewById(R.id.content_ly);
        layout2 = dialogView.findViewById(R.id.pb_ly);

     /*   EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
        editText.setText("test label");

      */
        btnSave.setOnClickListener(view1 -> {
            String emqail = txtemail.getText().toString().trim();

            if (TextUtils.isEmpty(emqail)){

            } else {
                if (emqail.equals(randomNumber)){
                    Toast.makeText(this, "accepted code ", Toast.LENGTH_SHORT).show();

                } else{
                    txtemail.setError("Wrong code!");
                    txtemail.requestFocus();
                }

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
