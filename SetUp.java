
import java.util.Vector;

import entities.Entity;
import entities.MoveableEntity;
import gui.Renderer;
import utilities.Vec2D;


public class SetUp {

    private Vector<Entity> entityVector;
    private Renderer       renderer;
    
    private Vec2D          gravityField = new Vec2D(0, 0);

    
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
}