package com.min.project_map.data.vo;

public class BookVo {
    private double latitude;
    private double longitude;
    private int aqi;
    private String name; // address name

    public BookVo() {

    }

    public BookVo(double latitude, double longitude, int aqi, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.aqi = aqi;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
