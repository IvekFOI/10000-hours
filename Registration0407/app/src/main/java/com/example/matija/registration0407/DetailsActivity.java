package com.example.matija.registration0407;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.matija.registration0407.Rez.EXTRA_CAT;
import static com.example.matija.registration0407.Rez.EXTRA_SUBCAT;
import static com.example.matija.registration0407.Rez.EXTRA_VRIJEME;

public class DetailsActivity extends AppCompatActivity {
    private ExampleAdapter mExampleAdapter;
    private ArrayList<Exampleitem> mExampleList;
    private RequestQueue mRequestQueue;
    private int seekbarvalue;
    Button button_accept;
    RequestQueue requestQueue;
    String EmailHolder, TimeHolder, CatHolder, SubcatHolder;
    String Email;
    String HttpUrl = "http://192.168.0.51/json.php";

    String HttpUrl2 = "http://192.168.0.51/CategorySetter.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

      /*  Intent intent = getIntent();

        String extracat = intent.getStringExtra(EXTRA_CAT);
        String extrasubcat = intent.getStringExtra(EXTRA_SUBCAT);
        String extravrijeme = intent.getStringExtra(EXTRA_VRIJEME);

*/
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Email = sharedPreferences.getString("UserEmailTag", "");


        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        TextView extriccat = findViewById(R.id.tvcatdet);
        TextView extricsubcat = findViewById(R.id.tvsubcatdet);
        TextView extrictime = findViewById(R.id.tvtimedet);
/*
        extriccat.setText(extracat);
        extricsubcat.setText(extrasubcat);
        extrictime.setText(extravrijeme);
*/

        button_accept = findViewById(R.id.detaccept);
        requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        SeekBar seekBar = findViewById(R.id.detseek);

        //final TextView TextViewTimeShow = findViewById(R.id.TextViewTimeShow);

        seekBar.setProgress(1);
        seekBar.setMax(4);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                seekbarvalue = progress * 15;
                //        TextViewTimeShow.setText(String.valueOf(progress * 15));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddtimeSender();


                Intent intent = new Intent(DetailsActivity.this, TimePickerActivity.class);
                intent.putExtra("vrijeme", seekbarvalue);
                startActivity(intent);

            }
        });
    }

    private void parseJSON() {
        //  String url = "https://api.myjson.com/bins/u4qv6";

        String url1 = HttpUrl + "?User_Email=" + Email;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("user");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String catrez = hit.getString("Category");
                                String subcatrez = hit.getString("Subcategory");
                                String vrijemerez = hit.getString("Time");

                                TextView extriccat = findViewById(R.id.tvcatdet);
                                TextView extricsubcat = findViewById(R.id.tvsubcatdet);
                                TextView extrictime = findViewById(R.id.tvtimedet);

                                extriccat.setText(catrez);
                                extricsubcat.setText(subcatrez);
                                extrictime.setText(vrijemerez);

                                SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(DetailsActivity.this);
                                sharedPreferences2.edit().putString("Time", vrijemerez).apply();
                                sharedPreferences2.edit().putString("Category", catrez).apply();
                                sharedPreferences2.edit().putString("Subcategory", subcatrez).apply();


                                mExampleList.add(new Exampleitem(catrez, subcatrez, vrijemerez));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


    public void AddtimeSender() {


        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing Echo Response Message Coming From Server.
                        //Toast.makeText(DetailsActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        //Toast.makeText(DetailsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailsActivity.this);
                EmailHolder = sharedPreferences.getString("UserEmailTag", "");
                TimeHolder = sharedPreferences.getString("Time", "");
                CatHolder = sharedPreferences.getString("Category", "");
                SubcatHolder = sharedPreferences.getString("Subcategory", "");


                int pretvoren = Integer.parseInt(TimeHolder);
                seekbarvalue = seekbarvalue + pretvoren;


                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("User_Email", EmailHolder);
                params.put("Time", String.valueOf(seekbarvalue));
                params.put("Category", CatHolder);
                params.put("Subcategory", SubcatHolder);

                return params;
            }

        };


        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

}
