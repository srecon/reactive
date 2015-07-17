package com.blu.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by shamim on 16/07/15.
 * Service facade
 */
// add json
@Path("/hello")
public class HelloWorldService {
    private static int CNT=1;
    @GET
    @Path("/getmsg/{param}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getMsg (@PathParam("param")  String param) throws Exception{
        if(CNT%30 == 0){
            System.out.println("imitate error " + CNT);
            CNT++;
            //throw new RuntimeException("exit");
            Thread.sleep(5000);
            return null;
        }
        System.out.println("Service invoked!! " + CNT);

        String resp = "Service sys: " + param;
        CNT++;
        return Response.status(200).entity(resp).build();
    }
    @GET
    @Path("/echo/{param}")
    // http://localhost:9093/test/rest/hello/echo/helloworld
    public Response echo (@PathParam("param") String param){
        System.out.println("Service echo invoked!!");
        ReplayEcho replay = new ReplayEcho(param);
        String response = replay.execute();
        return Response.status(200).entity(response).build();
    }

}
