
import java.util.Vector;

import entities.Entity;
import gui.Renderer;


public class SetUp {

    private Vector<Entity> entityVector;
    private Renderer renderer;

    
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
}