package org.acme.nbody;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BodyTest {

    private Body body;
    private final double[] initialPosition = {1.0, 2.0};
    private final double[] initialVelocity = {0.5, -0.5};

    @BeforeEach
    void setUp() {
        body = new Body("A", 10.0, initialPosition, initialVelocity, "red", 1.0);
    }

    @Test
    void testConstructor() {
        assertEquals(10.0, body.getMass(), "Masse incorrecte");
        assertArrayEquals(initialPosition, body.getPosition(), "Position initiale incorrecte");
        assertArrayEquals(initialVelocity, body.getVelocity(), "Vitesse initiale incorrecte");
        assertEquals("A", body.getId(), "ID incorrect");
        assertEquals("red", body.getColor(), "Couleur incorrecte");
        assertEquals(1.0, body.getRadius(), "Rayon incorrect");
    }

    @Test
    void testResetForce() {
        body.applyForce(0, 5.0);
        body.applyForce(1, -3.0);
        body.resetForce();
        assertArrayEquals(new double[]{0.0, 0.0}, body.getForce(), "Les forces ne sont pas réinitialisées");
    }

    @Test
    void testApplyForce() {
        body.applyForce(0, 4.0);
        body.applyForce(1, -2.0);
        assertArrayEquals(new double[]{4.0, -2.0}, body.getForce(), "Forces incorrectes après application");
    }

    @Test
    void testUpdate() {
        body.applyForce(0, 10.0); // Force sur x
        body.applyForce(1, -20.0); // Force sur y
        body.update(1.0); // Time step = 1.0

        double[] expectedVelocity = {0.5 + (10.0 / 10.0) * 1.0, -0.5 + (-20.0 / 10.0) * 1.0};
        double[] expectedPosition = {1.0 + expectedVelocity[0] * 1.0, 2.0 + expectedVelocity[1] * 1.0};

        assertArrayEquals(expectedVelocity, body.getVelocity(), 1e-6, "Vitesse incorrecte après mise à jour");
        assertArrayEquals(expectedPosition, body.getPosition(), 1e-6, "Position incorrecte après mise à jour");
    }

    @Test
    void testReset() {
        body.applyForce(0, 5.0);
        body.update(1.0);
        body.reset();
        assertArrayEquals(initialPosition, body.getPosition(), "Position incorrecte après reset");
        assertArrayEquals(initialVelocity, body.getVelocity(), "Vitesse incorrecte après reset");
        assertArrayEquals(new double[]{0.0, 0.0}, body.getForce(), "Les forces ne sont pas réinitialisées après reset");
    }

     @Test
    void testInvalidPositionArrayTooLarge() {
        // Position doit être de taille 2 (ex: 2D), mais ici on fournit un tableau avec 3 valeurs
        double[] invalidPosition = {1.0, 2.0, 3.0};
        double[] velocity = {0.5, -0.5};

        // Tenter de créer un Body avec un tableau de position trop grand
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Body("A", 10.0, invalidPosition, velocity, "red", 1.0);
        });
        
        assertEquals("Position array must have exactly 2 elements", thrown.getMessage(), "Message d'erreur incorrect");
    }

    @Test
    void testEmptyPositionArray() {
        // Test pour vérifier le comportement avec un tableau vide
        double[] invalidPosition = {};
        double[] velocity = {0.5, -0.5};

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Body("A", 10.0, invalidPosition, velocity, "red", 1.0);
        });
        
        assertEquals("Position array cannot be empty", thrown.getMessage(), "Message d'erreur incorrect");
    }

    @Test
    void testNullPositionArray() {
        // Test pour vérifier le comportement avec un tableau null
        double[] invalidPosition = null;
        double[] velocity = {0.5, -0.5};

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Body("A", 10.0, invalidPosition, velocity, "red", 1.0);
        });
        
        assertEquals("Position array cannot be null", thrown.getMessage(), "Message d'erreur incorrect");
    }
}
