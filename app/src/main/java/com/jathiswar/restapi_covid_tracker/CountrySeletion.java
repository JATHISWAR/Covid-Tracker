package com.jathiswar.restapi_covid_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountrySeletion extends AppCompatActivity {

    EditText Search;
    ListView listView;
    ProgressDialog progressbar;


    public static List<CountryModel> countryModelsList = new ArrayList<>();
    CountryModel countryModel;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_seletion);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Search = findViewById(R.id.Search);
        listView = findViewById(R.id.mylistview);

        fetchData();




        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countryAdapter.getFilter().filter(s);
                countryAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent = new Intent(CountrySeletion.this,CountryDetailActivity.class);
              intent.putExtra("position",position);
              startActivity(intent);

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String url = "https://disease.sh/v3/covid-19/countries";

        progressbar = new ProgressDialog(CountrySeletion.this);
        progressbar.setTitle("Please Wait");
        progressbar.setMessage("loading your data");
        progressbar.show();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");

                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");

                                countryModel = new CountryModel(flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered, active);
                                countryModelsList.add(countryModel);


                            }

                            countryAdapter = new CountryAdapter(CountrySeletion.this, countryModelsList);
                            listView.setAdapter(countryAdapter);

                            progressbar.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressbar.dismiss();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CountrySeletion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressbar.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

}
