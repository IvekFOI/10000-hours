package com.example.matija.registration0407;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    private int seekbarvalue = 0;

    TextView textView;
    Button button_accept;
    EditText EditTextCategory,EditTextSubcategory;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String CatHolder, SubcatHolder , EmailHolder;

//192.168.178.125

    // Storing server url into String variable.
    String HttpUrl = "http://192.168.0.51/CategorySender.php";
    String HttpUrl2 = "http://192.168.0.511/json.php";

    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Assign ID's to textview and button.
        textView = findViewById(R.id.TextViewUserEmail);
        button_accept= findViewById(R.id.button_accept);
        EditTextCategory = findViewById(R.id.EditTextCategory);
        EditTextSubcategory = findViewById(R.id.EditTextSubcategory);
        // Receiving value into activity using intent.
       // String TempHolder = getIntent().getStringExtra("UserEmailTAG");

        // Setting up received value into TextView.
        textView.setText(textView.getText() + EmailHolder);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(ProfileActivity.this);

        SeekBar seekBar = findViewById(R.id.seekBar);

        final TextView TextViewTimeShow = findViewById(R.id.TextViewTimeShow);

        seekBar.setProgress(1);

        seekBar.setMax(4);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seekbarvalue = progress * 15;
                TextViewTimeShow.setText(String.valueOf(progress * 15));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });


        // Adding click listener to logout button.
        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText){

                    CatSender();
                    EmailSender();

                }  else  {

                    Toast.makeText(ProfileActivity.this, "Nisam upisao u bazu", Toast.LENGTH_LONG).show();

                }


                Intent intent = new Intent(ProfileActivity.this,TimePickerActivity.class);
                intent.putExtra("vrijeme",seekbarvalue);
                startActivity(intent);

            }
        });
    }


    public void CatSender(){

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

              //  EmailHolder = getIntent().getStringExtra("UserEmailTAG");

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                EmailHolder = sharedPreferences.getString("UserEmailTag", "");



                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("Category", CatHolder);
                params.put("Subcategory", SubcatHolder);
                params.put("User_Email", EmailHolder);
                params.put("Time", String.valueOf(seekbarvalue));
                return params;
            }

        };


        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    public void CheckEditTextIsEmptyOrNot(){

        // Getting values from EditText.
        CatHolder = EditTextCategory.getText().toString().trim();
        SubcatHolder = EditTextSubcategory.getText().toString().trim();



        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(CatHolder) || TextUtils.isEmpty(SubcatHolder))
        {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }
        else {


            // If any of EditText is filled then set variable value as True.
            CheckEditText = true ;

        }
    }
   public void EmailSender() {



       // Creating string request with post method.
       StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl2,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String ServerResponse) {

                       // Showing Echo Response Message Coming From Server.

                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError volleyError) {

                       // Showing error message if something goes wrong.
                   }
               }) {
           @Override
           protected Map<String, String> getParams() {

               EmailHolder = getIntent().getStringExtra("UserEmailTAG");

               // Creating Map String Params.
               Map<String, String> params = new HashMap<String, String>();

               // Adding All values to Params.
               // The firs argument should be same sa your MySQL database table columns.
               params.put("User_Email", EmailHolder);
               return params;
           }

       };


       // Creating RequestQueue.
       RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);

       // Adding the StringRequest object into requestQueue.
       requestQueue.add(stringRequest);
   }


}
