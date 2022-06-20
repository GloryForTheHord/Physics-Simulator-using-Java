package Entities;
public class MoveableEntity extends Entity{

    private double xPrev;

    public MoveableEntity(String label, double x, double y, double vx, double vy){
        super(label, x, y);
    }

    public double get_vx(double dT){
        return (x - xPrev)/dT + compute_F()/2/mass * dT;
    }
    
    //todo: compute_F() using System
}