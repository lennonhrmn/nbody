package org.acme.nbody;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BodyDTOTest {

    private Body body;
    private BodyDTO bodyDTO;

    private final double[] initialPosition = {1.0, 2.0};
    private final double[] initialVelocity = {0.5, -0.5};

    @BeforeEach
    void setUp() {
        body = new Body("A", 10.0, initialPosition, initialVelocity, "red", 1.0);
        bodyDTO = new BodyDTO(body);
    }

    @Test
    void testConstructorWithBody() {
        assertEquals(body.getId(), bodyDTO.getId(), "ID incorrect dans BodyDTO");
        assertEquals(body.getMass(), bodyDTO.getMass(), "Masse incorrecte dans BodyDTO");
        assertArrayEquals(body.getPosition(), bodyDTO.getPosition(), "Position incorrecte dans BodyDTO");
        assertArrayEquals(body.getVelocity(), bodyDTO.getVelocity(), "Vitesse incorrecte dans BodyDTO");
        assertEquals(body.getColor(), bodyDTO.getColor(), "Couleur incorrecte dans BodyDTO");
        assertEquals(body.getRadius(), bodyDTO.getRadius(), "Rayon incorrect dans BodyDTO");
    }

    @Test
    void testSettersAndGetters() {
        bodyDTO.setId("B");
        bodyDTO.setMass(15.0);
        bodyDTO.setPosition(new double[]{3.0, 4.0});
        bodyDTO.setVelocity(new double[]{1.0, -1.0});
        bodyDTO.setColor("blue");
        bodyDTO.setRadius(2.0);

        assertEquals("B", bodyDTO.getId(), "ID incorrect après setter");
        assertEquals(15.0, bodyDTO.getMass(), "Masse incorrecte après setter");
        assertArrayEquals(new double[]{3.0, 4.0}, bodyDTO.getPosition(), "Position incorrecte après setter");
        assertArrayEquals(new double[]{1.0, -1.0}, bodyDTO.getVelocity(), "Vitesse incorrecte après setter");
        assertEquals("blue", bodyDTO.getColor(), "Couleur incorrecte après setter");
        assertEquals(2.0, bodyDTO.getRadius(), "Rayon incorrect après setter");
    }

    @Test
    void testNoArgConstructor() {
        BodyDTO newBodyDTO = new BodyDTO();
        assertNull(newBodyDTO.getId(), "ID ne doit pas être initialisé par défaut");
        assertEquals(0.0, newBodyDTO.getMass(), "Masse ne doit pas être initialisée par défaut");
        assertNull(newBodyDTO.getPosition(), "Position ne doit pas être initialisée par défaut");
        assertNull(newBodyDTO.getVelocity(), "Vitesse ne doit pas être initialisée par défaut");
        assertNull(newBodyDTO.getColor(), "Couleur ne doit pas être initialisée par défaut");
        assertEquals(0.0, newBodyDTO.getRadius(), "Rayon ne doit pas être initialisé par défaut");
    }
}
