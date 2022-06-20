package Entities;
public class Entity{

    protected String label;
    protected double x, y;
    protected double mass;
    
    public Entity(String label, double x, double y){
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public String get_label(){
        return label;
    }

    public double get_x(){
        return x;
    }

    public double get_y(){
        return y;
    }
}