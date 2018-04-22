package com.tiga.utils;

public class AppUtils {

    public static String getIDR(double d){
        String result = "";
        String[] strings = String.valueOf(d).split("\\.");
        result = "Rp. "+strings[0];

        return result;
    }
}
