package com.blu.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shamim on 11/07/15.
 */
public class Test {
    private static Logger LOGGER = LoggerFactory.getLogger(com.blu.reactive.Test.class);
    public static void main(String[] args) throws Exception{
        System.out.println("Test Hystrix!!");
//        String s = new HelloWorldCommand().execute();
//        System.out.println(s);
        //Thread.sleep(100000l);
        for(int i =0; i< 3100000; i++){
            //new Thread(""+i){
            //    public void run(){
                    String s = new HelloWorldCommand().execute();
                    //System.out.println("Thread-"+Thread.currentThread().getName()+i+ s);
                    LOGGER.info("Thread-"+Thread.currentThread().getName()+i+ s);
            //    }

            //}.start();
            /*if(i == 6){
                Thread.sleep(20000);
            }*/
        }
        //Thread.sleep(100000l);
    }
}
