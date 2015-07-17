package com.blu.reactive;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by shamim on 11/07/15.
 */
public class HelloWorldCommand extends HystrixCommand<String> {

    public HelloWorldCommand() {

        //super(HystrixCommandGroupKey.Factory.asKey("HelloWorld"), HystrixThreadPoolProperties.Setter().withCoreSize(11));
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorld"))
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(1000000))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(3)
                    .withCircuitBreakerSleepWindowInMilliseconds(2000)
                    //.withCircuitBreakerRequestVolumeThreshold(10)

                    )

        );
    }

    @Override
    protected String run() throws Exception {
        //return "Hello World!!";
        Client client = Client.create();
        WebResource webResource = client.resource("http://localhost:9093/test/rest/hello/helloworld");
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        if(response.getStatus() != 200){
            System.out.println(" Error:" + response.getStatus());
        }
        String output = response.getEntity(String.class);

        //System.out.println("Output from Server .... \n");
        //System.out.println(output);
        return output;
    }
    // fallback : static
    @Override
    protected String getFallback() {
        return "Fast fall";
    }
}
