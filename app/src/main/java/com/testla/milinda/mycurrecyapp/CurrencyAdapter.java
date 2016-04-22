package com.testla.milinda.mycurrecyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Milinda on 2016-04-10.
 */
public class CurrencyAdapter extends ArrayAdapter{
    public CurrencyAdapter(Context context, ArrayList<CurrencyData> resource) {
        super(context,0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CurrencyData cObj = (CurrencyData) getItem(position);
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item,parent,false);
        }
        TextView textView_data = (TextView)convertView.findViewById(R.id.tv_list_view_item);
        String bind_data = cObj.curr_name+" : "+cObj.curr_val;
        textView_data.setText(bind_data);

        return convertView;
    }

}
