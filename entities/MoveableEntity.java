
package entities;


import utilities.Vec2D;


public class MoveableEntity extends Entity{

    private final double mass, bouncingFactor;
    private       Vec2D  positionPrev;
    private       Vec2D  resultingForce;


    public MoveableEntity(String colorTag, double x, double y, int radius, double vx, double vy, double dt, double mass, double charge, double bouncingFactor){
        super(colorTag, x, y, radius, charge);
        this.mass = mass;
        if(mass <= 0){ throw new AssertionError("mass should be strictly greater than 0"); }
        if(bouncingFactor < 0 || bouncingFactor > 1){ throw new AssertionError("bouncing factor should be in [0; 1]"); }
        this.bouncingFactor = bouncingFactor;
        positionPrev = new Vec2D(x - vx*dt, y - vy*dt);
        resultingForce = new Vec2D(0, 0);
    }


    public Vec2D get_velocity(double dt){
        return position.subtract(positionPrev).scale(1/dt).add(resultingForce.scale(dt/2/mass)); // (pos - posPrev)/dt + F/(2m) *dt
    }


    public Vec2D get_positionPrev(){
        return (Vec2D) positionPrev.clone();
    }


    public void set_positionPrev(Vec2D positionPrev){
        this.positionPrev = (Vec2D) positionPrev.clone();
    }

    
    public double get_mass(){
        return mass;
    }

    
    public double get_bouncingFactor(){
        return bouncingFactor;
    }


    public void count_force(Vec2D newForce){
        resultingForce = resultingForce.add(newForce);
    }


    public void update(double dt){
        Vec2D positionNext = position.scale(2).subtract(positionPrev).add(resultingForce.scale(dt*dt/mass)); // posNext = 2*pos - posPrev + F/m *dt^2
        positionPrev = position;
        position = positionNext;
        resultingForce = new Vec2D(0, 0);
    }


    public void collide_on_surface(Vec2D positionCollisionOfCenter, Vec2D normal){
        normal = normal.get_normalized();
        Vec2D displacement = position.subtract(positionPrev);
        position = position.subtract(positionCollisionOfCenter).reflect(normal).add(positionCollisionOfCenter);
        positionPrev = position.subtract(displacement.reflect(normal).scale(bouncingFactor));
    }
}