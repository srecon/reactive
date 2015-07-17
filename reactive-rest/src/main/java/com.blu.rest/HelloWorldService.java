package com.blu.rest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by shamim on 16/07/15.
 * Service facade
 */
// add json
@Path("/hello")
public class HelloWorldService extends HystrixCommand<String>{
    private String echo;
    public HelloWorldService() {

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorld"))
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(100))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(3)
                        .withCircuitBreakerSleepWindowInMilliseconds(500)
                        //.withCircuitBreakerRequestVolumeThreshold(10)

                        )

        );
    }

    @GET
    @Path("/echo/{param}")
    // http://localhost:9093/test/rest/hello/echo/helloworld
    public Response echo (@PathParam("param") String param){
        this.echo = param;
        String resp = this.execute();
        return Response.status(200).entity(resp).build();
    }

    @Override
    protected String run() throws Exception {
        return new EchoImpl().sayHello(this.echo);
    }
}
