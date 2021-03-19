package com.jathiswar.restapi_covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView mtotalcases,mtodaycases,mtotalrecovered,mtodayrecovered,mtotaldeaths,mtodaydeaths,mactivecases,maffected;
    Button mtrackcountries;
    ImageView mrefresh;
    PieChart pieChart;
    ProgressDialog progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        assign();
        fetchdata();
        mrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        mtrackcountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CountrySeletion.class);
                startActivity(intent);
            }
        });
    }

    private void fetchdata() {

        String url  = "https://disease.sh/v3/covid-19/all";
        progressbar = new ProgressDialog(MainActivity.this);
        progressbar.setTitle("Please Wait");
        progressbar.setMessage("loading your data");
        progressbar.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    mtotalcases.setText(jsonObject.getString("cases"));
                    mtodayrecovered.setText(jsonObject.getString("todayRecovered"));
                    mtotalrecovered.setText(jsonObject.getString("recovered"));
                    mactivecases.setText(jsonObject.getString("active"));
                    mtodaycases.setText(jsonObject.getString("todayCases"));
                    mtotaldeaths.setText(jsonObject.getString("deaths"));
                    mtodaydeaths.setText(jsonObject.getString("todayDeaths"));
                    maffected.setText(jsonObject.getString("affectedCountries"));


                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(mtotalcases.getText().toString()), Color.parseColor("#ed6f1a")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(mtotalrecovered.getText().toString()), Color.parseColor("#00db1a")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(mtotaldeaths.getText().toString()), Color.parseColor("#ff0000")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(mactivecases.getText().toString()), Color.parseColor("#2500f7")));
                    pieChart.startAnimation();

                     progressbar.dismiss();




                } catch (JSONException e) {
                    e.printStackTrace();
                   progressbar.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }


    private void assign()
    {
        mtotalcases = (TextView) findViewById(R.id.totalcases);
        mtodaycases = (TextView) findViewById(R.id.todaycases);
        mtotalrecovered = (TextView) findViewById(R.id.totalrecovered);
        mtodayrecovered = (TextView) findViewById(R.id.todayrecovered);
        mtotaldeaths = (TextView) findViewById(R.id.totaldeaths);
        mtodaydeaths = (TextView) findViewById(R.id.todaydeaths);
        mactivecases = (TextView) findViewById(R.id.activecases);
        maffected = (TextView) findViewById(R.id.affectedcountries);
        mtrackcountries = (Button) findViewById(R.id.track_countries);
        mrefresh = (ImageView) findViewById(R.id.refresh);
        pieChart = findViewById(R.id.piechart);

    }
}