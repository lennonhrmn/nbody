package org.acme.nbody;

public class BodyDTO {
    private String id;
    private double mass;
    private double[] position;
    private double[] velocity;
    private String color;
    private double radius;

    // No-argument constructor
    public BodyDTO() {
        // Initialize fields with default values if necessary
    }

    public BodyDTO(Body body) {
        this.id = body.getId();
        this.mass = body.getMass();
        this.position = body.getPosition().clone();
        this.velocity = body.getVelocity().clone();
        this.color = body.getColor();
        this.radius = body.getRadius();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}