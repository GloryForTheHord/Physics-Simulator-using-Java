
package entities;


import utilities.Vec2D;


public class Entity{

    protected final String  colorTag;
    protected       Vec2D   position;
    protected       double  mass, charge, bouncingFactor;
    protected       int     radius;

    
    public Entity(String colorTag, double x, double y, int radius, double mass, double charge, double bouncingFactor){
        this.colorTag = colorTag;
        position = new Vec2D(x, y);
        this.radius = radius;
        if(radius <= 0){ throw new AssertionError("radius should be strictly greater than 0"); }
        this.mass = mass;
        if(mass <= 0){ throw new AssertionError("mass should be strictly greater than 0"); }
        this.charge = charge;
        if(bouncingFactor < 0 || bouncingFactor > 1){ throw new AssertionError("bouncing factor should be in [0; 1]"); }
        this.bouncingFactor = bouncingFactor;
    }


    public String get_colorTag(){
        return colorTag;
    }


    public double get_x(){
        return position.get_x();
    }


    public void set_x(double x){
        position.set_x(x);
    }


    public double get_y(){
        return position.get_y();
    }


    public void set_y(double y){
        position.set_y(y);
    }

    
    public int get_radius(){
        return radius;
    }

    
    public double get_mass(){
        return mass;
    }

    public void update(){
        return;
    }
}