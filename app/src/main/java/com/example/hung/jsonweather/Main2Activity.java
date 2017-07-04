package com.example.hung.jsonweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    String tenthanhpho ="";
    Button bt_back;
    TextView tv_tentp;
    ListView lv;
    CustomAdapter customadapter;
    ArrayList<Weather> mangthoitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt_back = (Button)findViewById(R.id.bt_back);
        tv_tentp = (TextView)findViewById(R.id.tv_tentp);
        lv = (ListView)findViewById(R.id.lv);

        mangthoitiet = new ArrayList<Weather>();
        customadapter = new CustomAdapter(Main2Activity.this,mangthoitiet);
        lv.setAdapter(customadapter);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua", "du lieu truyen qua: "+city);
        if (city.equals("")){
            tenthanhpho = "SaiGon";
            Get7Date(tenthanhpho);
        }
        else {
            tenthanhpho = city;
            Get7Date(tenthanhpho);
        }
    }

    private void Get7Date(String data) {
        String url="http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+",DE&appid=1473fb9b70e945f6dfdb949e655d048f";
        RequestQueue requestqueue = Volley.newRequestQueue(Main2Activity.this);
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String name =jsonObjectCity.getString("name");
                    tv_tentp.setText(name);

                    JSONArray jsonAraayList = jsonObject.getJSONArray("list");
                    for (int i =0; i<jsonAraayList.length();i++){
                        JSONObject jsonObjectList = jsonAraayList.getJSONObject(i);
                        String ngay = jsonObjectList.getString("dt");

                        long l = Long.valueOf(ngay);
                        Date date = new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                        String Day = simpleDateFormat.format(date);

                        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                        String max = jsonObjectTemp.getString("max");
                        String min = jsonObjectTemp.getString("min");

                        Double a = Double.valueOf(max);
                        String NhietdoMax = String.valueOf(a.intValue()/10);
                        Double b = Double.valueOf(min);
                        String NhietdoMin = String.valueOf(b.intValue()/10);

                        JSONArray jsonAraayWeather = jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonAraayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");

                        mangthoitiet.add(new Weather(Day,status,icon,NhietdoMax,NhietdoMin));
                    }
                    customadapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestqueue.add(stringrequest);
    }
}
