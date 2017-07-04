package com.example.hung.jsonweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hung on 6/21/2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Weather> arrayList;

    public CustomAdapter(Context context, ArrayList<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_lv,null);

        Weather weather = arrayList.get(i);

        TextView tv_ngay = (TextView) view.findViewById(R.id.tv_ngay);
        TextView tv_tt = (TextView) view.findViewById(R.id.tv_tt);
        TextView tv_maxtemp = (TextView) view.findViewById(R.id.tv_maxtemp);
        TextView tv_mintemp = (TextView) view.findViewById(R.id.tv_mintemp);
        ImageView img = (ImageView) view.findViewById(R.id.img_1);

        tv_ngay.setText(weather.Day);
        tv_tt.setText(weather.Status);
        tv_maxtemp.setText(weather.tempmax+"*C");
        tv_mintemp.setText(weather.tempmin+"*C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+weather.Image+".png").into(img);
        return view;
    }
}
