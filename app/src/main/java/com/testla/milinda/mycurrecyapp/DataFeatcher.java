package com.testla.milinda.mycurrecyapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Milinda on 2016-04-10.
 */
public class DataFeatcher extends AsyncTask<String, Void, String> {

    public AsyncResponse asyncResponse;
    URL url;
    HttpURLConnection httpCon;
    public static final String rates = "rates";

    @Override
    protected String doInBackground(String... params) {
        try {
            url = new URL("https://openexchangerates.org/api/latest.json?app_id=1c0a42a3e9664dabab4fc015865bbb08");
            httpCon = (HttpURLConnection) url.openConnection();
            if (httpCon.getResponseCode() != 200) {
                throw new Exception("Failed to connect");
            }

            InputStream is = httpCon.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String read;

            while ((read = br.readLine()) != null) {
                //System.out.println(read);
                sb.append(read);
            }
            br.close();
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        asyncResponse.processFinish(s);

    }

}
