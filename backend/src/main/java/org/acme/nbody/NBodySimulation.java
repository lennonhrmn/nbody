package org.acme.nbody;

import java.util.ArrayList;
import java.util.List;

public class NBodySimulation {
    private List<Body> bodies;
    private double gravitationalConstant;
    private double timeStep;
    private int dimensions; // 2D or 3D

    public NBodySimulation(double gravitationalConstant, double timeStep, int dimensions) {
        this.bodies = new ArrayList<>();
        this.gravitationalConstant = gravitationalConstant;
        this.timeStep = timeStep;
        this.dimensions = dimensions;
    }

    public void addBody(Body body) {
        bodies.add(body);
    }

    public void update() {
        // Calculate forces between all pairs of bodies
        for (int i = 0; i < bodies.size(); i++) {
            Body body1 = bodies.get(i);
            
            // Reset forces
            body1.resetForce();
            
            // Calculate force with all other bodies
            for (int j = 0; j < bodies.size(); j++) {
                if (i != j) {
                    Body body2 = bodies.get(j);
                    calculateForce(body1, body2);
                }
            }
        }
        
        // Update positions based on forces
        for (Body body : bodies) {
            body.update(timeStep);
        }
    }

    private void calculateForce(Body body1, Body body2) {
        double[] direction = new double[dimensions];
        double distance = 0;
        
        // Calculate distance vector
        for (int i = 0; i < dimensions; i++) {
            direction[i] = body2.getPosition()[i] - body1.getPosition()[i];
            distance += direction[i] * direction[i];
        }
        
        // Prevent division by zero with a small epsilon
        distance = Math.sqrt(distance);
        double epsilon = 0.001;
        if (distance < epsilon) {
            distance = epsilon;
        }
        
        // Calculate gravitational force magnitude
        // F = G * m1 * m2 / r^2
        double forceMagnitude = gravitationalConstant * body1.getMass() * body2.getMass() / (distance * distance);
        
        // Apply force components
        for (int i = 0; i < dimensions; i++) {
            double forceComponent = forceMagnitude * direction[i] / distance;
            body1.applyForce(i, forceComponent);
        }
    }

    public List<BodyDTO> getState() {
        List<BodyDTO> stateDTOs = new ArrayList<>();
        for (Body body : bodies) {
            stateDTOs.add(new BodyDTO(body));
        }
        return stateDTOs;
    }

    // Reset simulation
    public void reset() {
        for (Body body : bodies) {
            body.reset();
        }
    }
}