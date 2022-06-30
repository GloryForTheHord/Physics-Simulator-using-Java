
import java.util.Vector;

import entities.Entity;
import entities.MoveableEntity;
import gui.Renderer;
import utilities.Vec2D;


public class SetUp {

    private Vector<Entity> entityVector;
    private Renderer       renderer;
    
    private Vec2D          gravityField = new Vec2D(0, 0);
    private boolean        collisionOnRenderer = false;

    
    public SetUp(Renderer renderer){
        entityVector = new Vector<Entity>();
        this.renderer = renderer;
    }


    public void add_entity(Entity entity){
        entityVector.add(entity);
        renderer.add_entity(entity);
    }


    public void update_renderer(){
        renderer.repaint();
    }

    
    public void update(){

        for (Entity entity : entityVector) {
            if(entity instanceof MoveableEntity){
                MoveableEntity moveableEntity = (MoveableEntity) entity;
                gravity_field(moveableEntity);
                moveableEntity.update();
                if(collisionOnRenderer){
                    collision_on_renderer(moveableEntity);
                }
            }
        }
    }


    public void set_gravityField(Vec2D gravityField){
        this.gravityField = gravityField;
    }


    private void gravity_field(MoveableEntity moveableEntity){
        if(!gravityField.isZero()){
            moveableEntity.count_force(gravityField.scale(moveableEntity.get_mass())); // gravityField * mass
        }
    }


    public void set_collisionOnRenderer(){
        collisionOnRenderer = true;
    }


    private void collision_on_renderer(MoveableEntity moveableEntity){
        int xMax = renderer.getWidth();
        int yMax = renderer.getHeight();
        double x = moveableEntity.get_x();
        double y = moveableEntity.get_y();
        int radius = moveableEntity.get_radius();

        if(x < radius){
            moveableEntity.collide_on_surface(radius, 0, new Vec2D(1, 0));
        }
        else if(x > xMax - radius){
            moveableEntity.collide_on_surface(xMax - radius, 0, new Vec2D(-1, 0));
        }

        if (y < radius){
            moveableEntity.collide_on_surface(0, radius, new Vec2D(0, 1));
        }
        else if (y > yMax - radius){
            moveableEntity.collide_on_surface(0, yMax - radius, new Vec2D(0, -1));
        }
    }
}