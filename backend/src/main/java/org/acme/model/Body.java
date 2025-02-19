package org.acme.model;

public class Body {
    public String name;
    public double x;
    public double y;
    public double vx;
    public double vy;
    public double mass;
    public String color;

    public Body(String name, double x, double y, double vx, double vy, double mass, String color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.color = color;
    }
}