package org.acme.nbody;

public class SimulationConfig {
    private double gravitationalConstant;
    private double timeStep;
    private int dimensions;

    public SimulationConfig() {
        // Default constructor for Jackson
    }

    public SimulationConfig(double gravitationalConstant, double timeStep, int dimensions) {
        this.gravitationalConstant = gravitationalConstant;
        this.timeStep = timeStep;
        this.dimensions = dimensions;
    }

    public double getGravitationalConstant() {
        return gravitationalConstant;
    }

    public void setGravitationalConstant(double gravitationalConstant) {
        this.gravitationalConstant = gravitationalConstant;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }
}