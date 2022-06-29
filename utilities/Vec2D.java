
package utilities;


public class Vec2D implements Cloneable{

    private double x, y;
    

    public Vec2D(double x, double y){
        this.x = x;
        this.y = y;
    }


    public double get_x(){
        return x;
    }


    public void set_x(double x){
        this.x = x;
    }

    public double get_y(){
        return y;
    }


    public void set_y(double y){
        this.y = y;
    }


    public Vec2D add(Vec2D vec){
        return new Vec2D(this.x + vec.x, this.y + vec.y);
    }


    public Vec2D subtract(Vec2D vec){
        return new Vec2D(this.x - vec.x, this.y - vec.y);
    }


    public Vec2D scale(double factor){
        return new Vec2D(x * factor, y * factor);
    }


    public Object clone(){
        Vec2D clone;

        try {
            clone = (Vec2D) super.clone();
            clone.x = this.x;
            clone.y = this.y;
        } catch (CloneNotSupportedException e) {
            throw new InternalError("Cannot clone: " + e.getMessage());
        }

        return clone;
    }
    

    public boolean isZero(){
        return this.x == 0 && this.y == 0;
    }
}