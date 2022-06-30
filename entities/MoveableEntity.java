
package entities;


import utilities.Vec2D;


public class MoveableEntity extends Entity{

    private final double dt;
    private       Vec2D  positionPrev;
    private       Vec2D  resultingForce;


    public MoveableEntity(String colorTag, double x, double y, int radius, double vx, double vy, double dt, double mass, double charge, double bouncingFactor){
        super(colorTag, x, y, radius, mass, charge, bouncingFactor);
        this.dt = dt;
        positionPrev = new Vec2D(x - vx*dt, y - vy*dt);
        resultingForce = new Vec2D(0, 0);
    }


    public Vec2D get_velocity(){
        return position.subtract(positionPrev).scale(1/dt).add(resultingForce.scale(dt/2/mass)); // (pos - posPrev)/dt + F/(2m) *dt
    }


    public void count_force(Vec2D newForce){
        resultingForce = resultingForce.add(newForce);
    }


    public void update(){
        Vec2D positionNext = position.scale(2).subtract(positionPrev).add(resultingForce.scale(dt*dt/mass)); // posNext = 2*pos - posPrev + F/m *dt^2
        positionPrev = position;
        position = positionNext;
        resultingForce = new Vec2D(0, 0);
    }


    public void collide_on_surface(double xRef, double yRef, Vec2D normal){
        Vec2D displacement = position.subtract(positionPrev);
        Vec2D ref = new Vec2D(xRef, yRef);
        position = position.subtract(ref).reflect(normal).add(ref);
        positionPrev = position.subtract(displacement.reflect(normal).scale(bouncingFactor));
    }
}