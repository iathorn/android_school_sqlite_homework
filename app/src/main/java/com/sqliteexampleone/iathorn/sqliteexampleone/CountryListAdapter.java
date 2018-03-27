package com.sqliteexampleone.iathorn.sqliteexampleone;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by USER on 2018-03-27.
 */

public class CountryListAdapter extends BaseAdapter {
    private Context context;
    ArrayList<ListItem> countryListArray;


    public CountryListAdapter(Context context, ArrayList<ListItem> countryListArray) {
        this.context = context;
        this.countryListArray = countryListArray;
    }

    @Override
    public int getCount() {
        return this.countryListArray.size();
    }

    @Override
    public Object getItem(int i) {
        return countryListArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            TextView tvCountry = (TextView) view.findViewById(R.id.tv_country);
            TextView tvCapital = (TextView) view.findViewById(R.id.tv_capital);

            tvCountry.setText(countryListArray.get(i).getCountry());
            tvCapital.setText(countryListArray.get(i).getCapital());
        }
        return view;
    }
}
