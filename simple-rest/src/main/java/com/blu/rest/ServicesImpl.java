package com.blu.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shamim on 17/07/15.
 * // http://localhost:8080/test/rest/serviceimpl/getweather/helloworld
 */
@Path("/serviceimpl")
public class ServicesImpl implements IServices {
    private static final Map<String, String> WEATHERS= new HashMap<String, String>(){{
        put("moscow", "29");
        put("petersburg", "21");
        put("berlin", "31");
        put("bercelona", "33");
    }};
    private static final Map<String, String> CITIES = new HashMap<String, String>(){{
        put("192.168.121.11","moscow");
        put("192.168.124.21","petersburg");
        put("192.138.111.31","berlin");
        put("192.168.132.61","bercelona");
    }};

    @GET
    @Path("/getweather/{param}")
    @Override
    public String getWeather(@PathParam("param") String cityName) {
        return WEATHERS.get(cityName);
    }
    @GET
    @Path("/getcitybyip/{param}")
    @Override
    public String getCityByIp(@PathParam("param")String ipAddress) {
        // Randomly failure
        final long ctime = System.currentTimeMillis();

        if(ctime % 20 == 0){
            throw new RuntimeException("Randomly failure!!");
        }
        if(ctime % 30 == 0){
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
        return CITIES.get(ipAddress);
    }
}
