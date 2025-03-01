package org.acme.nbody;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/api/simulation")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulationResource {

    private NBodySimulation simulation;
    private boolean isRunning = false;
    private Thread simulationThread;

    public SimulationResource() {
        // Default 2D simulation
        resetSimulation(new SimulationConfig(
                0.1,    // Gravitational constant
                0.01,   // Time step
                2       // 2D simulation
        ));
    }

    @POST
    @Path("/config")
    public Response configure(SimulationConfig config) {
        resetSimulation(config);
        return Response.ok("Simulation configured successfully").build();
    }

    @POST
    @Path("/body")
    public Response addBody(BodyDTO bodyDTO) {
        // Convert DTO to Body
        Body body = new Body(
                bodyDTO.getId() != null ? bodyDTO.getId() : UUID.randomUUID().toString(),
                bodyDTO.getMass(),
                bodyDTO.getPosition(),
                bodyDTO.getVelocity(),
                bodyDTO.getColor(),
                bodyDTO.getRadius()
        );
        
        simulation.addBody(body);
        return Response.ok("Body added successfully").build();
    }

    @POST
    @Path("/start")
    public Response startSimulation() {
        if (!isRunning) {
            isRunning = true;
            simulationThread = new Thread(() -> {
                while (isRunning) {
                    simulation.update();
                    try {
                        Thread.sleep(16); // ~60 FPS
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            simulationThread.start();
            return Response.ok("Simulation started").build();
        }
        return Response.ok("Simulation already running").build();
    }

    @POST
    @Path("/stop")
    public Response stopSimulation() {
        if (isRunning) {
            isRunning = false;
            if (simulationThread != null) {
                simulationThread.interrupt();
            }
            return Response.ok("Simulation stopped").build();
        }
        return Response.ok("Simulation not running").build();
    }

    @POST
    @Path("/reset")
    public Response reset() {
        simulation.reset();
        return Response.ok("Simulation reset").build();
    }

    @GET
    @Path("/state")
    public List<BodyDTO> getState() {
        return simulation.getState();
    }

    private void resetSimulation(SimulationConfig config) {
        // Stop current simulation if running
        if (isRunning) {
            stopSimulation();
        }
        
        // Create new simulation with provided configuration
        simulation = new NBodySimulation(
                config.getGravitationalConstant(),
                config.getTimeStep(),
                config.getDimensions()
        );
    }
}