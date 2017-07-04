package com.example.hung.jsonweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView tv_city, tv_country, tv_nhietdo, tv_trangthai, tv_doam, tv_may, tv_gio, tv_ngaythang;
    private EditText edt_seach;
    private ImageView img_weather;
    private Button bt_seach, bt_dubao;
    String City="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_city = (TextView)findViewById(R.id.tv_city);
        tv_country = (TextView)findViewById(R.id.tv_country);
        tv_nhietdo = (TextView)findViewById(R.id.tv_nhietdo);
        tv_trangthai = (TextView)findViewById(R.id.tv_trangthai);
        tv_doam = (TextView)findViewById(R.id.tv_doam);
        tv_may = (TextView)findViewById(R.id.tv_may);
        tv_gio = (TextView)findViewById(R.id.tv_gio);
        tv_ngaythang = (TextView)findViewById(R.id.tv_ngaythang);

        bt_seach = (Button)findViewById(R.id.bt_seach);
        bt_dubao = (Button)findViewById(R.id.bt_dubao);

        edt_seach = (EditText)findViewById(R.id.edt_seach);

        img_weather = (ImageView)findViewById(R.id.img_weather);

        GetCurrentWeatherData("SaiGon");

        bt_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edt_seach.getText().toString();
                if(city.equals("")){
                    City = "SaiGon";
                }else {
                    City = city;
                    GetCurrentWeatherData(City);
                }
            }
        });

        bt_dubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edt_seach.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });
    }
    public void GetCurrentWeatherData(final String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);//thuc hien cac yeu cau goi di
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=1473fb9b70e945f6dfdb949e655d048f";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String name = jsonObject.getString("name");
                    tv_city.setText("Tên Thành Phố: "+name);
                    //format truong dt trong json ve ngay thang nam
                    long l = Long.valueOf(day);
                    Date date = new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                    String Day = simpleDateFormat.format(date);
                    tv_ngaythang.setText(Day);

                    JSONArray jsonArrayweather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectweather = jsonArrayweather.getJSONObject(0);
                    String status = jsonObjectweather.getString("main");
                    String icon = jsonObjectweather.getString("icon");

                    Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(img_weather);
                    tv_trangthai.setText(status);

                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String nhietdo = jsonObjectMain.getString("temp");
                    String doam = jsonObjectMain.getString("humidity");

                    Double a = Double.valueOf(nhietdo);
                    String Nhietdo = String.valueOf(a.intValue());

                    tv_nhietdo.setText(Nhietdo+"*C");
                    tv_doam.setText(doam+"%");

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    tv_gio.setText(gio+"m/s");

                    JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectClouds.getString("all");
                    tv_may.setText(may+"%");

                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String quocgia = jsonObjectSys.getString("country");
                    tv_country.setText("Tên Quốc Gia: "+quocgia);
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
        requestQueue.add(stringRequest);
    }
}
