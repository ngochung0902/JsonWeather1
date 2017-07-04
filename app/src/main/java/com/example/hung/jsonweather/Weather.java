package com.example.hung.jsonweather;

/**
 * Created by Hung on 6/21/2017.
 */

public class Weather {
    public String Day;
    public String Status;
    public String Image;
    public String tempmax;
    public String tempmin;

    public Weather(String day, String status, String image, String tempmax, String tempmin) {
        Day = day;
        Status = status;
        Image = image;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
    }

    public Weather() {
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTempmax() {
        return tempmax;
    }

    public void setTempmax(String tempmax) {
        this.tempmax = tempmax;
    }

    public String getTempmin() {
        return tempmin;
    }

    public void setTempmin(String tempmin) {
        this.tempmin = tempmin;
    }
}
