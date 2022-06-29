
package entities;


import utilities.Vec2D;


public class Entity{

    protected final String  colorTag;
    protected       Vec2D   position;
    protected       double  mass, charge;
    protected       int     radius;

    
    public Entity(String colorTag, double x, double y, int radius, double mass, double charge){
        this.colorTag = colorTag;
        position = new Vec2D(x, y);
        this.radius = radius;
        assert radius > 0 : "radius should be strictly greater than 0";
        this.mass = mass;
        assert mass > 0 : "mass should be strictly greater than 0";
        this.charge = charge;
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