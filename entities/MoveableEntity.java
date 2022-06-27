
package entities;


import utilities.Vec2D;


public class MoveableEntity extends Entity{

    private Vec2D prevPosition;
    private Vec2D acceleration;


    public MoveableEntity(String label, double x, double y, int radius, double vx, double vy, double dt, double mass, double charge){
        super(label, x, y, radius, mass, charge);
        prevPosition = new Vec2D(x - vx*dt, y - vy*dt);
    }


    public Vec2D get_velocity(double dt){
        return position.subtract(prevPosition).scale(1/dt).add(acceleration.scale(dt));
    }


    public void move(double x, double y){
        position = position.add(new Vec2D(x, y));
    }
    
    // todo : compute_ax() and compute_ay() using System
}