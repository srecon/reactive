package com.blu.rest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * Created by shamim on 17/07/15.
 */
public class ReplayEcho extends HystrixCommand<String> {
    private String echo;

    public ReplayEcho(String echo) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorld"))
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(1000000))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(3)
                                        .withCircuitBreakerSleepWindowInMilliseconds(2000)
                                //.withCircuitBreakerRequestVolumeThreshold(10)

                        )

        );
        this.echo = echo;
    }

    @Override
    // delegate the call to the implementation class
    protected String run() throws Exception {
        return new EchoImpl().sayHello(this.echo);
    }
}
