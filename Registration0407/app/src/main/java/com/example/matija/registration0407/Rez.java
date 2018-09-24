package com.example.matija.registration0407;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class Rez extends AppCompatActivity implements ExampleAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<Exampleitem> mExampleList;
    private RequestQueue mRequestQueue;
    String Email;
    String HttpUrl = "http://192.168.0.51/json.php";
    String HttpUrl3 = "http://192.168.0.51/Delete.php";
    String EmailHolder,CatHolder,SubcatHolder;

    public static final String EXTRA_CAT = "catrez";
    public static final String EXTRA_SUBCAT = "subcatrez";
    public static final String EXTRA_VRIJEME = "proba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rez);
        createExampleList();
        buildRecyclerView();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Email = sharedPreferences.getString("UserEmailTag", "");

        mRequestQueue = Volley.newRequestQueue(this);

        parseJSON();

    }
    public void createExampleList() {
        mExampleList = new ArrayList<>();
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void removeItem(int position) {
        mExampleList.remove(position);
        mExampleAdapter.notifyItemRemoved(position);
    }

    private void parseJSON() {
      //  String url = "https://    api.myjson.com/bins/u4qv6";

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


                                SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(Rez.this);
                                sharedPreferences2.edit().putString("Category", catrez).apply();
                                sharedPreferences2.edit().putString("Subcategory", subcatrez).apply();

                                mExampleList.add(new Exampleitem(catrez, subcatrez, vrijemerez));

                            }

                            mExampleAdapter = new ExampleAdapter(Rez.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(Rez.this);

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



    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,DetailsActivity.class);
        Exampleitem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_CAT,clickedItem.getCatexa());
        detailIntent.putExtra(EXTRA_SUBCAT,clickedItem.getSubcatexa());
        detailIntent.putExtra(EXTRA_VRIJEME,clickedItem.getTimeexa());

        startActivity(detailIntent);


    }

    @Override
    public void onDeleteClick(int position) {
        removeItem(position);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                //  EmailHolder = getIntent().getStringExtra("UserEmailTAG");


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Rez.this);
                EmailHolder = sharedPreferences.getString("UserEmailTag", "");
                SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(Rez.this);
                CatHolder = sharedPreferences2.getString("Category","");
                SubcatHolder = sharedPreferences2.getString("Subcategory","");



                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("User_Email", EmailHolder);
                params.put("Category", CatHolder);
                params.put("Subcategory", SubcatHolder);

                return params;
            }

        };


        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Rez.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);





    }



}





































































