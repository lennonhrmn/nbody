package org.acme.nbody;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NBodySimulationTest {
    
    private NBodySimulation simulation;
    
    @BeforeEach
    void setUp() {
        // Create a 2D simulation
        simulation = new NBodySimulation(1.0, 0.1, 2);
    }
    
    @Test
    void testInitialState() {
        // Add a body to the simulation
        Body body = new Body("test", 1.0, new double[]{0, 0}, new double[]{0, 0}, "red", 1.0);
        simulation.addBody(body);
        
        // Get the state
        List<BodyDTO> state = simulation.getState();
        
        // Verify state
        assertEquals(1, state.size());
        assertEquals("test", state.get(0).getId());
        assertArrayEquals(new double[]{0, 0}, state.get(0).getPosition());
    }
    
    @Test
    void testGravitationalAttraction() {
        // Create two bodies separated along x-axis
        Body body1 = new Body("body1", 1.0, new double[]{-1, 0}, new double[]{0, 0}, "red", 1.0);
        Body body2 = new Body("body2", 1.0, new double[]{1, 0}, new double[]{0, 0}, "blue", 1.0);
        
        simulation.addBody(body1);
        simulation.addBody(body2);
        
        // Run one update
        simulation.update();
        
        // Bodies should move towards each other
        List<BodyDTO> state = simulation.getState();
        
        // Find bodies in the result
        BodyDTO b1 = state.stream().filter(b -> b.getId().equals("body1")).findFirst().orElse(null);
        BodyDTO b2 = state.stream().filter(b -> b.getId().equals("body2")).findFirst().orElse(null);
        
        assertNotNull(b1);
        assertNotNull(b2);
        
        // Body 1 should move right (x position increases)
        assertTrue(b1.getPosition()[0] > -1);
        
        // Body 2 should move left (x position decreases)
        assertTrue(b2.getPosition()[0] < 1);
    }
    
    @Test
    void testReset() {
        // Create a body with initial velocity
        Body body = new Body("test", 1.0, new double[]{0, 0}, new double[]{1, 0}, "red", 1.0);
        simulation.addBody(body);
        
        // Run several updates to change position
        for (int i = 0; i < 10; i++) {
            simulation.update();
        }
        
        // Position should have changed
        List<BodyDTO> state = simulation.getState();
        double[] position = state.get(0).getPosition();
        assertFalse(position[0] == 0);
        
        // Reset simulation
        simulation.reset();
        
        // Position should be back to initial
        state = simulation.getState();
        position = state.get(0).getPosition();
        assertEquals(0, position[0]);
        assertEquals(0, position[1]);
    }
}