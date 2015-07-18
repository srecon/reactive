package com.blu.reactive;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shamim on 16/07/15.
 */
public class JersyClient {
    private static Logger LOGGER = LoggerFactory.getLogger(JersyClient.class);
    private static final String URI = "http://localhost:9093/test/rest/servicefacade/getcitybyip/192.168.121.11";

    public static void main(String[] args) {

        LOGGER.info("Invoking Hystrix Service facade!!");
        for (int i = 0; i < 1000000; i++) {
            Client client = Client.create();
            WebResource webResource = client.resource(URI);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                LOGGER.info("Error:" + response.getStatus());
            }
            String output = response.getEntity(String.class);

            LOGGER.info(output);
        }

    }
}
