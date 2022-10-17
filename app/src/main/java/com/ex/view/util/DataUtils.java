
package com.ex.view.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.ex.chat.compat.Utils;
import com.ex.chat.entry.CandleEntry;
import com.ex.chat.entry.DepthEntry;
import com.ex.view.KChatMyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings("all")
public class DataUtils {

    private static AsyncTask<Void, Void, Void> task;
    public static List<CandleEntry> candleEntries;
    public static List<DepthEntry> depthEntries;
    public static double res=0;


    @SuppressLint("StaticFieldLeak")
    public static void loadData(int scale, int quoteScale, LoadingListener listener) {
        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (Utils.listIsEmpty(candleEntries)) {
                    candleEntries = DataUtils.getCandelData(scale);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                listener.loadComplete();
            }
        }.execute();
    }

    public static double getv(String date){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url = new URL("http://43.143.213.47:8081/banker/getDailyInfo?date="+date);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);
                    //设置请求方式 GET / POST 一定要大小
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setDoOutput(false);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode != HttpURLConnection.HTTP_OK) {
                        throw new IOException("HTTP error code" + responseCode);
                    }
                    String result = getStringByStream(connection.getInputStream());
                    if (result == null) {
                        Log.d("Fail", "失败了");
                    }else{
                        JSONObject json = new JSONObject(result);; // 从转 JSON
                        res=json.getDouble("tradingVolume");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}).start();
        return res;
    }
    private static String getStringByStream(InputStream inputStream){
        Reader reader;
        try {
            reader=new InputStreamReader(inputStream,"UTF-8");
            char[] rawBuffer=new char[512];
            StringBuffer buffer=new StringBuffer();
            int length;
            while ((length=reader.read(rawBuffer))!=-1){
                buffer.append(rawBuffer,0,length);
            }
            return buffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<CandleEntry> getCandelData(int scale) {
        String kLineData;
        List<CandleEntry> dataList = new ArrayList<>();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    try {
                        URL url = new URL("http://43.143.213.47:8081/banker/getGraphInfo");
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(3000);
                        connection.setReadTimeout(3000);
                        //设置请求方式 GET / POST 一定要大小
                        connection.setRequestMethod("GET");
                        connection.setDoInput(true);
                        connection.setDoOutput(false);
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if (responseCode != HttpURLConnection.HTTP_OK) {
                            throw new IOException("HTTP error code" + responseCode);
                        }
                        InputStream is=connection.getInputStream();

                        //循环读取
                        String line;
                        InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(inputStream);
                        StringBuilder current = new StringBuilder();;
                        while ((line = bufferedReader.readLine()) != null) {
                            current.append(line);
                        }
                        JSONArray arry=new JSONArray(current.toString());
                        for(int i=0;i<arry.length();i++){
                            //Log.d("success","!!");
                            JSONObject lan=arry.getJSONObject(i);
                            String d=lan.getString("date");
                            double open = lan.getDouble("openPrice");
                            double high = lan.getDouble("topPrice");
                            double low = lan.getDouble("bottomPrice");
                            double close = lan.getDouble("closingPrice");
                            double volume = getv(d);
                            dataList.add(
                                    new CandleEntry(scale, open, high, low, close, volume, new Date(lan.getString("date"))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(dataList, (arg0, arg1) -> arg0.getTime().compareTo(arg1.getTime()));

        return dataList;
    }

    public static void destroy() {
        if (null != task) {
            task.cancel(true);
        }
        if (null != candleEntries) {
            candleEntries.clear();
        }
        if (null != depthEntries) {
            depthEntries.clear();
        }
    }
}
