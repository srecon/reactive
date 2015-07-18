package com.blu.rest;

/**
 * Created by shamim on 17/07/15.
 */
public interface IServices {
    public String getWeather(String cityName);
    public String getCityByIp(String ipAddress);
}
