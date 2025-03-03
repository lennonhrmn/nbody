package org.acme.nbody;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationConfigTest {

    private SimulationConfig config;

    @BeforeEach
    void setUp() {
        config = new SimulationConfig();
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0.0, config.getGravitationalConstant(), "Gravitational constant should be 0.0 by default");
        assertEquals(0.0, config.getTimeStep(), "Time step should be 0.0 by default");
        assertEquals(0, config.getDimensions(), "Dimensions should be 0 by default");
    }

    @Test
    void testParameterizedConstructor() {
        SimulationConfig customConfig = new SimulationConfig(6.67430e-11, 0.01, 3);
        assertEquals(6.67430e-11, customConfig.getGravitationalConstant(), "Incorrect gravitational constant");
        assertEquals(0.01, customConfig.getTimeStep(), "Incorrect time step");
        assertEquals(3, customConfig.getDimensions(), "Incorrect dimensions");
    }

    @Test
    void testSettersAndGetters() {
        config.setGravitationalConstant(9.81);
        assertEquals(9.81, config.getGravitationalConstant(), "Setter or getter for gravitational constant failed");

        config.setTimeStep(0.02);
        assertEquals(0.02, config.getTimeStep(), "Setter or getter for time step failed");

        config.setDimensions(2);
        assertEquals(2, config.getDimensions(), "Setter or getter for dimensions failed");
    }
}
