package com.blu.reactive;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by shamim on 16/07/15.
 */
public class JersyClient {
    public static void main(String[] args) {
        System.out.println("Invoking jersy service!!");

        for (int i = 0; i < 200; i++) {
            new Thread("" + i) {
                public void run() {
                    Client client = Client.create();
                    WebResource webResource = client.resource("http://localhost:8080/test/rest/hello/echo/helloworld");
                    ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

                    if (response.getStatus() != 200) {
                        System.out.println(" Error:" + response.getStatus());
                    }
                    String output = response.getEntity(String.class);

                    //System.out.println("Output from Server .... \n");
                    System.out.println(output);
                }

            }.start();

        }
    }
}
