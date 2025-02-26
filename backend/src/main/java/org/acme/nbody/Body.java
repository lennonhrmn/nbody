package org.acme.nbody;

public class Body {
    private double mass;
    private double[] position;
    private double[] velocity;
    private double[] force;
    private double[] initialPosition;
    private double[] initialVelocity;
    private String id;
    private String color;
    private double radius;

    public Body(String id, double mass, double[] position, double[] velocity, String color, double radius) {
        this.id = id;
        this.mass = mass;
        this.position = position.clone();
        this.velocity = velocity.clone();
        this.force = new double[position.length];
        this.initialPosition = position.clone();
        this.initialVelocity = velocity.clone();
        this.color = color;
        this.radius = radius;
    }

    public void resetForce() {
        for (int i = 0; i < force.length; i++) {
            force[i] = 0;
        }
    }

    public void applyForce(int dimension, double forceComponent) {
        force[dimension] += forceComponent;
    }

    public void update(double timeStep) {
        // Update velocity: v = v + F/m * dt
        for (int i = 0; i < velocity.length; i++) {
            velocity[i] += force[i] / mass * timeStep;
        }
        
        // Update position: p = p + v * dt
        for (int i = 0; i < position.length; i++) {
            position[i] += velocity[i] * timeStep;
        }
    }

    public void reset() {
        for (int i = 0; i < position.length; i++) {
            position[i] = initialPosition[i];
            velocity[i] = initialVelocity[i];
        }
        resetForce();
    }

    // Getters and setters
    public double getMass() {
        return mass;
    }

    public double[] getPosition() {
        return position;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public double[] getForce() {
        return force;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public double getRadius() {
        return radius;
    }
}