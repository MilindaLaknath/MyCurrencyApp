package com.testla.milinda.mycurrecyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    ArrayList<CurrencyData> ar;
    CurrencyAdapter currencyAdapter;
    ListView listView;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    String source = "USD";
    String base = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataFeatcher df = new DataFeatcher();
        df.asyncResponse = this;
        df.execute();

        tv1 = (TextView) findViewById(R.id.tv_currency_lbl);
        tv2 = (TextView) findViewById(R.id.tv_currency_data);
        tv3 = (TextView) findViewById(R.id.tv_currency_lbl2);
        tv4 = (TextView) findViewById(R.id.tv_currency_data2);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.planets_array, R.layout.textview_spainner);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter1);                                                      // Apply the adapter to the spinner

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter1);


        // Spinner 2 clicks. To currency
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String source =  ((Spinner)(view.findViewById(R.id.spinner1))).getItemAtPosition(0).toString().substring(0, 3);

                base = parent.getItemAtPosition(position).toString().substring(0, 3);

                if (ar != null) {
                    for (CurrencyData cd : ar) {
                        if (cd.curr_name.equals(base.trim())) {
                            tv1.setText(source + " 1 : ");
                            tv2.setText(cd.curr_val + " " + base);
                            tv3.setText(base + " 1 : ");
                            tv4.setText((1 / cd.curr_val) + " " + source);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner 1 clicks. From currency.
        // Still keep from currency to USD.
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                source = parent.getItemAtPosition(position).toString().substring(0, 3);
                double source_value = 1.0;
                CurrencyData cdata = null;

                if(ar != null){
                    for(CurrencyData cd : ar){
                        if (cd.curr_name.equals(source)){
                            source_value = cd.curr_val;
                        }
                    }
                    double base_source = 1/source_value;
                    for(CurrencyData cd : ar){
                        cd.curr_val = base_source*cd.curr_val;
                        if(cd.curr_name.equals(base)){
                            cdata = cd;
                        }
                    }

                    tv1.setText(source + " 1 : ");
                    tv2.setText(cdata.curr_val + " " + base);
                    tv3.setText(base + " 1 : ");
                    tv4.setText((1 / cdata.curr_val) + " " + source);

                    currencyAdapter = new CurrencyAdapter(getApplicationContext(), ar);

                    listView = (ListView) findViewById(R.id.lv_main);
                    listView.setAdapter(currencyAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Listview Clicks
        listView = (ListView) findViewById(R.id.lv_main);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                CurrencyData cudata = (CurrencyData) parent.getItemAtPosition(position);
                String base = cudata.curr_name;

                tv1.setText(source + " 1 : ");
                tv2.setText(cudata.curr_val + " " + base);
                tv3.setText(base + " 1 : ");
                tv4.setText((1 / cudata.curr_val) + " " + source);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String output) {

        try {
            JSONObject jsonObject = new JSONObject(output);
            JSONObject json_data_Object = new JSONObject(jsonObject.getString(DataFeatcher.rates));

            tv1 = (TextView) findViewById(R.id.tv_currency_lbl);
            tv2 = (TextView) findViewById(R.id.tv_currency_data);
            tv3 = (TextView) findViewById(R.id.tv_currency_lbl2);
            tv4 = (TextView) findViewById(R.id.tv_currency_data2);

            tv1.setText("USD - USD");
            tv2.setText(json_data_Object.getString("USD"));
            tv3.setText("USD - USD");
            tv4.setText(json_data_Object.getString("USD"));

            ar = new ArrayList<CurrencyData>();

            ArrayList<ArrayList> alist = JsonUtil2.getListFromJsonObject(json_data_Object);
            for (ArrayList ll : alist) {
                CurrencyData currencyData = new CurrencyData(ll.get(0).toString(), ll.get(1).toString());
                ar.add(currencyData);
            }

            currencyAdapter = new CurrencyAdapter(getApplicationContext(), ar);

            listView = (ListView) findViewById(R.id.lv_main);
            listView.setAdapter(currencyAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
