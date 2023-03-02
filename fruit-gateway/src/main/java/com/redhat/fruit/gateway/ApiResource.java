package com.redhat.fruit.gateway;

import com.redhat.fruit.gateway.beans.Config;
import com.redhat.fruit.gateway.beans.Fruit;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A JAX-RS interface. An implementation of this interface must be provided.
 */
@Path("/api")
public interface ApiResource {
    public static String FORCED_ERROR = "Forced error to find in jaeger and elastic search";

    @Path("fruits")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Fruit> listAll();

    @GET
    @Path("fruits/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Fruit findById(@PathParam("id") Integer id);

    @Path("fruits")
    @POST
    @Produces("application/octet-stream")
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(Fruit fruit);

    @PUT
    @Path("fruits/{id}")
    @Produces("application/json")
    Response update(@PathParam("id") Integer id, Fruit fruit);
    
    @Path("config")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Config configGet() throws Exception;
}
