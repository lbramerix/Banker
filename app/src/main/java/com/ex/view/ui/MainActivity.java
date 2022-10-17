package com.ex.view.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ex.chat.enumeration.DataDisplayType;
import com.ex.view.R;
import com.ex.view.util.DataUtils;
import com.ex.view.util.LoadingListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoadingListener {
    private LinearLayout loadingTips;
    private Button historicalDataBtn;
    private Button QueryBtn;
    static String date_txt="";
    int x,y=0;
    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.loadingTips = findViewById(R.id.loading_tips);
        this.historicalDataBtn = findViewById(R.id.btn_historical_data);
        this.QueryBtn=findViewById(R.id.btn_query);

        this.historicalDataBtn.setOnClickListener(this);
        this.QueryBtn.setOnClickListener(this);

        this.loadingTips.setVisibility(View.VISIBLE);
        //this.LineBtn.setEnabled(false);
        this.historicalDataBtn.setEnabled(false);
        DataUtils.loadData(4, 2, this);
    }

    public void showDatePickerDialog(Activity activity, int themeResId, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                date_txt = String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection=null;
                        try {
                            URL url = new URL("http://43.143.213.47:8081/banker/getDailyInfo?date="+date_txt);
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
                                x=y=0;
                            }else{
                                Log.d("succuss", "成功了 ");
                                JSONObject json = new JSONObject(result);; // 从转 JSON
                                x =json.getInt("turnover");
                                y =json.getInt("tradingVolume");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }}).start();
                Toast.makeText(MainActivity.this, "turnover:"+x+"\n"+"tradingVolume:"+y,Toast.LENGTH_SHORT).show();
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_query){
            showDatePickerDialog(this, 2, calendar);
            return;
        }
        switch (v.getId()) {
            case R.id.btn_historical_data:
                Intent intent1 = new Intent(this, ChartActivity.class);
                intent1.putExtra(ChartActivity.DATA_SHOW_KEY, DataDisplayType.PAGING.ordinal());
                startActivity(intent1);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataUtils.destroy();
    }

    @Override
    public void loadComplete() {
        this.loadingTips.setVisibility(View.INVISIBLE);
        this.historicalDataBtn.setEnabled(true);
    }

    private String getStringByStream(InputStream inputStream){
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
}
