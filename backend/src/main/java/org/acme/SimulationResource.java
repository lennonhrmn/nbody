package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.acme.model.Body;


@Path("/simulation")
public class SimulationResource {
    private static final double G = 6.67430e-11; // Gravitational constant
    private static final double SCALE = 1e9; // Scale to make distances more manageable
    private List<Body> bodies;
    private double dt = 3600.0; // Time step in seconds (e.g., 1 hour)

    public SimulationResource() {
        bodies = new ArrayList<>();
        // Initialize with some celestial bodies (simplified positions)
        bodies.add(new Body("Sun", 0, 0, 0, 0, 1.989e30, "yellow"));
        bodies.add(new Body("Earth", 149.6e9, 0, 0, 29.78e3, 5.972e24, "blue"));
        bodies.add(new Body("Mars", 227.9e9, 0, 0, 24.077e3, 6.39e23, "red"));
        bodies.add(new Body("Jupiter", 778.5e9, 0, 0, 13.07e3, 1.898e27, "orange"));
    }

    private void updatePositions() {
        // Calculate forces and update positions
        for (Body body1 : bodies) {
            double fx = 0, fy = 0;

            for (Body body2 : bodies) {
                if (body1 == body2) continue;

                double dx = body2.x - body1.x;
                double dy = body2.y - body1.y;
                double r = Math.sqrt(dx * dx + dy * dy);

                if (r == 0) continue;

                double f = G * body1.mass * body2.mass / (r * r);
                fx += f * dx / r;
                fy += f * dy / r;
            }

            // Update velocities (F = ma)
            body1.vx += fx / body1.mass * dt;
            body1.vy += fy / body1.mass * dt;
        }

        // Update positions
        for (Body body : bodies) {
            body.x += body.vx * dt;
            body.y += body.vy * dt;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSimulationData() {
        updatePositions(); // Update the simulation

        // Convert positions to a format suitable for the frontend
        List<Object> responseData = new ArrayList<>();
        for (Body body : bodies) {
            responseData.add(Map.of(
                "name", body.name,
                "x", body.x / SCALE,  // Scale for display
                "y", body.y / SCALE,
                "vx", body.vx,
                "vy", body.vy,
                "mass", body.mass,
                "color", body.color
            ));
        }

        return Response.ok(responseData)
                      .header("Access-Control-Allow-Origin", "http://localhost:3000")
                      .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                      .header("Access-Control-Allow-Headers", "Content-Type")
                      .build();
    }

    @OPTIONS
    public Response optionsForCORS() {
        return Response.ok()
                      .header("Access-Control-Allow-Origin", "http://localhost:3000")
                      .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                      .header("Access-Control-Allow-Headers", "Content-Type")
                      .build();
    }
}
