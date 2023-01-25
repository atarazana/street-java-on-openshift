package com.redhat.fruit.gateway.restclient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.fruit.gateway.beans.Fruit;
import com.redhat.fruit.gateway.beans.Status;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey="fruit-api")
@RegisterClientHeaders(RequestHeaderFactory.class)
public interface FruitService {

    @GET
    @Path("api/fruits")
    List<Fruit> listAll();

    @GET
    @Path("api/fruits/{id}")
    Fruit findById(@PathParam("id") Integer id);

    @POST
    @Path("api/fruits")
    Response create(Fruit fruit);

    @PUT
    @Path("api/fruits/{id}")
    Response update(@PathParam("id") Integer id, Fruit fruit);

    @GET
    @Path("api/info/name")
    @Produces(MediaType.TEXT_PLAIN)
    String serviceName();

    @GET
    @Path("actuator/health")
    
    Status health();
}