package com.jathiswar.restapi_covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class CountryDetailActivity extends AppCompatActivity {
    private int positionCountry;
    TextView ccountry,ctotalcases, ctodaycases, ctotalrecovered, ctodayrecovered, ctotaldeaths, ctodaydeaths, cactivecases, caffected;
    ImageView cbackbutton, crefresh;
    PieChart pieChart;
    private ProgressDialog progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
       cbackbutton = (ImageView) findViewById(R.id.cback);
        crefresh = (ImageView) findViewById(R.id.crefresh);
         Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);
        clicklisteners();
        progressbar = new ProgressDialog(CountryDetailActivity.this);
        progressbar.setTitle("Please Wait");
        progressbar.setMessage("loading your data");
        progressbar.show();

        fetchdata();
    }

    private void fetchdata() {
       assign();

        ccountry.setText(CountrySeletion.countryModelsList.get(positionCountry).getCountry());
        ctotalcases.setText(CountrySeletion.countryModelsList.get(positionCountry).getCases());
        ctotalrecovered.setText(CountrySeletion.countryModelsList.get(positionCountry).getRecovered());
        cactivecases.setText(CountrySeletion.countryModelsList.get(positionCountry).getActive());
        ctodayrecovered.setText(CountrySeletion.countryModelsList.get(positionCountry).getRecovered());
        ctodaycases.setText(CountrySeletion.countryModelsList.get(positionCountry).getTodayCases());
        ctotaldeaths.setText(CountrySeletion.countryModelsList.get(positionCountry).getDeaths());
        ctodaydeaths.setText(CountrySeletion.countryModelsList.get(positionCountry).getTodayDeaths());

        pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(ctotalcases.getText().toString()), Color.parseColor("#ed6f1a")));
        pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(ctotalrecovered.getText().toString()), Color.parseColor("#00db1a")));
        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(ctotaldeaths.getText().toString()), Color.parseColor("#ff0000")));
        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(cactivecases.getText().toString()), Color.parseColor("#2500f7")));
        pieChart.startAnimation();

        progressbar.dismiss();


    }

    private void assign() {
        ctotalcases = (TextView) findViewById(R.id.ctotalcases);
        ctodaycases = (TextView) findViewById(R.id.ctodaycases);
        ctotalrecovered = (TextView) findViewById(R.id.ctotalrecovered);
        ctodayrecovered = (TextView) findViewById(R.id.ctodayrecovered);
        ctotaldeaths = (TextView) findViewById(R.id.ctotaldeaths);
        ctodaydeaths = (TextView) findViewById(R.id.ctodaydeaths);
        cactivecases = (TextView) findViewById(R.id.cactivecases);
        pieChart = (PieChart) findViewById(R.id.cpiechart);
    }






    private void clicklisteners() {
        cbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountryDetailActivity.this, CountrySeletion.class);
                startActivity(intent);
            }
        });

        crefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }
}


