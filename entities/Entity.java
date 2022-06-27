
package entities;


import utilities.Vec2D;


public class Entity{

    protected final String  label;
    protected       Vec2D   position;
    protected       double  mass, charge;
    protected       int     radius;

    
    public Entity(String label, double x, double y, int radius, double mass, double charge){
        this.label = label;
        position = new Vec2D(x, y);
        this.radius = radius;
        this.mass = mass;
        this.charge = charge;
    }


    public String get_label(){
        return label;
    }


    public double get_x(){
        return position.get_x();
    }


    public double get_y(){
        return position.get_y();
    }

    
    public int get_radius(){
        return radius;
    }
}