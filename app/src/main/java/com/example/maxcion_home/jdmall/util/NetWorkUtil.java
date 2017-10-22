package com.example.maxcion_home.jdmall.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by maxcion_home on 2017/9/5.
 */

public class NetWorkUtil {


    public static String doGet(String urlPath) {
        InputStream inputStream = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlPath).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                return br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static String doPost(String urlPath, HashMap<String, String> params) {
        String stringParams = "";
        InputStream is = null;
        try {
            HttpURLConnection con = (HttpURLConnection) (new URL(urlPath).openConnection());
            con.setRequestMethod("POST");
            con.setConnectTimeout(10 * 1000);
            con.setReadTimeout(10 * 1000);
            for (HashMap.Entry<String, String> entry : params.entrySet()) {
                stringParams = stringParams + ("&" + entry.getKey() + "=" + entry.getValue());
            }
            stringParams = stringParams.substring(1);
            con.setDoOutput(true);
            con.getOutputStream().write(stringParams.getBytes());
            int code = con.getResponseCode();
            if (code == 200) {
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                return br.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
