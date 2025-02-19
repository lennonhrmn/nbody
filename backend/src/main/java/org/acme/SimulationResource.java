package org.acme;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/simulation")
public class SimulationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSimulationData() {
        Response.ResponseBuilder response = Response.ok(List.of("Planet 1", "Planet 2", "Planet 3"));
        // Assure-toi que seule une valeur est définie pour Access-Control-Allow-Origin
        response.header(HttpHeaders.ACCEPT, "http://localhost:3000"); 
        return response.build();
    }
}
