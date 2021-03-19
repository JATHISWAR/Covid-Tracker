package com.jathiswar.restapi_covid_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryModelsList;
    private List<CountryModel> countryModelsListFiltered;

    public CountryAdapter( Context context, List<CountryModel> countryModelsList) {
        super(context, R.layout.cardview,countryModelsList);

        this.context = context;
        this.countryModelsList = countryModelsList;
        this.countryModelsListFiltered = countryModelsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,null,true);
        TextView CountryName = view.findViewById(R.id.country);
        ImageView imageView = view.findViewById(R.id.flag);

        CountryName.setText(countryModelsListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelsListFiltered.get(position).getFlag()).into(imageView);

        return view;
    }


    @Override
    public int getCount() {
        return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModelsList.size();
                    filterResults.values = countryModelsList;

                }else{
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModel itemsModel:countryModelsList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelsListFiltered = (List<CountryModel>) results.values;
                CountrySeletion.countryModelsList = (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}