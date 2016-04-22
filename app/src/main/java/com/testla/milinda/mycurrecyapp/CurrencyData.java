package com.testla.milinda.mycurrecyapp;

/**
 * Created by Milinda on 2016-04-12.
 */
public class CurrencyData {
    public String curr_name;
    public double curr_val;

    public CurrencyData(String name, String val) {
        try {
            curr_name = name;
            curr_val = Double.parseDouble(val);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
