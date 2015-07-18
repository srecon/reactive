package com.blu.rest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by shamim on 16/07/15.
 * Service facade
 */
// add json
@Path("/servicefacade")
public class HystrixServiceFacade extends HystrixCommand<String>{
    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixServiceFacade.class);
    private static final String CONTEXT_ROOT="http://localhost:8080/test/rest/serviceimpl/";
    private String ipAddress;


    public HystrixServiceFacade() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("getCityByIp"))
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(100))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(3)
                        .withCircuitBreakerSleepWindowInMilliseconds(2000)
                        //.withCircuitBreakerRequestVolumeThreshold(10)
                        )
        );
    }

    @GET
    @Path("/getcitybyip/{param}")
    // http://localhost:8080/test/rest/servicefacade/getcitybyip/192.168.121.11
    public Response echo (@PathParam("param") String ipAddress){
        LOGGER.info("Invoked with Parameter!!!" + ipAddress);
        this.ipAddress = ipAddress;


        String resp = this.execute();
        return Response.status(200).entity(resp).build();
    }

    @Override
    protected String run() throws Exception {
        // invoke 3rd party service
        final String uri = CONTEXT_ROOT + "getcitybyip/" +this.ipAddress;
        Client client = Client.create();
        WebResource webResource = client.resource(uri);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            LOGGER.info("Error:" + response.getStatus());
        }
        return response.getEntity(String.class);


    }

    @Override
    protected String getFallback() {
        return "Fall back for get City by Ip";
    }
    private GetWeatherCommand getWeatherCommand = new GetWeatherCommand();

    @GET
    @Path("/getweather/{param}")
    public Response getWeather(@PathParam("param")String cityName){
        LOGGER.info("Invoked getWeather with param: "+ cityName);
        getWeatherCommand.cityName = cityName;
        String resp = getWeatherCommand.execute();

        return Response.status(200).entity(resp).build();
    }
    class GetWeatherCommand extends HystrixCommand<String>{
        private String cityName;
        public GetWeatherCommand() {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetWeather"))
                            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(100))
                            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(5)
                                            .withCircuitBreakerSleepWindowInMilliseconds(2000)
                                    //.withCircuitBreakerRequestVolumeThreshold(10)
                            )
            );
        }

        @Override
        protected String run() throws Exception {
            // invoke 3rd party service
            final String uri = CONTEXT_ROOT +"getweather/"+this.cityName;
            Client client = Client.create();
            WebResource webResource = client.resource(uri);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                LOGGER.info("Error {}", response.getStatus());
            }
            return response.getEntity(String.class);
        }
        // static fall back
        @Override
        protected String getFallback() {
            return "Fall Back for getWeather";
        }
    };
}
