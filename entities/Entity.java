
package entities;


import utilities.Vec2D;


public class Entity{

    protected final String  colorTag;
    protected       Vec2D   position;
    protected       double  charge;
    protected       int     radius;

    
    public Entity(String colorTag, double x, double y, int radius, double charge){
        this.colorTag = colorTag;
        position = new Vec2D(x, y);
        this.radius = radius;
        if(radius <= 0){ throw new AssertionError("radius should be strictly greater than 0"); }
        this.charge = charge;
    }


    public String get_colorTag(){
        return colorTag;
    }


    public Vec2D get_position(){
        return (Vec2D) position.clone();
    }


    public void set_position(Vec2D position){
        this.position = (Vec2D) position.clone();
    }

    
    public int get_radius(){
        return radius;
    }


    public void update(){
        return;
    }
}